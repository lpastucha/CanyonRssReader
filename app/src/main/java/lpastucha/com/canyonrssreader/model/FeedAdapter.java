package lpastucha.com.canyonrssreader.model;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lpastucha.com.canyonrssreader.R;

/**
 * Created by lpastucha on 16.06.2017.
 */

public class FeedAdapter extends ArrayAdapter<RssItem>{
    List<RssItem> rssItems;
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public FeedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<RssItem> objects) {
        super(context, R.layout.rss_item_layout, objects);
        rssItems = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        ViewHolderItem viewHolder;
        viewHolder = new ViewHolderItem();




        RssItem item = rssItems.get(position);
        View feedView = layoutInflater.inflate(R.layout.rss_item_layout,null);

        viewHolder.image = (WebView) feedView.findViewById(R.id.rss_item_image);
        viewHolder.title = (TextView) feedView.findViewById(R.id.rss_item_title);
        viewHolder.publishDate = (TextView) feedView.findViewById(R.id.rss_item_publish_date);
        viewHolder.content = (WebView) feedView.findViewById(R.id.rss_item_content);

        feedView.setTag(viewHolder);

        viewHolder.image.loadDataWithBaseURL(null, "<html><head></head><body><img src=\"" + item.getItemImageUrl() + "\"></body></html>", "html/css", "utf-8", null);
        viewHolder.title.setText(item.getItemTitle());
        viewHolder.publishDate.setText(item.getItemPublishDate().toString());
        viewHolder.content.loadDataWithBaseURL(null, "<html><head></head><body>" + item.getItemContent() + "</body></html>", "html/css", "utf-8", null);
        return feedView;
    }

    static class ViewHolderItem {
        WebView image;
        TextView title;
        TextView publishDate ;
        WebView content ;
    }
}
