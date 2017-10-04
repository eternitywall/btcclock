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

public class EthereumClock extends Clock {

    public EthereumClock() {
        super(3, "ETHEREUM: blockchain height", R.drawable.ethereum);
    }

    String url = "https://api.blockcypher.com/v1/eth/main";

    public void run(final Context context){
        new Runnable() {
            @Override
            public void run() {

                AsyncHttpClient client = new AsyncHttpClient();

                client.get(url,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JSONObject json = null;
                        try {
                            json = (JSONObject) response;
                            String height = String.valueOf(json.getLong("height"));
                            EthereumClock.this.updateListener.callback(context, height, EthereumClock.this.name, EthereumClock.this.resource);

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
