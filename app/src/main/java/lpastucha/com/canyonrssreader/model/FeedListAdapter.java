package lpastucha.com.canyonrssreader.model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lpastucha.com.canyonrssreader.R;

/**
 * Created by lpastucha on 25.06.2017.
 */

public class FeedListAdapter extends ArrayAdapter<RssFeedListElement> {
    List<RssFeedListElement> rssItems;
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public FeedListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<RssFeedListElement> objects) {
        super(context, R.layout.rss_feed_list_element, objects);
        rssItems = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        RssFeedListElement item = rssItems.get(position);
        View feedView = layoutInflater.inflate(R.layout.rss_feed_list_element,null);

        TextView content = (TextView) feedView.findViewById(R.id.feedElementContent);
        content.setText(item.toString());
        return feedView;
    }


}
