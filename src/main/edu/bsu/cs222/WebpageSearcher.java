package edu.bsu.cs222;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebpageSearcher {
    public String search(String searchOption) throws MalformedURLException {
        String searchOptionConverted = convert(searchOption);
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=json&rvprop=timestamp%7Cuser&rvlimit=20&titles=" +
                searchOptionConverted + "&redirects=";
        return urlString;

        //"https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=json&rvprop=timestamp%7Cuser&rvlimit=20&titles=frank%20zappa&redirects="
    }

    private String convert(String searchOption) {
        if (searchOption.contains(" ")) {
            searchOption = searchOption.replace(" ", "%20");
        }
        searchOption = searchOption.toLowerCase();
        return searchOption;
    }

    public static URLConnection connectToWikipedia(URL url) throws IOException {
        try {
            URLConnection connection = url.openConnection();

            connection.setRequestProperty("User-Agent",
                    "CS222FirstProject/0.1 (msheckman@bsu.edu)");
            connection.connect();
            return connection;
        } catch (Exception e) {
            System.out.println("Cannot connect to the internet. Try again.");
            return null;
        }
    }

}
