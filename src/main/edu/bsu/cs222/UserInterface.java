package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a Wikipedia search: ");
        String searchInput = scanner.nextLine();

        System.out.println();
        System.out.println("Would you like to sort by most edits, or by recent edits?");
        System.out.println("--Type '1' for sorting by most edits");
        System.out.println("--Type '2' for sorting by most recent edits");

        int option = Integer.parseInt(scanner.nextLine());



        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        ListReceiver listReceiver = new ListReceiver();
        ListDisplayer listDisplayer = new ListDisplayer();

        String result = webpageSearcher.search(searchInput);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        if (wikipediaConnect == null) {
            System.out.println("Cannot connect to the internet. Try again.");
        } else {
            InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
            JsonObject wikiObject = parser.parse(is);
            JsonArray revisionsArray = parser.getRevisionsList(wikiObject);

            boolean nullity = parser.isNull(wikiObject);
            if (nullity) {
                System.out.println("Page does not exist!");
            } else {
                String redirectOutput = parser.redirectResults(wikiObject);
                parser.isRedirected(searchInput,redirectOutput);


                List<String> userList = listReceiver.createUserList(revisionsArray);
                List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
                List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
                List<String> dates = listReceiver.createDateList(revisionsArray);

                if (option == 1) {
                    listDisplayer.displayListByMostEdits(userList);
                } else if(option == 2){
                    listDisplayer.displayListByTime(userList,dates,adjustedTimezones);
                } else {
                    System.out.println("Bad input. Sorting by timestamp.");
                    listDisplayer.displayListByTime(userList,dates,adjustedTimezones);
                }
            }
        }

    }

}
