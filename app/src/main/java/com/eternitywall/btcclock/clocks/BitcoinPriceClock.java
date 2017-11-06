package com.eternitywall.btcclock.clocks;

import android.content.Context;

import com.eternitywall.btcclock.Clock;
import com.eternitywall.btcclock.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by luca on 26/09/2017.
 */

public class BitcoinPriceClock extends Clock {
    private final static DateFormat formatter = DateFormat.getDateTimeInstance(
            DateFormat.SHORT,
            DateFormat.SHORT);


    public BitcoinPriceClock() {
        super(8, "BITCOIN: last price from coinmarketcap.com", R.drawable.bitcoin);
    }

    String url = "https://api.coinmarketcap.com/v1/ticker/bitcoin/?convert=USD";

    public void run(final Context context, final int appWidgetId){
        new Runnable() {
            @Override
            public void run() {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url,  new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            final JSONObject jsonObject = response.getJSONObject(0);
                            String height = jsonObject.getString("price_usd");
                            Long lastUpdated = Long.parseLong( jsonObject.getString("last_updated") );

                            final Date date = new Date(lastUpdated*1000L);
                            String desc = "Coinmarketcap @ " + formatter.format(date);

                            BitcoinPriceClock.this.updateListener.callback(context, appWidgetId, height, desc, BitcoinPriceClock.this.resource);

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
