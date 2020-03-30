package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.FarmerActionDAO;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.page.cityfarmers.SendGiftPageView;

public class SendGiftController extends CityFarmersController {
    FarmerActionDAO farmerActionDAO = new FarmerActionDAO(database());
    StoreDAO storeDAO = new StoreDAO(database());

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
        }

        Crop toSend = getCropSelection(choice);
        String[] recipients = getRecipients();

        if (!giftsAreValid(recipients)) return;

        handleSendCrops(toSend, recipients);
    }

    private Crop getCropSelection(String choice) {
        Crop toSend = null;
        try {
            int index = Integer.parseInt(choice);
            if (index <= 0 || index > crops.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
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
        int giftsSentTodayCount = farmerLoadDAO.getGiftCountToday(me.getUsername());
        if (giftsSentTodayCount + recipients.length > 5) {
            view.setStatus(Painter.paint("Cannot send more than 5 gifts a day.", Painter.Color.RED));
            view.appendStatus(String.format(Painter.paint("Already sent %d gifts today.", Painter.Color.RED), giftsSentTodayCount));
            return false;
        }

        Map<String, Boolean> sentGifts = farmerLoadDAO.sentGiftToUsersToday(me.getUsername(), recipients);
        // Reduce the map into a list containing all invalid recipients.
        String[] usersSentTo = sentGifts.entrySet().stream()
            .filter((Entry<String, Boolean> entry) -> entry.getValue())
            .map((Entry<String, Boolean> entry) -> entry.getKey())
            .toArray(size -> new String[size]);

        if (usersSentTo.length == 0) return true;

        displayAlreadySentTodayError(usersSentTo);
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