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
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.cityfarmers.SendGiftPageView;

/**
 * This is a controller for Sending of gifts.
 */
public class SendGiftController extends CityFarmersController {
    CredentialsDAO credDAO = new CredentialsDAO(database());
    FarmerActionDAO farmerActionDAO = new FarmerActionDAO(database());
    StoreDAO storeDAO = new StoreDAO(database());
    UserDAO userDAO = new UserDAO(database());

    List<Crop> crops;

    /**
     * Creates a sending of gifts controller.
     * @param nav The app's navigator.
     * @param me The farmer.
     */
    public SendGiftController(Navigator nav, Farmer me) {
        super(nav, me);
        crops = storeDAO.getStoreItems();
        setView(new SendGiftPageView(crops));
    }

    @Override
    public SendGiftPageView getView() {
        return (SendGiftPageView) super.getView();
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.matches("-?\\d+")) {
            Crop toSend = getSelectedCrop(choice);
            if (toSend == null) {
                return;
            }

            String[] recipients = getRecipients();
            if (!giftsAreValid(recipients)) {
                return;
            }

            handleSendCrops(toSend, recipients);
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    /**
     * Gets the selected crop for sending gift.
     * @param choice The choice of crop for sending gift.
     * @return The selected choice of crop.
     */
    private Crop getSelectedCrop(String choice) {
        try {
            int index = Integer.parseInt(choice);

            if (index <= 0 || index > crops.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return null;
            }
            return crops.get(index - 1);
        } catch (NumberFormatException e) {
            setStatus(Painter.paint("Use <id> to select a gift.", Color.RED));
            return null;
        }
    }

    /**
     * Gets a string array of recipients.
     * @return The string array of recipients.
     */
    private String[] getRecipients() {
        getView().showSendToPrompt();
        return input.nextLine().split("\\s*,\\s*");
    }

    /**
     * A method to check if the gifts are valid to send with various conditions. 
     * @param recipients The string array of recipients.
     * @return If the gifts are valid to send.
     */
    private boolean giftsAreValid(String[] recipients) {
        return !moreThanFiveGiftsToday(recipients)
                && !sendingToSelf(recipients)
                && allUsersExist(recipients)
                && allUsersFriends(recipients)
                && !repeatedGiftToday(recipients);
    }

    /**
     * Check if the gift request will result in more than 5 gifts being sent
     * today.
     * @param recipients The list of usernames of recipients to send to.
     * @return Whether the total gifts sent today will be more than 5.
     */
    private boolean moreThanFiveGiftsToday(String[] recipients) {
        // Check if the gift request will result in more than 5 gifts being sent
        // today.
        int giftsSentTodayCount = farmerLoadDAO.getGiftCountToday(
                me.getUsername());
        if (giftsSentTodayCount + recipients.length > 5) {
            setStatus(Painter.paint(
                    "Cannot send more than 5 gifts a day.", Color.RED));
            appendStatus(Painter.paint(
                    String.format("Already sent %d gifts today.",
                            giftsSentTodayCount),
                    Color.RED));
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
            setStatus(Painter.paint("Cannot send gift to self.", Color.RED));
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
                setStatus(Painter.paintf(
                        String.format("[{User [{%s}] not found.}]", name),
                        Color.RED, Color.BLUE));
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
            setStatus(Painter.paint("Cannot send to non friends.", Color.RED));
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
        Map<String, Boolean> sentGifts = farmerLoadDAO.sentGiftToUsersToday(
                me.getUsername(), recipients);
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

    /**
     * A method to display that the error of gift already sent today.
     * @param usersSentTo The string array of friends that user already sent 
     *                    gift to.
     */
    private void displayAlreadySentTodayError(String[] usersSentTo) {
        // Prepare the names for Painter::paintf
        String[] prepNames = Arrays.stream(usersSentTo)
                .map((String name) -> String.format("[{%s}]", name))
                .toArray(size -> new String[size]);

        StringBuilder sb = new StringBuilder("[{Already sent a gift to ");
        sb.append(TextUtils.prettyList(prepNames));
        sb.append(" today.}]");

        setStatus(Painter.paintf(sb.toString(), Color.RED, Color.BLUE));
    }

    /**
     * A method to handle sending of crops.
     * @param toSend The crop to send.
     * @param recipients The string array of recipients of the crop gift.
     */
    private void handleSendCrops(Crop toSend, String[] recipients) {
        farmerActionDAO.sendGifts(
                me.getUsername(), recipients, toSend.getName());

        setStatus(Painter.paint(
                "Gift posted to your friends' wall.", Color.GREEN));
    }
}