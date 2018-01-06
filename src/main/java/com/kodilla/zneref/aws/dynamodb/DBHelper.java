package com.kodilla.zneref.aws.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kodilla.zneref.aws.dynamodb.table.Attrib;
import com.kodilla.zneref.aws.dynamodb.table.PrimaryKey;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class DBHelper {
    private static final DBHelper HELPER = new DBHelper();
    private final DynamoDB dynamoDB;

    private DBHelper() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(DBService.END_POINT, "us-west-2"))
                .build();

        dynamoDB = new DynamoDB(client);
    }

    public static DBHelper getInstance() { return HELPER; }

    public void createTable(String tableName) {
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition key
                            new KeySchemaElement("title", KeyType.RANGE)), // Sort key
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

    public void fillTable(String tableName, String resource) {
        Table table = dynamoDB.getTable(tableName);

        JsonParser parser = null;
        try {
            parser = new JsonFactory().createParser(new File(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode rootNode = null;
        try {
            rootNode = new ObjectMapper().readTree(parser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<JsonNode> iter = rootNode.iterator();

        ObjectNode currentNode;

        while (iter.hasNext()) {
            currentNode = (ObjectNode) iter.next();

            int year = currentNode.path(Attrib.YEAR).asInt();
            String title = currentNode.path(Attrib.TITLE).asText();

            try {
                table.putItem(new Item().withPrimaryKey(Attrib.YEAR, year, Attrib.TITLE, title).withJSON(Attrib.INFO,
                        currentNode.path(Attrib.INFO).toString()));
                System.out.println("PutItem succeeded: " + year + " " + title);

            } catch (Exception e) {
                System.err.println("Unable to add " + tableName + " item: " + year + " " + title);
                System.err.println(e.getMessage());
                break;
            }
        }
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Item getItem(String tableName, PrimaryKey key) {
        Table table = dynamoDB.getTable(tableName);
        Item outcome = null;
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(Attrib.YEAR, key.year(), Attrib.TITLE, key.title());
        try {
            outcome = table.getItem(spec);

        } catch (Exception e) {
            System.err.println("Unable to read item from" + tableName + ":" + key.year() + " " + key.title());
            System.err.println(e.getMessage());
        }
        return outcome;
    }

    public void updateItem(String tableName, PrimaryKey key, int rating) {
        Table table = dynamoDB.getTable(tableName);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(Attrib.YEAR, key.year(), Attrib.TITLE, key.title())
                .withUpdateExpression("set info.rating = :r")
                .withValueMap(new ValueMap().withNumber(":r", rating))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item rating...");
            table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:");

        } catch (Exception e) {
            System.err.println("Unable to update item: " + key.year() + " " + key.title());
            System.err.println(e.getMessage());
        }
    }
}

