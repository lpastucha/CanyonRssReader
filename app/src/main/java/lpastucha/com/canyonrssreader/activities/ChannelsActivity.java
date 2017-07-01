package lpastucha.com.canyonrssreader.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lpastucha.com.canyonrssreader.R;
import lpastucha.com.canyonrssreader.model.RssFeed;

public class ChannelsActivity extends AppCompatActivity {
    private  TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        this.content = (TextView) findViewById(R.id.parsedContent);
        Intent intent = getIntent();
        RssFeed rssFeed = (RssFeed) intent.getSerializableExtra("RssChanel");

    }

    public void displayParsedRssChannel(RssFeed rssFeed) throws Exception{
        content.setText(rssFeed.toString());
    }


}
