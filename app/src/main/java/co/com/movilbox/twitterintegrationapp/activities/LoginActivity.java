package co.com.movilbox.twitterintegrationapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.BasicTimelineFilter;
import com.twitter.sdk.android.tweetui.FilterValues;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineFilter;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import co.com.movilbox.twitterintegrationapp.R;
import co.com.movilbox.twitterintegrationapp.beans.ConfigTwitterBeans;
import co.com.movilbox.twitterintegrationapp.beans.UserBeans;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //Beans
    ConfigTwitterBeans configTwitterBeans = new ConfigTwitterBeans();
    UserBeans userBeans = new UserBeans();

    private TwitterLoginButton loginButton;
    private TextView txtUsername, txtName, txtLocation, txtDescription;
    private TwitterSession session;
    private ImageView imgUser;
    private FloatingActionButton fab;
    private ListView listTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(configTwitterBeans.getTWITTER_KEY(), configTwitterBeans.getTWITTER_SECRET());
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        listTweet = (ListView) findViewById(R.id.listTweet);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        imgUser = (ImageView) findViewById(R.id.imgUser);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        fab.setVisibility(View.GONE);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                session = result.data;
                session = Twitter.getSessionManager().getActiveSession();

                txtName.setText("Name : " + session.getUserName());
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this,ListTweetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });



                loginButton.setVisibility(View.GONE);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("elespectador")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        listTweet.setAdapter(adapter);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
