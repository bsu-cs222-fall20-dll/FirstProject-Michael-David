package edu.bsu.cs222;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebpageSearcher {
    public String search(String searchOption) {
        String searchOptionConverted = convert(searchOption);
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=json&rvprop=timestamp%7Cuser&rvlimit=20&titles=" +
                searchOptionConverted + "&redirects=";
        return urlString;
    }

    private String convert(String searchOption) {
        if (searchOption.contains(" ")) {
            searchOption = searchOption.replace(" ", "%20");
        }
        searchOption = searchOption.toLowerCase();
        return searchOption;
    }

    public HttpURLConnection connectToWikipedia(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "CS222FirstProject/0.1 (msheckman@bsu.edu)");
            connection.setRequestMethod("GET");
            connection.connect();
            return connection;
        } catch (Exception e) {
            System.out.println("Cannot connect to the internet. Try again.");
            return null;
        }
    }

    public InputStream getPageStream(HttpURLConnection connection) throws IOException {
        return connection.getInputStream();
    }

}
