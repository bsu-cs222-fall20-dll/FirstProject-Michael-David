package edu.bsu.cs222;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class ParserTest {

    @Test
    public void testFrank_ZappaExists() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        Assertions.assertFalse(parser.isNull(wikiObject));
    }

    @Test
    public void testFrankZappaDoesntExist() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "FrankZappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        Assertions.assertTrue(parser.isNull(wikiObject));
    }

    @Test
    public void testParseOnFrankZappa() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        System.out.println(wikiObject);
    }

    @Test
    public void testRedirectResults() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        parser.redirectResults(wikiObject);
    }

    @Test
    public void testNoRedirectOnFrankZappa() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        String redirectResult = parser.redirectResults(wikiObject);
        Assertions.assertFalse(parser.isRedirected("Frank Zappa", redirectResult));
    }
    @Test
    public void testNoRedirectOnWatermelon() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Watermelon";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        String redirectResult = parser.redirectResults(wikiObject);
        Assertions.assertFalse(parser.isRedirected("Watermelon", redirectResult));
    }

    @Test
    public void testRedirectOnFrankZappa() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Zappa";
        String result = webpageSearcher.search(searchOption);
        URL wikiURL = new URL(result);
        HttpURLConnection wikipediaConnect = webpageSearcher.connectToWikipedia(wikiURL);
        InputStream is = webpageSearcher.getPageStream(wikipediaConnect);
        JsonObject wikiObject = parser.parse(is);
        String redirectResult = parser.redirectResults(wikiObject);
        Assertions.assertTrue(parser.isRedirected("Zappa", redirectResult));
    }


}
