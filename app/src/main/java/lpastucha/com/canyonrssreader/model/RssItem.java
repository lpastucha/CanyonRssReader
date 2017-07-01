package lpastucha.com.canyonrssreader.model;

import android.media.Image;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

/**
 * Created by lpastucha on 08.06.2017.
 */

public class RssItem implements Serializable{
    private final String itemTitle;
    private final URL itemLink;
    private final String itemContent;
    private final URL itemImageUrl;
    private final Date itemPublishDate;

    public RssItem(String itemTitle, URL itemLink, String itemContent, URL itemImageUrl, Date itemPublishDate) {
        this.itemTitle = itemTitle;
        this.itemLink = itemLink;
        this.itemContent = itemContent;
        this.itemImageUrl = itemImageUrl;
        this.itemPublishDate = itemPublishDate;
    }

    /**
     * Gets RSS item title.
     * @return title of RSS news
     */
    public String getItemTitle() {
        return itemTitle;
    }

    /**
     * Gets link to website news.
     * @return URL of website news
     */
    public URL getItemLink() {
        return itemLink;
    }

    /**
     * Gets text representation of link to website new.
     * @return the path to website news
     */
    public String getItemLinkPath() {
        return itemLink.getPath();
    }

    /**
     * Gets content of RSS item news
     * @return content of news
     */
    public String getItemContent() {
        return itemContent;
    }

    /**
     * Gets image of news
     * @return image of news
     */
    public URL getItemImageUrl() {
        return itemImageUrl;
    }

    /**
     * Gets date of publishing RSS news
     * @return date of new publication
     */
    public Date getItemPublishDate() {
        return itemPublishDate;
    }


    public String getHtmlRepresentation(){
        StringBuilder rssItemHtml = new StringBuilder();
        rssItemHtml.append("<h1>").append(itemTitle).append("<h1>\n")
                .append("<article>")
                    .append("<span>").append(itemPublishDate).append("</span>")
                    .append("<p>")
                    .append(itemContent)
                    .append("</p>")
                    .append("GO TO: <a href='")
                    .append(itemLink)
                    .append("'>VISIT NEWS</a>")
                .append("<article>\n");
        return rssItemHtml.toString();
    }
    @Override
    public String toString() {
        StringBuilder rssItem = new StringBuilder("Item: \n");
        rssItem.append("[Title: ").append(itemTitle)
                .append(" Link: ").append(itemLink)
                .append(" \n Content:").append(itemContent)
                .append("\n Published: ").append(itemPublishDate);

        return rssItem.toString();
    }
}
