package com.afzaal.twittifyIt;

import android.os.Bundle;
import android.os.Handler;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.design.widget.NavigationView;
import com.afzaal.twittifyIt.TextClassifier.Result;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityScreen extends AppCompatActivity {

    Button show,classifybtn;
    CheckBox person,org;
    LinearLayout layout;
    RecyclerView recyclerView;
EditText inputEditText;
    List data;
TextView result;
LinearLayout text_analysis;
    NavigationView navigationView;
    private DrawerLayout drawer;
    private TextClassifier client;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        show=findViewById(R.id.show);
        person=findViewById(R.id.persons);
        text_analysis=findViewById(R.id.text_analysis);
        org=findViewById(R.id.orgs);
        layout=findViewById(R.id.layout2);
        recyclerView=findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
      //  llm.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        final Person persons=new Person();
        data=persons.initializeData();






//        toggle.syncState();
        client = new TextClassifier(getApplicationContext());

        handler = new Handler();

result=findViewById(R.id.result);
        classifybtn=findViewById(R.id.classify);
        inputEditText = findViewById(R.id.text);
        classifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                classify(inputEditText.getText().toString());
            }
        });

        final MainScreenRecyclerAdapter adapter = new MainScreenRecyclerAdapter(data,getApplicationContext());
        recyclerView.setAdapter(adapter);
        person.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    if(adapter.getItemCount()==10)
                    {
                        adapter.deleteItem(data.subList(5,10));
                    }else
                    {
                        adapter.deleteItem(new ArrayList());
                    }

                }else{

                    if(adapter.getItemCount()==0)
                    {
                        adapter.addItem(data.subList(0,5));
                    }
                    else
                    {

                        adapter.addItem(data);
                    }



                }
            }
        });

        org.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    if(adapter.getItemCount()==10)
                    {
                        adapter.deleteItem(data.subList(0,5));
                    }else
                    {
                        adapter.deleteItem(new ArrayList());
                    }


                }
                else
                {
                    if(adapter.getItemCount()==0)
                    {
                        adapter.addItem(data.subList(5,10));

                    }
                    else
                    {
                        adapter.addItem(data);
                    }

                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility()==View.VISIBLE)
                {
                    layout.setVisibility(View.GONE);
                    text_analysis.setVisibility(View.GONE);
                }
                else
                {
                    layout.setVisibility(View.VISIBLE);
                    text_analysis.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(
                () -> {
                    client.load();
                });
    }
    @Override
    protected void onStop() {
        super.onStop();

        handler.post(
                () -> {
                    client.unload();
                });
    }

    private void classify(final String text) {
        handler.post(
                () -> {
                    // Run text classification with TF Lite.
                    List<Result> results = client.classify(text);

                    // Show classification result on screen
                    showResult(text, results);
                });
    }
    private void showResult(final String inputText, final List<Result> results) {
        // Run on UI thread as we'll updating our app UI
        runOnUiThread(
                () -> {
                    String textToShow = "Input: " + inputText + "\nOutput:\n";
                    for (int i = 0; i < results.size(); i++) {
                        Result result = results.get(i);
                        textToShow +=
                                String.format("    %s: %s\n", result.getTitle(), result.getConfidence());
                    }
                    textToShow += "---------\n";

                    // Append the result to the UI.
                    result.append(textToShow);
                    inputEditText.getText().clear();


                });
    }


}
