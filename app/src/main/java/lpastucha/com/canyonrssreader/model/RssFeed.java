package lpastucha.com.canyonrssreader.model;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by lpastucha on 09.06.2017.
 */

public final class RssFeed implements Serializable{

    private String channelTitle;
    private URL channelImageUrl;
    private String channelDescription;
    private URL channelUrl;
    private List<RssItem> channelItems = new ArrayList<>();

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public URL getChannelImageUrl() {
        return channelImageUrl;
    }

    public void setChannelImageUrl(URL channelImageUrl) {
        this.channelImageUrl = channelImageUrl;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public URL getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(URL channelUrl) {
        this.channelUrl = channelUrl;
    }

    public final void addItem(final RssItem rssItem) {
        channelItems.add(rssItem);
    }

    public final ListIterator<RssItem> getChannelItemsIterator() {
        return channelItems.listIterator();
    }

    public final List<RssItem> getItems() {
        return new ArrayList<>(this.channelItems);
    }

    @Override
    public String toString() {
        StringBuilder rssChannel = new StringBuilder("Channel: " + channelTitle + "\n");
        for(RssItem e : channelItems){
            rssChannel.append(e.toString() + "\n");
        }
        return rssChannel.toString();
    }
}
