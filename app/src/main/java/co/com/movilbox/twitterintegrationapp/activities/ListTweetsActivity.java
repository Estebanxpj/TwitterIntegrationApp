package co.com.movilbox.twitterintegrationapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.*;

import java.lang.reflect.Array;

import co.com.movilbox.twitterintegrationapp.R;
import io.fabric.sdk.android.Fabric;

public class ListTweetsActivity extends TwitterActivity implements AdapterView.OnItemClickListener{

    private ListView listTweet;
    private FloatingActionButton fab;
    private String user;
    private TwitterSession session = null;
    private Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
        setContentView(R.layout.activity_list_tweet);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        listTweet = (ListView) findViewById(R.id.listTweet);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
        }

        final UserTimeline userTimeline = new UserTimeline.Builder().screenName(user)
                .maxItemsPerRequest(MAXITEMSREQUEST).build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline).build();
        listTweet.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                intent = new ComposerActivity.Builder(ListTweetsActivity.this).session(session)
                        .createIntent();
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            salir();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salir() {
        Intent intent = new Intent(ListTweetsActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
