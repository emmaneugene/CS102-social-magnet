package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;

public class FarmlandController extends Controller {
    public FarmlandController(Navigation nav) {
        super(nav);
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers", 
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
    }
}