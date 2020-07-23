package com.afzaal.twittifyIt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class LoginActivity extends AppCompatActivity {
    private TwitterAuthClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig("hvZpggiktXKbpNPtyNUHmaefP","MImcxwUBTzPUqX3odEA52qgpAmu4aN34W4GET7K9fsmOJSUSZ0"))
                .build();

        //finally initialize twitter with created configs
        Twitter.initialize(config);
        client = new TwitterAuthClient();
    }
    public void customLoginTwitter(View view) {
        //check if user is already authenticated or not
        if (getTwitterSession() == null) {

            //if user is not authenticated start authenticating
            client.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;

                    //call fetch email only when permission is granted
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    Toast.makeText(LoginActivity.this, "Failed to authenticate. Please try again."+e, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
           startActivity(new Intent(this,MainActivity.class));
        }
    }
    private TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        //NOTE : if you want to get token and secret too use uncomment the below code
        /*TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;*/

        return session;
    }
}
