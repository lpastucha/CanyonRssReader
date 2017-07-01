package lpastucha.com.canyonrssreader.model;

/**
 * Created by lpastucha on 25.06.2017.
 */

public class RssFeedListElement {
    private String url;
    private String title;

    public  RssFeedListElement(final String title,final String url){
        this.url = url;
        this.title = title;
    }

    public final String readRssLink(){
        return url;
    }

    public final String rssTitle(){
        return title;
    }

    public final String displayInfoAboutRssFeed(){
        return  new StringBuilder("Title: ").append(title).append(" url: ").append(url).toString();
    }

    @Override
    public String toString() {
        return displayInfoAboutRssFeed();
    }
}
