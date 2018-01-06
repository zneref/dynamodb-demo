package com.kodilla.zneref.aws.library.item;

import com.kodilla.zneref.aws.library.tools.Player;
import com.kodilla.zneref.aws.utils.console.Print;

public class Disc extends LibItem implements Player {

    public Disc(int year, String title, String author, int ranking) {
        super(year, title, author, ranking);
    }

    @Override
    public void play() {
        Print.ln(this + " is playing now...");
    }

    @Override
    public void stop() {
        Print.ln(this + " has been stopped...");
    }
}
