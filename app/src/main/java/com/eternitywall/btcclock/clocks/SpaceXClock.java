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

public class SpaceXClock extends Clock {

    public SpaceXClock() {
        super(7, "SPACEX: latest flight number", R.drawable.earth);
    }

    String url = "https://api.spacexdata.com/v1/launches/latest";

    public void run(final Context context){
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
                            String count = String.valueOf(response.getLong("flight_number"));
                            SpaceXClock.this.updateListener.callback(context, count, SpaceXClock.this.name, SpaceXClock.this.resource);

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
