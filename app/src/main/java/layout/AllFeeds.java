package layout;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lpastucha.com.canyonrssreader.R;
import lpastucha.com.canyonrssreader.dao.FeedReaderDbHelper;
import lpastucha.com.canyonrssreader.model.FeedListAdapter;
import lpastucha.com.canyonrssreader.model.RssFeed;
import lpastucha.com.canyonrssreader.model.RssFeedListElement;
import lpastucha.com.canyonrssreader.parser.ParserManager;

public class AllFeeds extends Fragment {
    ParserManager parserManager;
    ListView availableFeedList;
    TextView infoLabel;
    FeedListAdapter titlesAdapter;
    FeedReaderDbHelper feedReaderDbHelper;

    public AllFeeds() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_feeds, container, false);
        parserManager = new ParserManager();
        availableFeedList = (ListView) view.findViewById(R.id.listViewForRSSFeeds);
        availableFeedList.setOnItemClickListener(this.getItemClickListner());
        availableFeedList.setClickable(true);
        infoLabel = (TextView) view.findViewById(R.id.infoLabel);
        feedReaderDbHelper = FeedReaderDbHelper.getInstance(getContext());
        getAvailableFeed();
        return view;
    }

    private void getAvailableFeed() {
        List<RssFeedListElement> titles = new ArrayList<>();

        Cursor cursor = feedReaderDbHelper.getFeeds();

        while (cursor.moveToNext()) {

            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow("title"));
            String url = cursor.getString(
                    cursor.getColumnIndexOrThrow("url"));

            titles.add(new RssFeedListElement(title, url));
        }
        cursor.close();

        if (titles.isEmpty())

        {
            infoLabel.setText("No feeds available");
        } else

        {
            infoLabel.setText("");
            titlesAdapter = new FeedListAdapter(getActivity(),R.layout.rss_feed_list_element, titles);
            availableFeedList.setAdapter(titlesAdapter);
        }


    }


    private AdapterView.OnItemClickListener getItemClickListner() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = titlesAdapter.getItem(position).readRssLink();
                Toast.makeText(getActivity().getApplicationContext(), "Wait, " + url + " is parsing...", Toast.LENGTH_LONG).show();
                try {
                    ParseTask task = new ParseTask();

                    RssFeedFragment rssFeedFragment = task.doInBackground(url);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent, rssFeedFragment).commit();
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Url is not parseable", Toast.LENGTH_LONG).show();
                }

            }
        };
    }


    private class ParseTask extends AsyncTask<String, Void, RssFeedFragment> {

        @Override
        protected RssFeedFragment doInBackground(String... params) {
            String rssUrl = params[0];
            RssFeed rssFeed = null;
            try {
                rssFeed = parserManager.runRssParser(rssUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (rssFeed != null) {
                RssFeedFragment rssFeedFragment = new RssFeedFragment();
                rssFeedFragment.setFeedToDisplay(rssFeed);
                return rssFeedFragment;
            }
            return null;
        }
    }
}
