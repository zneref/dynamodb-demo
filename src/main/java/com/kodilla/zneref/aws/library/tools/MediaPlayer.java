package com.kodilla.zneref.aws.library.tools;

import com.kodilla.zneref.aws.library.item.Disc;
import com.kodilla.zneref.aws.utils.console.Print;

public class MediaPlayer implements Player {
    private Disc disc;
    private boolean empty = true;

    @Override
    public void play() {
        if (!empty) disc.play();  //delegated method
    }

    @Override
    public void stop() {
        if (!empty) disc.stop();
    }

    public void insert(Disc disc) {
        Print.ln("Disc inserted to player.");
        this.disc = disc;
        empty = false;
    }

    public Disc eject() {
        Print.ln("Disc ejected.");
        empty = true;
        return disc;
    }
}
