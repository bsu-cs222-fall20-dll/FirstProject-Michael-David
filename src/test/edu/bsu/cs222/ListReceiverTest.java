package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ListReceiverTest {
    @Test
    public void testGetUsers() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        JsonArray revisionsArray = parser.getRevisionsList(wikiObject);
        ListReceiver listReceiver = new ListReceiver();
        List<String> userList = listReceiver.createUserList(revisionsArray);
        System.out.println(userList);
    }

    @Test
    public void testGetTimezone() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Watermelon";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        JsonArray revisionsArray = parser.getRevisionsList(wikiObject);
        ListReceiver listReceiver = new ListReceiver();
        List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
        System.out.println(timezones);
    }

    @Test
    public void adjustTimezone() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        JsonArray revisionsArray = parser.getRevisionsList(wikiObject);
        ListReceiver listReceiver = new ListReceiver();
        List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
        System.out.println(timezones);
        List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
        System.out.println(adjustedTimezones);
    }

    @Test
    public void testGetDate() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        JsonArray revisionsArray = parser.getRevisionsList(wikiObject);
        ListReceiver listReceiver = new ListReceiver();
        List<String> dates = listReceiver.createDateList(revisionsArray);
        System.out.println(dates);
    }
}
