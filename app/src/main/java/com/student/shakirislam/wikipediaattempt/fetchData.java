package com.student.shakirislam.wikipediaattempt;

import android.os.AsyncTask;
import android.text.Html;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class fetchData extends AsyncTask<Void,Void,Void> {

    //This is the variable that will hold the json file data.
    private String data = "";


    @Override
    protected Void doInBackground(Void... voids) {



        try {
            URL url = new URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Scrum_(software_development)");

            //This is the connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //We are then getting an input stream from the connection
            InputStream inputStream = httpURLConnection.getInputStream();
            //Then a bufferedReader would read the data from the stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            String line = "";

            while(line != null){
              line = bufferedReader.readLine();
                //The json file has multiple lines. The buffered Reader can only read a line at a time.
               data = data + line;

            }

            data = new JSONObject(data).getJSONObject("query").getJSONObject("pages").getJSONObject("4743665").getString("extract");



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        MainActivity.textview.setText(data);
        super.onPostExecute(aVoid);

    }
}
