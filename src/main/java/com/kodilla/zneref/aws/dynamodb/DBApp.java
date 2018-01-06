package com.kodilla.zneref.aws.dynamodb;

public class DBApp {

    public static void main(String[] args) {
        DBHelper db = DBHelper.getInstance();

        //creation and filling tables stuff
        loadData(db, DBService.TABLE_BOOKS, DBService.RESOURCE_BOOKS);
        loadData(db, DBService.TABLE_CDS, DBService.RESOURCE_CDS);
        loadData(db, DBService.TABLE_DVDS, DBService.RESOURCE_DVDS);
    }

    private static void loadData(DBHelper db, String tableName, String resource) {
        db.createTable(tableName);
        db.fillTable(tableName, resource);
    }
}
