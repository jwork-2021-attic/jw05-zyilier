package com.anish.screen;

import asciiPanel.AsciiPanel;

public class WinScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Won! Press enter!", 0, 0);
    }

}