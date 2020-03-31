package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.CredentialsDAO;
import com.g1t11.socialmagnet.data.FarmerActionDAO;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.page.cityfarmers.SendGiftPageView;

public class SendGiftController extends CityFarmersController {
    CredentialsDAO credDAO = new CredentialsDAO(database());
    FarmerActionDAO farmerActionDAO = new FarmerActionDAO(database());
    StoreDAO storeDAO = new StoreDAO(database());
    UserDAO userDAO = new UserDAO(database());

    List<Crop> crops;

    public SendGiftController(Navigator nav, Farmer me) {
        super(nav, me);
        crops = storeDAO.getStoreItems();
        view = new SendGiftPageView(crops);
    }

    @Override
    public void handleInput() {
        input.setPrompt(Painter.paintf(
            "[[{M}]]ain | Select choice",
            Painter.Color.YELLOW
        ));

        String choice = input.nextLine();
        if (choice.length() == 0) {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
            return;
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
            return;
        } else if (choice.matches("-?\\d+")) {
            Crop toSend = getCropSelection(choice);
            if (toSend == null)
                return;

            String[] recipients = getRecipients();

            if (!giftsAreValid(recipients))
                return;

            handleSendCrops(toSend, recipients);
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }

    }

    private Crop getCropSelection(String choice) {
        Crop toSend = null;
        try {
            int index = Integer.parseInt(choice);
            if (index <= 0 || index > crops.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return null;
            }
            toSend = crops.get(index - 1);
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use <id> to select a gift.", Painter.Color.RED));
        }
        return toSend;
    }

    private String[] getRecipients() {
        input.setPrompt("Send to");
        return input.nextLine().split("\\s*,\\s*");
    }

    private boolean giftsAreValid(String[] recipients) {
        if (moreThanFiveGiftsToday(recipients)
        || sendingToSelf(recipients)
        || !allUsersExist(recipients)
        || !allUsersFriends(recipients)
        || repeatedGiftToday(recipients)) {
            return false;
        }
        return true;
    }

    /**
     * Check if the gift request will result in more than 5 gifts being sent today.
     * @param recipients The list of usernames of recipients to send to.
     * @return Whether the total gifts sent today will be more than 5.
     */
    private boolean moreThanFiveGiftsToday(String[] recipients) {
        // Check if the gift request will result in more than 5 gifts being sent today.
        int giftsSentTodayCount = farmerLoadDAO.getGiftCountToday(me.getUsername());
        if (giftsSentTodayCount + recipients.length > 5) {
            view.setStatus(Painter.paint("Cannot send more than 5 gifts a day.", Painter.Color.RED));
            view.appendStatus(String.format(Painter.paint("Already sent %d gifts today.", Painter.Color.RED), giftsSentTodayCount));
            return true;
        }
        return false;
    }

    /**
     * Check if the current user is sending a gift to himself.
     * @param recipients The list of usernames of recipients to send to.
     * @return Whether the current user is within the recipient list.
     */
    private boolean sendingToSelf(String[] recipients) {
        boolean sendToMyself = Arrays.stream(recipients)
            .anyMatch(name -> name.equals(me.getUsername()));
        if (sendToMyself) {
            view.setStatus(Painter.paint("Cannot send gift to self.", Painter.Color.RED));
            return true;
        }
        return false;
    }

    /**
     * Check if all users to send to exist.
     * @param recipients The list of usernames of recipients to send to.
     * @return Whether all the users to send to exist.
     */
    private boolean allUsersExist(String[] recipients) {
        for (String name : recipients) {
            if (!credDAO.userExists(name)) {
                view.setStatus(Painter.paintf(
                    String.format("[{User [{%s}] not found.}]", name),
                    Painter.Color.RED,
                    Painter.Color.BLUE
                ));
                return false;
            }
        }
        return true;
    }

    /**
     * Check if all users to send to are friends.
     * @param recipients The list of usernames of recipients to send to.
     * @return Whether all the users to send to are friends of the current user.
     */
    private boolean allUsersFriends(String[] recipients) {
        List<String> friends = userDAO.getFriends(me.getUsername()).stream()
            .map((user) -> user.getUsername())
            .collect(Collectors.toList());
        if (!friends.containsAll(List.of(recipients))) {
            view.setStatus(Painter.paint("Cannot send to non friends.", Painter.Color.RED));
            return false;
        }
        return true;
    }

    /**
     * Check if any recipient has already been sent a gift today.
     * @param recipients The list of usernames of recipients to send to.
     * @return Whether any user has already received a gift today.
     */
    private boolean repeatedGiftToday(String[] recipients) {
        Map<String, Boolean> sentGifts = farmerLoadDAO.sentGiftToUsersToday(me.getUsername(), recipients);
        // Reduce the map into a list containing all invalid recipients.
        String[] usersSentTo = sentGifts.entrySet().stream()
            .filter((Entry<String, Boolean> entry) -> entry.getValue())
            .map((Entry<String, Boolean> entry) -> entry.getKey())
            .toArray(size -> new String[size]);

        if (usersSentTo.length != 0) {
            displayAlreadySentTodayError(usersSentTo);
            return true;
        }

        return false;
    }

    private void displayAlreadySentTodayError(String[] usersSentTo) {
        int sentCount = usersSentTo.length;
        // Prepare the names for Painter::paintf
        String[] prepName = Arrays.stream(usersSentTo)
            .map((String name) -> String.format("[{%s}]", name))
            .toArray(size -> new String[size]);

        StringBuilder sb = new StringBuilder("[{Already sent a gift to");
        if (sentCount == 1) {
            sb.append(" ").append(prepName[0]);
        } else if (sentCount == 2) {
            sb.append(" ").append(prepName[0]).append(" and ").append(prepName[1]);
        } else {
            for (int i = 0; i < sentCount - 1; i++) {
                sb.append(" ").append(prepName[i]).append(",");
            }
            sb.append(" and ").append(prepName[sentCount - 1]);
        }
        sb.append(" today.}]");
        view.setStatus(Painter.paintf(sb.toString(), Painter.Color.RED, Painter.Color.BLUE));
    }

    private void handleSendCrops(Crop toSend, String[] recipients) {
        farmerActionDAO.sendGifts(me.getUsername(), recipients, toSend.getName());
        view.setStatus(Painter.paint("Gift posted to your friends' wall.", Painter.Color.GREEN));
    }
}