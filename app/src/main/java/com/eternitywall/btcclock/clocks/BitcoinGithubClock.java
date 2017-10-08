package com.eternitywall.btcclock.clocks;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.eternitywall.btcclock.Clock;
import com.eternitywall.btcclock.R;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by luca on 26/09/2017.
 */

public class BitcoinGithubClock extends Clock {

    public BitcoinGithubClock() {
        super(5, "BITCOIN: sha of the last commit on github", R.drawable.bitcoin);
    }

    String url = "https://api.github.com/repos/bitcoin/bitcoin/commits/master";

    public void run(final Context context, final int appWidgetId){
        new Runnable() {
            @Override
            public void run() {

                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("User-Agent","Awesome-Octocat-App");
                client.get(url,  new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String height = response.getString("sha");
                            BitcoinGithubClock.this.updateListener.callback(context, appWidgetId, height, BitcoinGithubClock.this.name, BitcoinGithubClock.this.resource);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });


            }
        }.run();
    }

}
