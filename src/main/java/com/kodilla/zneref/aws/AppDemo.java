package com.kodilla.zneref.aws;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.kodilla.zneref.aws.dynamodb.DBHelper;
import com.kodilla.zneref.aws.dynamodb.DBService;
import com.kodilla.zneref.aws.dynamodb.table.Attrib;
import com.kodilla.zneref.aws.dynamodb.table.PrimaryKey;
import com.kodilla.zneref.aws.library.item.*;
import com.kodilla.zneref.aws.library.tools.MediaPlayer;
import com.kodilla.zneref.aws.library.tools.Rateable;
import com.kodilla.zneref.aws.utils.console.Print;
import com.kodilla.zneref.aws.utils.random.RandomInts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppDemo {

    public static void main(String[] args) {
        DBHelper db = DBHelper.getInstance();
        List<LibItem> borrowedItems = new ArrayList<>();

        borrowedItems.add(getBook(db.getItem(DBService.TABLE_BOOKS, Wanted.ITEMS.get(0))));
        borrowedItems.add(getCD(db.getItem(DBService.TABLE_CDS, Wanted.ITEMS.get(1))));
        borrowedItems.add(getDVD(db.getItem(DBService.TABLE_DVDS, Wanted.ITEMS.get(2))));

        Print.ln("\nBorrowed items:".toUpperCase());
        for (LibItem i : borrowedItems) { i.info(); }

        Print.ln("\nPlayer functions:".toUpperCase());
        MediaPlayer player = new MediaPlayer();
        for (int i = 1; i <= 2; i++) {
            Disc disc = (Disc) borrowedItems.get(i);
            player.insert(disc);
            player.play();
            player.stop();
            player.eject();
        }

        Print.ln("\nRating operation:".toUpperCase());
        Rateable rateable = (item, stars) -> item.setRanking(stars);
        LibItem item = borrowedItems.get(0);
        int stars = RandomInts.nextInt(1, 6);
        item.info();
        rateable.rate(item, stars);
        PrimaryKey key = new PrimaryKey(item.getYear(), item.getTitle());
        db.updateItem(DBService.TABLE_BOOKS, key, stars); // update rating in database
        Print.ln(db.getItem(DBService.TABLE_BOOKS, key));
    }

    private static Book getBook(Item i) {
        Map m = i.getMap(Attrib.INFO);
        int pages = Integer.parseInt(m.get(Attrib.PAGES).toString());
        int ranking = Integer.parseInt(m.get(Attrib.RATING).toString());
        String author = m.get(Attrib.AUTHOR).toString();
        return new Book(i.getInt(Attrib.YEAR), i.getString(Attrib.TITLE), author, ranking, pages);
    }

    private static CompactDisc getCD(Item i) {
        Map m = i.getMap(Attrib.INFO);
        int tracks = Integer.parseInt(m.get(Attrib.TRACKS).toString());
        int ranking = Integer.parseInt(m.get(Attrib.RATING).toString());
        String author = m.get(Attrib.AUTHOR).toString();
        return new CompactDisc(i.getInt(Attrib.YEAR), i.getString(Attrib.TITLE), author, ranking, tracks);
    }

    private static DigitalVideoDisc getDVD(Item i) {
        Map m = i.getMap(Attrib.INFO);
        int ranking = Integer.parseInt(m.get(Attrib.RATING).toString());
        String director = m.get(Attrib.DIRECTOR).toString();
        return new DigitalVideoDisc(i.getInt(Attrib.YEAR), i.getString(Attrib.TITLE), director, ranking);
    }
}
