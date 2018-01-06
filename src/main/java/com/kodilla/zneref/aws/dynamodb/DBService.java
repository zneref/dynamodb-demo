package com.kodilla.zneref.aws.dynamodb;

public class DBService {
    public static final String END_POINT = "http://localhost:8000";
    public static final String TABLE_BOOKS = "books";
    public static final String TABLE_CDS = "cds";
    public static final String TABLE_DVDS = "dvds";
    public static final String RESOURCE_PATH = "./src/main/resources/";
    public static final String RESOURCE_TYPE = ".json";
    public static final String RESOURCE_BOOKS;
    public static final String RESOURCE_CDS;
    public static final String RESOURCE_DVDS;

    static {
        RESOURCE_BOOKS = RESOURCE_PATH + TABLE_BOOKS + RESOURCE_TYPE;
        RESOURCE_CDS = RESOURCE_PATH + TABLE_CDS + RESOURCE_TYPE;
        RESOURCE_DVDS = RESOURCE_PATH + TABLE_DVDS + RESOURCE_TYPE;
    }
}
