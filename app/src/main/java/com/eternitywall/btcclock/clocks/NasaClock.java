package com.eternitywall.btcclock.clocks;

import android.content.Context;
import android.util.Log;

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

public class NasaClock extends Clock {

    public NasaClock() {
        super(4, "NASA NEO: near earth object count", R.drawable.earth);
    }

    String url = "https://api.nasa.gov/neo/rest/v1/stats?api_key=DEMO_KEY";

    public void run(final Context context, final int appWidgetId){
        new Runnable() {
            @Override
            public void run() {

                AsyncHttpClient client = new AsyncHttpClient();
                Log.d(getClass().getName(),"run");

                client.get(url,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(getClass().getName(),response.toString());
                        JSONObject json = null;
                        try {
                            String count = String.valueOf(response.getLong("near_earth_object_count"));
                            NasaClock.this.updateListener.callback(context, appWidgetId, count, NasaClock.this.name, NasaClock.this.resource);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(getClass().getName(),e.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(getClass().getName(),errorResponse.toString());
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d(getClass().getName(),responseString);
                    }
                });


            }
        }.run();
    }

}
