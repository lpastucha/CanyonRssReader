package lpastucha.com.canyonrssreader.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import lpastucha.com.canyonrssreader.model.RssFeed;
import lpastucha.com.canyonrssreader.model.RssItem;

/**
 * Created by lpastucha on 08.06.2017.
 */

public abstract class XmlParser {
    private XmlPullParserFactory xmlPullParserFactory;
    private XmlPullParser xmlPullParser;

    private static final String NS = null;

    public XmlParser(String namespace) throws XmlPullParserException {
        xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParser = xmlPullParserFactory.newPullParser();
    }

    protected String nameSpace() {
        return NS;
    }

    public final RssFeed parseXml(InputStream inputStream) throws Exception {
        if (inputStream != null) {
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.nextTag();
            return readRssDocument(xmlPullParser);
        } else {
            throw new NullPointerException("Input stream to parse does not exit!");
        }
    }

    protected abstract RssFeed readRssDocument(XmlPullParser xmlPullParser) throws Exception;

    protected abstract RssItem readItem(XmlPullParser xmlPullParser) throws Exception;

    protected abstract String readContent(XmlPullParser xmlPullParser) throws Exception;

    protected abstract Date readPublishDate(XmlPullParser xmlPullParser) throws Exception;

    /**
     * Gets title content from xml tag
     *
     * @return text value of curr tag
     * @throws IOException            if
     * @throws XmlPullParserException if occurs any problem with xml parsing
     */
    protected final String readTitle(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "title");
        String title = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, nameSpace(), "title");
        return title;
    }

    /**
     * Gets text value from xml tag
     *
     * @return text value of curr tag
     * @throws IOException            if
     * @throws XmlPullParserException if occurs any problem with xml parsing
     */
    protected final String readText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String result = "";
        if (xmlPullParser.next() == XmlPullParser.TEXT) {
            result = xmlPullParser.getText();
            xmlPullParser.nextTag();
        }
        return result;
    }

    protected abstract URI readLink(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException, URISyntaxException;

    protected URL takeImageUrl(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException, URISyntaxException {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "image");
        String link = readImageUrlLink(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, nameSpace(), "image");
        if(link==null) {
            return null;
        }
        return new URL(link);
    }

    private  String readImageUrlLink(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, nameSpace(), "link");
        String link = null;
        xmlPullParser.require(XmlPullParser.END_TAG, nameSpace(), "link");
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            link = readText(xmlPullParser);
        }
        return link;
    }

    protected final void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (xmlPullParser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
