package lpastucha.com.canyonrssreader.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

import lpastucha.com.canyonrssreader.R;
import lpastucha.com.canyonrssreader.model.RssFeed;
import lpastucha.com.canyonrssreader.parser.ParserManager;

public class WelcomeActivity extends AppCompatActivity {
    private static final Logger LOG = Logger.getLogger("WelcomActivity");

    private EditText rssUrlInput;

    private ParserManager paraseManager;

    public WelcomeActivity(){
        this.paraseManager = new ParserManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOG.log(Level.INFO,"Application Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.rssUrlInput = (EditText) findViewById(R.id.rssUrlInput);


    }

    public void runParsing(View view){
        Intent rssContentDisplayer = new Intent(this,ChannelsActivity.class);
        String urlToParse = rssUrlInput.getText().toString();

        try {
            RssFeed parsedChannel = paraseManager.runRssParser(urlToParse);
            rssContentDisplayer.putExtra("RssChanel",parsedChannel);
            startActivity(rssContentDisplayer);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Url is not parseable", Toast.LENGTH_LONG).show();;
        }
    }
    @Override
    protected void onPause() {
        LOG.log(Level.INFO,"Application Pasued");
        super.onPause();
    }

    @Override
    protected void onResume() {
        LOG.log(Level.INFO,"Application Reasumed");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        LOG.log(Level.INFO,"Application Destroyed");
        super.onDestroy();
    }
}
