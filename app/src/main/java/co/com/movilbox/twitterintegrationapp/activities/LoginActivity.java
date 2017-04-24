package co.com.movilbox.twitterintegrationapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import co.com.movilbox.twitterintegrationapp.R;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends TwitterActivity {

    private TwitterLoginButton loginButton;
    private TextView txtName;
    private TwitterSession session;
    private ImageView imgUser;
    private FloatingActionButton fab;
    private CardView carUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        txtName = (TextView) findViewById(R.id.txtName);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        carUser = (CardView) findViewById(R.id.cardUser);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        fab.setVisibility(View.GONE);
        carUser.setVisibility(View.GONE);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // TwitterActivity.getInstance().core.getSessionManager().getActiveSession()
                session = result.data;
                session = Twitter.getSessionManager().getActiveSession();
                Picasso.with(getApplicationContext()).load("https://wog.ua/images/user_icon.png").into(imgUser);

                txtName.setText(session.getUserName() +" \n" + session.getUserId());
                carUser.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this,ListTweetsActivity.class);
                        intent.putExtra("user",session.getUserName().toString());
                        startActivity(intent);
                        finish();
                    }
                });
                loginButton.setVisibility(View.GONE);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with TwitterActivity failure", exception);
            }
        });

        // TODO: Use a more specific parent
        final LinearLayout lilaTweets = (LinearLayout) findViewById(R.id.lilaTweets);
        // TODO: Base this Tweet ID on some data from elsewhere in your app
        long tweetId = 631879971628183552L;
        TweetUtils.loadTweet (tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                TweetView tweetView = new TweetView(LoginActivity.this, result.data);
                lilaTweets.addView(tweetView);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
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
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
