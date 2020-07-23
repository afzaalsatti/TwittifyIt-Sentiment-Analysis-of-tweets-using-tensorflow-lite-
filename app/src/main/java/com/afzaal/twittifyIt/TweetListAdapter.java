package com.afzaal.twittifyIt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.twitter.sdk.android.tweetui.ToggleImageButton;
import com.twitter.sdk.android.tweetui.TweetUi;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class TweetListAdapter extends RecyclerView.Adapter<TweetListAdapter.TweetViewHolder>  {
   static List<List> data;
    Context context;
    private AdapterCallback adapterCallback;
    private Handler handler;
    private TextClassifier textClassifier;
    TweetListAdapter(Context context,List<List> data,TextClassifier textClassifier,Handler handler){

       this.textClassifier=textClassifier;
       this.handler=handler;

        try {
            adapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {

        }
        this.data = data;
        this.context=context;


    }
    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweetlist_adapter, parent, false);
        TweetListAdapter.TweetViewHolder pvh = new TweetListAdapter.TweetViewHolder(v);

        return pvh;

    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        List<String> list=data.get(position);
        Glide.with(context).load(list.get(4)).into(holder.dp);
        holder.name.setText(list.get(0));
        holder.screen_name.setText(list.get(1));
        holder.time.setText(list.get(2));
        holder.text.setText(list.get(3));
        if(list.get(6).equals("true"))
        {
            holder.retweet.setText("Retweeted by "+list.get(0));
        }
        else
        {
            holder.retweet.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public class TweetViewHolder  extends RecyclerView.ViewHolder  {
        ImageView dp,tw__twitter_logo;
        TextView result;
        TextView name,screen_name,time,text,retweet;
          ToggleImageButton tw__tweet_like_button;
        ImageView star;
        ImageButton tw__tweet_share_button;
        private void showResult(final String inputText, final List<TextClassifier.Result> results ,TextView view) {
            // Run on UI thread as we'll updating our app UI

                        String textToShow ="\nResult:\n";
                        for (int i = 0; i < results.size(); i++) {
                            TextClassifier.Result result = results.get(i);
                            textToShow +=
                                    String.format("    %s: %s\n", result.getTitle(), result.getConfidence());
                        }
                        textToShow += "\n";
                        view.setText(textToShow);

                        // Append the result to the UI.
//                        result.append(textToShow);
//                        inputEditText.getText().clear();



        }
        public TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.tw__tweet_author_avatar);
            name=itemView.findViewById(R.id.tw__tweet_author_full_name);
            result=itemView.findViewById(R.id.result);
            tw__twitter_logo=itemView.findViewById(R.id.tw__twitter_logo);
            screen_name=itemView.findViewById(R.id.tw__tweet_author_screen_name);
            time=itemView.findViewById(R.id.tw__tweet_timestamp);
            text=itemView.findViewById(R.id.tw__tweet_text);
            retweet=itemView.findViewById(R.id.tw__tweet_retweeted_by);
             star=itemView.findViewById(R.id.star);
             tw__tweet_share_button=itemView.findViewById(R.id.tw__tweet_share_button);



            retweet.setVisibility(View.VISIBLE);
            tw__tweet_like_button=itemView.findViewById(R.id.tw__tweet_like_button);
            tw__tweet_like_button.setTag(1);
            tw__tweet_like_button.setColorFilter(ContextCompat.getColor(context, R.color.tw__composer_white));
            tw__tweet_share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    share.setType("text/plain");


                    // Add data to the intent, the receiving app will decide
                    // what to do with it.
                    share.putExtra(Intent.EXTRA_SUBJECT, "Tweeted By :"+name+"\n");
                    share.putExtra(Intent.EXTRA_TEXT,text.getText()+"\nShared from TweetifyIt App Download now" );
                    Intent s=Intent.createChooser(share, "Share with Friends!");
                    s.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    context. startActivity(s);

                }
            });
            tw__tweet_like_button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if((int)tw__tweet_like_button.getTag()==1)
                    {
                        tw__tweet_like_button.setColorFilter(ContextCompat.getColor(context, R.color.tw__composer_red));
                        tw__tweet_like_button.setTag(2);
                    }
                    else
                    {
                        tw__tweet_like_button.setColorFilter(ContextCompat.getColor(context, R.color.tw__composer_white));
                        tw__tweet_like_button.setTag(1);
                    }

                    return false;
                }
            });

            tw__twitter_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, text.getText().toString(), Toast.LENGTH_SHORT).show();
                    if(result.getVisibility()==View.VISIBLE)
                    {
                        result.setVisibility(View.GONE);
                        star.setVisibility(View.GONE);
                        tw__twitter_logo.setColorFilter(ContextCompat.getColor(context, R.color.tw__composer_white));
                    }else
                    {
                        result.setVisibility(View.VISIBLE);
                        star.setVisibility(View.VISIBLE);
                       // adapterCallback.onClassifyRequest(text.getText().toString(),result);
                        handler.post(
                                () -> {
                                    // Run text classification with TF Lite.
                                    List<TextClassifier.Result> results = textClassifier.classify(text.getText().toString());

                                    // Show classification result on screen
                                    showResult(text.getText().toString(), results,result);
                                });
                        tw__twitter_logo.setColorFilter(ContextCompat.getColor(context, R.color.tw__composer_blue));
                    }
                }
            });







        }
    }


    public static interface AdapterCallback {
        void onClassifyRequest(String text,TextView view);
    }
}
