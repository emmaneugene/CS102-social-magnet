package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.page.cityfarmers.SendGiftPageView;

public class SendGiftController extends CityFarmersController {
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
        List<String> usersSentTo = sentGifts.entrySet().stream()
            .filter((Entry<String, Boolean> entry) -> entry.getValue())
            .map((Entry<String, Boolean> entry) -> entry.getKey())
            .collect(Collectors.toList());

        if (usersSentTo.size() == 0) return true;

        displayAlreadySentTodayError(usersSentTo);
        return false;
    }

    private void displayAlreadySentTodayError(List<String> usersSentTo) {
        int sentCount = usersSentTo.size();
        // Prepare the names for Painter::paintf
        List<String> prepName = usersSentTo.stream()
            .map((String name) -> String.format("[{%s}]", name))
            .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("[{Already sent a gift to");
        if (sentCount == 1) {
            sb.append(" ")
            .append(prepName.get(0))
            .append(" today.}]");
        } else {
            for (int i = 0; i < sentCount - 1; i++) {
                sb.append(" ").append(prepName.get(i)).append(",");
            }
            sb.append(" and ").append(prepName.get(sentCount - 1)).append(" today.}]");
        }
        view.setStatus(Painter.paintf(sb.toString(), Painter.Color.RED, Painter.Color.BLUE));
    }

    private void handleSendCrops(Crop toSend, String[] recipients) {
        view.setStatus(Painter.paint("Gift posted to your friends' wall.", Painter.Color.GREEN));
    }
}