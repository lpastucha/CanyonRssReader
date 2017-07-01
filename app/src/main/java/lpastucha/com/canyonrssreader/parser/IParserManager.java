package lpastucha.com.canyonrssreader.parser;

import lpastucha.com.canyonrssreader.model.RssFeed;

/**
 * Created by lpastucha on 09.06.2017.
 */

public interface IParserManager {
    /**
     * Runs parsing for specified url
     * @param urlToParse
     * @return parsed rss items list
     */
    RssFeed runRssParser(String urlToParse) throws Exception;
}
