package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ListDisplayerTest {
    @Test
    public void testListDisplay() throws IOException {
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
        List<String> timezones = listReceiver.createTimezoneList(revisionsArray);
        List<String> adjustedTimezones = listReceiver.adjustTimezone(timezones);
        List<String> dates = listReceiver.createDateList(revisionsArray);

        ListDisplayer listDisplayer = new ListDisplayer();
        listDisplayer.displayListByTime(userList,dates,adjustedTimezones);
    }

    @Test
    public void testListByEditor() throws IOException {
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

        ListDisplayer listDisplayer = new ListDisplayer();
        listDisplayer.displayListByMostEdits(userList);
    }
}
