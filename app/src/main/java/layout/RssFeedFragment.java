package layout;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import lpastucha.com.canyonrssreader.R;
import lpastucha.com.canyonrssreader.model.FeedAdapter;
import lpastucha.com.canyonrssreader.model.RssFeed;

public class RssFeedFragment extends ListFragment{
    private RssFeed rssFeed;

    private ListView itemsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss_channel,container);
        itemsList = (ListView) view.findViewById(R.id.feedItemsList);
        Log.d("INFO", "onCreateView: Item list size" + rssFeed.getItems().size() );
        FeedAdapter adapter = new FeedAdapter(getContext(),R.layout.rss_item_layout,rssFeed.getItems());
        itemsList.setAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setFeedToDisplay(RssFeed rssFeed){
        this.rssFeed = rssFeed;
    }
}
