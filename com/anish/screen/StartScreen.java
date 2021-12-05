package com.anish.screen;

import asciiPanel.AsciiPanel;

public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("start!.", 0, 0);
        terminal.write("Press ENTER!", 0, 1);
    }

}