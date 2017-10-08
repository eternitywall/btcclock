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

public class BitcoinClock extends Clock {

    public BitcoinClock() {
        super(2, "BITCOIN: blockchain height", R.drawable.bitcoin);
    }

    String url = "https://insight.bitpay.com/api/blocks?limit=1";

    public void run(final Context context, final int appWidgetId){
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
                            json = (JSONObject) response.getJSONArray("blocks").get(0);
                            String height = String.valueOf(json.getLong("height"));
                            BitcoinClock.this.updateListener.callback(context, appWidgetId, height, BitcoinClock.this.name, BitcoinClock.this.resource);

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
