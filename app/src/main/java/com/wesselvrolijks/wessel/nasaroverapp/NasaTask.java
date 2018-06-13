package com.wesselvrolijks.wessel.nasaroverapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NasaTask extends AsyncTask<String, Void, String> {
    private NasaTaskListener listener;

    public NasaTask(NasaTaskListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        String urlString = "";
        String response = "";

        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();

            reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            response = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
        } catch (MalformedURLException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("TAG", e.getLocalizedMessage());
                    return null;
                }
            }
        }

        return response;
    }

    protected void onProgressUpdate(Integer... progress) {
        Log.i("TAG", progress.toString());
    }

    protected void onPostExecute(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray photos = jsonObject.getJSONObject("photos").
                    getJSONArray("items");
            for(int idx = 0; idx < photos.length(); idx++){
                String imageId = photos.getJSONObject(idx).getString("id");
                //JSONArray album_urls = albums.getJSONObject(idx).getJSONArray("images");
                String imageUrl = photos.getJSONObject(0).getString("img_src");

                NasaItem item = new NasaItem(imageId, imageUrl);

                this.listener.onNasaItemAvailable(item);

            }
        } catch(JSONException e) {
            Log.e("TAG", e.getLocalizedMessage());
        }
    }
}
