package com.kodilla.zneref.aws;

import com.kodilla.zneref.aws.dynamodb.table.PrimaryKey;

import java.util.Arrays;
import java.util.List;

public final class Wanted {
    public final static List<PrimaryKey> ITEMS =
            Arrays.asList(new PrimaryKey(2005,"Wybawiciel"), //book
                    new PrimaryKey(2014, "Polka"), //cd
                    new PrimaryKey(1996, "Fear")); //dvd
}
