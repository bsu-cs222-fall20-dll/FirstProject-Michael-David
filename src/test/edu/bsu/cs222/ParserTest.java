package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class ParserTest {
    @Test
    public void testParse() {
        Parser parser = new Parser();
        InputStream sampleInput = getClass().getClassLoader().getResourceAsStream("sample.json");
        List<Revision> revisions = parser.parse(sampleInput);
        Assertions.assertEquals(4, revisions.size());
    }

    @Test
    public void testParseOnFrankZappa() throws IOException {
        Parser parser = new Parser();
        WebpageSearcher webpageSearcher = new WebpageSearcher();
        String searchOption = "Frank Zappa";
        String result = webpageSearcher.search(searchOption);

        //"https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=json&rvprop=timestamp%7Cuser&rvlimit=20&titles=frank%20zappa&redirects="
        //URLConnection connectionToWikipedia = webpageSearcher.connectToWikipedia(result);
    }
}
