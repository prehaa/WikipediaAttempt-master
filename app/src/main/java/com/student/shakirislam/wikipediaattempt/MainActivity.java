package com.student.shakirislam.wikipediaattempt;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button click;
    public static TextView summary;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button1);
        summary = (TextView) findViewById(R.id.textView);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // fetchData process = new fetchData();
                //process.execute();
           getWikiPage("Scrum_Sprint");
           showWikiPage("Scrum_(software_development)#Product_owner");

            }
        });

    }

    private void getWikiPage(String title){
        OkHttpClient client = new OkHttpClient();
        String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + title;



        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final Response wikiResponse = response;

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = " ";
                        try {
                            data = wikiResponse.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            String pagenumber = "";
                            Iterator <String> iterator = new JSONObject(data).getJSONObject("query").getJSONObject("pages").keys();
                            while (iterator.hasNext()){
                                pagenumber = iterator.next();
                            }

                            data = new JSONObject(data).getJSONObject("query").getJSONObject("pages").getJSONObject(pagenumber).getString("extract");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        summary.setText(data);

                    }
                });

            }
        });



    }

    private void showWikiPage(String wikititle){
        //intent > actionview
        //url

        String wikiurl = "https://en.wikipedia.org/wiki/" + wikititle;

        Intent wiki = new Intent(Intent.ACTION_VIEW);
        wiki.setData(Uri.parse(wikiurl));
        startActivity(wiki);
    }
}
