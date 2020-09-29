package edu.bsu.cs222;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class WebpageSearcherTest {

    @Test
    public void SearchZappa() {
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        String expected = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=json&rvprop=timestamp%7Cuser&rvlimit=20&titles=frank%20zappa&redirects=";
        Assertions.assertEquals(expected, result);

    }

    @Test
    public void TestWatermelon() {
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Watermelon";
        String result = webpageSearcher.search(searchOption);
        String expected = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=json&rvprop=timestamp%7Cuser&rvlimit=20&titles=watermelon&redirects=";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void ConnectToWikipedia() throws IOException {
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL resultURL = new URL(result);
        URLConnection wikipediaConnection = webpageSearcher.connectToWikipedia(resultURL);
        Assertions.assertTrue(wikipediaConnection.getDoInput());
    }


}
