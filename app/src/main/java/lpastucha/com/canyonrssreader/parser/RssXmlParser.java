package lpastucha.com.canyonrssreader.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;

import lpastucha.com.canyonrssreader.model.RssFeed;
import lpastucha.com.canyonrssreader.model.RssItem;

/**
 * Created by lpastucha on 08.06.2017.
 */

public class RssXmlParser extends XmlParser {
    private static final Logger LOG = Logger.getLogger("RssXmlParser");

    public RssXmlParser(String namespace) throws XmlPullParserException {
        super(namespace);
    }

    @Override
    protected RssFeed readRssDocument(XmlPullParser xmlPullParser) throws Exception {
        RssFeed rssFeed = new RssFeed();
        xmlPullParser.nextTag();
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "channel");

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            // Starts by looking for the entry tag
            if (name.equals("item")) {
                rssFeed.addItem(readItem(xmlPullParser));
            } else {
                skip(xmlPullParser);
            }
        }
        return rssFeed;
    }

    @Override
    protected RssItem readItem(XmlPullParser xmlPullParser) throws Exception {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "item");

        String title = null;
        String description = null;
        URL imageURL  = null;
        URL link = null;
        Date date = null;

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            if (name.equals("title")) {
                title = readTitle(xmlPullParser);
            } else if (name.equals("description")) {
                description = readContent(xmlPullParser);
            }  else if (name.equals("image")) {
                imageURL = takeImageUrl(xmlPullParser);
            } else if (name.equals("link")) {
                try {
                    link = readLink(xmlPullParser).toURL();
                } catch (URISyntaxException e) {
                    link = null;
                }
            } else if (name.equals("pubDate")) {
                date = readPublishDate(xmlPullParser);

            } else {
                skip(xmlPullParser);
            }
        }
        return new RssItem(title,link,description,imageURL,date);
    }

    @Override
    protected String readContent(XmlPullParser xmlPullParser) throws Exception {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "description");
        String summary = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, nameSpace(), "description");
        return summary;
    }

    @Override
    protected Date readPublishDate(XmlPullParser xmlPullParser) throws Exception {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "pubDate");
        Date date = new Date();//FIXME pobrać datę ;
        readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, nameSpace(), "pubDate");
        return date;
    }

    @Override
    protected URI readLink(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException, URISyntaxException {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "link");
        String link = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, nameSpace(), "link");
        if (link != null) {
            return new URI(link);
        }
        return new URI("");
    }

}


