package com.afzaal.twittifyIt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afzaal.database.DBHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.internal.network.UrlUtils;
import com.twitter.sdk.android.core.Callback;

import com.twitter.sdk.android.core.TwitterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okio.ByteString;

public class MainActivity extends AppCompatActivity implements TweetListAdapter.AdapterCallback {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TwitterLoginButton twitterLoginButton;
    TextView view;
    private ImageView userProfileImageView;
    private TextView userDetailsLabel;
    RecyclerView recyclerView;
List<String> people_links,org_links,headers;
    //twitter auth client required for custom login
    private TwitterAuthClient client;
    private Handler handler;
    private TextClassifier textClassifier;
    int pos;
    String person_name;
    ProgressBar progressBar;
    Button retry_btn,load_btn;
    DBHandler dbHandler;
private TwitterSession getTwitterSession() {
    TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

    //NOTE : if you want to get token and secret too use uncomment the below code
        /*TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;*/

    return session;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        people_links=new ArrayList<>();
       org_links =new ArrayList<>();
        headers =new ArrayList<>();
        textClassifier = new TextClassifier(getApplicationContext());
 pos=getIntent().getExtras().getInt("pos");
        person_name=getIntent().getExtras().getString("name");
        handler = new Handler();
      dbHandler  =new DBHandler(getApplicationContext());
        progressBar=findViewById(R.id.progress);
        retry_btn=findViewById(R.id.retry);
        load_btn=findViewById(R.id.load);

//              view=findViewById(R.id.view);
            //initialize twitter auth client
           // client = new TwitterAuthClient();
        recyclerView=findViewById(R.id.recycler);
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                load_btn.setVisibility(View.GONE);
                try{
                    List<List> data=dbHandler.getData(person_name);
                  setAdapter(data);

                }
                catch (Exception e)
                {
                    retry_btn.setVisibility(View.VISIBLE);

                    load_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }
        });
retry_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        retry_btn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        load_btn.setVisibility(View.GONE);
        defaultLoginTwitter();
    }
});
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);


people_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=realDonaldTrump&count=100");
        people_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=ImranKhanPTI&count=100");
        people_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=narendramodi&count=100");
        people_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=KremlinRussia_E&count=100");
        people_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=XiJingpingReal&count=100");


        org_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=OfficialDGISPR&count=100");
        org_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=pmln_org&count=100");
        org_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=BJP4India&count=100");
        org_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=USArmy&count=100");
        org_links.add("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=adgpi&count=100");


        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");

        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add( "OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");
        headers.add("OAuth oauth_consumer_key=\"your key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"oauth_timestamp\",oauth_nonce=\"oauth_nonce\",oauth_version=\"1.0\",oauth_signature=\"oauth_signature\"");

        defaultLoginTwitter();

        }

    String getHeader(int no)
    {


            return headers.get(pos);

    }
String getUrl(int no,int type)
{

    if(pos<5){
        return people_links.get(pos);
    }else
    {
        int p=pos-5;
        return org_links.get(p);
    }
}




    private String getNonce() {
         SecureRandom RAND = new SecureRandom();
        return String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(RAND.nextLong()));
    }

    String calculateSignature(String signatureBase) {
        try {
            final String key ="MImcxwUBTzPUqX3odEA52qgpAmu4aN34W4GET7K9fsmOJSUSZ0&Y68QBy6E8K9rLtukDxiA53wPwLg6ZXXeFU85IQPzyTrMw";

            // Calculate the signature by passing both the signature base and signing key to the
            // HMAC-SHA1 hashing algorithm
            final byte[] signatureBaseBytes = signatureBase.getBytes(UrlUtils.UTF8);
            final byte[] keyBytes = key.getBytes(UrlUtils.UTF8);
            final SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            final Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            final byte[] signatureBytes = mac.doFinal(signatureBaseBytes);
            return ByteString.of(signatureBytes, 0, signatureBytes.length).base64();
        } catch (InvalidKeyException e) {
            Twitter.getLogger().e(TwitterCore.TAG, "Failed to calculate signature", e);
            return "";
        } catch (NoSuchAlgorithmException e) {
            Twitter.getLogger().e(TwitterCore.TAG, "Failed to calculate signature", e);
            return "";
        } catch (UnsupportedEncodingException e) {
            Twitter.getLogger().e(TwitterCore.TAG, "Failed to calculate signature", e);
            return "";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(
                () -> {
                    textClassifier.load();
                });
    }
    @Override
    protected void onStop() {
        super.onStop();

        handler.post(
                () -> {
                    textClassifier.unload();
                });
    }

    public void classify(final String text,TextView view) {
        handler.post(
                () -> {
                    // Run text classification with TF Lite.
                    List<TextClassifier.Result> results = textClassifier.classify(text);

                    // Show classification result on screen
                    showResult(text, results,view);
                });
    }
private void setAdapter( List<List> data)
{
    int size=data.get(0).size();
    size=1;
    new Thread(new Runnable() {
        @Override
        public void run() {

            dbHandler.createTable();
            dbHandler.deleteOldData(person_name);

            dbHandler.insertData(data);
        }
    }).start();
    final TweetListAdapter adapter = new TweetListAdapter(getApplicationContext(),data,textClassifier,handler);

    this.recyclerView.setAdapter(adapter);

}

    /**
         * method to do Default Twitter Login
         */
        public void defaultLoginTwitter() {


            String args ="&oauth_consumer_key=\"oauth_consumer_key\",oauth_token=\"your outh token\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1578157229\",oauth_nonce=\"29Bp2tirSne\",oauth_version=\"1.0\"";


            String JsonURL=getUrl(1,1);
            final String signature=calculateSignature("GET&"+"https://api.twitter.com/1.1/statuses/user_timeline.json"+args);
          final String nonce=getNonce();
           final String header=getHeader(1);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
List<List> data=new ArrayList<>();

            JsonArrayRequest arrayreq = new JsonArrayRequest(Request.Method.GET,JsonURL,null,
                    // The second parameter Listener overrides the method onResponse() and passes
                    //JSONArray as a parameter


                    new Response.Listener<JSONArray>() {
List<Object> list;
                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONArray response) {
                            try {

                                JSONObject obj;
                                for(int i=0;i<response.length();i++)
                                {
                                    list=new ArrayList<>();
                                    obj=response.getJSONObject(i);



                                    JSONObject a7=obj.getJSONObject("user");
                                    list.add( a7.getString("name"));
                                    list.add(" @"+ a7.getString("screen_name"));
                                    String time=obj.getString("created_at");
                                    time=time.substring(4,10)+" "+time.substring(time.length()-4);
                                    list.add(time);
                                    list.add( obj.getString("text"));
                                    list.add( a7.getString("profile_image_url_https"));
                                    list.add( obj.getString("retweet_count"));
                                    list.add( obj.getString("retweeted"));
                                    list.add( obj.getString("favorite_count"));
                                data.add(list);

                                }

                               int size= data.size();
                                size=0;
                                try
                                {
                                    setAdapter(data);

                                    progressBar.setVisibility(View.GONE);

                                }catch (Exception e)
                                {
                                    System.out.println(e);
                                }


                            }


                            // Try and catch are included to handle any errors due to JSON
                            catch (JSONException e) {
                                // If an error occurs, this prints the error to the log

                                e.printStackTrace();
                            }
                        }
                    },
                    // The final parameter overrides the method onErrorResponse() and passes VolleyError
                    //as a parameter
                    new Response.ErrorListener() {
                        @Override
                        // Handles errors that occur due to Volley
                        public void onErrorResponse(VolleyError error) {
                            VolleyError e=error;
                            Log.e("Volley", "Error"+e);
                            retry_btn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
load_btn.setVisibility(View.VISIBLE);



                        }


                        }

            )
            {
                /** Passing some request headers* */
                @Override
                public Map getHeaders() throws AuthFailureError {

//
                    HashMap headers = new HashMap();

                    headers.put("Authorization", header);

                    return headers;
                }};
            // Adds the JSON array request "arrayreq" to the request queue
            requestQueue.add(arrayreq);




       }


        public void fetchTwitterEmail(final TwitterSession twitterSession) {
            client.requestEmail(twitterSession, new Callback<String>() {
                @Override
                public void success(Result<String> result) {
                    Toast.makeText(MainActivity.this, "User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data, Toast.LENGTH_LONG).show();
                    defaultLoginTwitter();
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(MainActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the twitterAuthClient.
        if (client != null)
            client.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

       
        private void showResult(final String inputText, final List<TextClassifier.Result> results ,TextView view) {
            // Run on UI thread as we'll updating our app UI
            runOnUiThread(
                    () -> {
                        String textToShow = "Input: " + inputText + "\nOutput:\n";
                        for (int i = 0; i < results.size(); i++) {
                            TextClassifier.Result result = results.get(i);
                            textToShow +=
                                    String.format("    %s: %s\n", result.getTitle(), result.getConfidence());
                        }
                        textToShow += "---------\n";
view.setText(textToShow);

                        // Append the result to the UI.
//                        result.append(textToShow);
//                        inputEditText.getText().clear();


                    });
        }

    @Override
    public void onClassifyRequest(String text, TextView view) {
        handler.post(
                () -> {
                    // Run text classification with TF Lite.
                    List<TextClassifier.Result> results = textClassifier.classify(text);

                    // Show classification result on screen
                    showResult(text, results,view);
                });
    }
}
