package layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lpastucha.com.canyonrssreader.R;
import lpastucha.com.canyonrssreader.dao.FeedReaderDbHelper;
import lpastucha.com.canyonrssreader.model.RssFeed;
import lpastucha.com.canyonrssreader.parser.ParserManager;

public class ManageFeeds extends Fragment {
    FeedReaderDbHelper feedReaderDbHelper;

    EditText rssUrl;
    EditText rssTitle;
    Button parseButton;
    public ManageFeeds() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedReaderDbHelper = FeedReaderDbHelper.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_feeds, container, false);
        this.rssUrl = (EditText)view.findViewById(R.id.rssFeedUrl);
        this.rssTitle = (EditText)view.findViewById(R.id.rssFeedTitle);
        this.parseButton = (Button)view.findViewById(R.id.parseButton);
        parseButton.setOnClickListener(new ParseButtonListener());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.feed_manager_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



private class ParseButtonListener implements View.OnClickListener{

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        UrlSaver urlSaver = new UrlSaver();

        urlSaver.doInBackground(rssUrl.getText().toString(),rssTitle.getText().toString());
        Toast.makeText(getActivity().getApplicationContext(), "Rss Feed added", Toast.LENGTH_LONG).show();
//        parseRss(v);
    }
}

private class UrlSaver extends AsyncTask<String,Void,Void>{

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
    protected Void doInBackground(String... params) {
        feedReaderDbHelper.saveUrl(params[0],params[1]);
        return null;
    }
}

    @Override
    public void onDestroy() {
        feedReaderDbHelper.close();
        super.onDestroy();
    }
}
