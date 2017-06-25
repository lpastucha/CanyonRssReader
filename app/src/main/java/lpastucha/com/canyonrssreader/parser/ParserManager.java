package lpastucha.com.canyonrssreader.parser;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lpastucha.com.canyonrssreader.model.RssFeed;
import lpastucha.com.canyonrssreader.model.StreamManager;

/**
 * Created by lpastucha on 08.06.2017.
 */

public final class ParserManager implements IParserManager {

    private static final Logger LOG = Logger.getLogger("ParseManager");

    public ParserManager() {
    }

    /**
     * Runs parsing for specified url
     *
     * @param urlToParse
     * @return parsed rss items list
     */
    @Override
    public RssFeed runRssParser(final String urlToParse) throws Exception {
        LOG.log(Level.INFO, new StringBuilder("Start parsing: ").append(urlToParse).toString());
        ParserTask parserTask = new ParserTask();
        return parserTask.execute(urlToParse).get();
    }

    private String findRssType(final String rssURL) throws ParserConfigurationException, IOException, SAXException {
        LOG.log(Level.INFO, new StringBuilder("Checking rss type: ").append(rssURL).toString());
        final DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setNamespaceAware(true);
        DocumentBuilder builder = f.newInstance().newDocumentBuilder();
        Document doc = builder.parse(rssURL);
        Element e = doc.getDocumentElement();
        LOG.log(Level.INFO, new StringBuilder("Find element: ").append(e.getTagName()).toString());
        return e.getTagName();
    }

    private XmlParser takeAtomParser() throws XmlPullParserException {
        return new AtomXmlParser(null);
    }

    private XmlParser takeRssParser() throws XmlPullParserException {
        return new RssXmlParser(null);
    }

    private XmlParser selectParserType(String documentLocalName) throws Exception {
        switch (documentLocalName) {
            case "feed":
                LOG.log(Level.INFO, new StringBuilder("Selected parser:  ").append(AtomXmlParser.class.getName()).toString());
                return takeAtomParser();
            case "rss":
                LOG.log(Level.INFO, new StringBuilder("Selected parser:  ").append(RssXmlParser.class.getName()).toString());
                return takeRssParser();
            default:
                throw new IllegalArgumentException("Url is not parseable");
        }
    }

    private class ParserTask extends AsyncTask<String, Void, RssFeed> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected RssFeed doInBackground(String... params) {
            final XmlParser xmlParser;
            try {

                xmlParser = selectParserType(findRssType(params[0]));
                return xmlParser.parseXml(StreamManager.downloadUrl(params[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
