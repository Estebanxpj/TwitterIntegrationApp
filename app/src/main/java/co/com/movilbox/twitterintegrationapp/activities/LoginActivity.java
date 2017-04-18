package co.com.movilbox.twitterintegrationapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import co.com.movilbox.twitterintegrationapp.R;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;

    private TextView txtUsername, txtName, txtLocation, txtDescription;
    private TwitterSession session;
    private ImageView imgUser;
    private FloatingActionButton fab;


    String userImage;
    private static final String TWITTER_KEY = "7LfSnuf7Z9oFE5fsPwrU1a71H";
    private static final String TWITTER_SECRET = "QQNRr3uIRODsyd8gu5SiN644DbqDoO37WlyPhbT4HyoCHNTAJX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        imgUser = (ImageView) findViewById(R.id.imgUser);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                session = result.data;
                session = Twitter.getSessionManager().getActiveSession();
                txtName.setText("Name : " + session.getUserName());

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this,ListTweetActivity.class);
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

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
