package com.eternitywall.btcclock.clocks;

import android.content.Context;

import com.eternitywall.btcclock.Clock;
import com.eternitywall.btcclock.R;

/**
 * Created by luca on 26/09/2017.
 */

public class TimestampClock extends Clock {

    public TimestampClock() {
        super(1, "UNIX Timestamp", R.drawable.time);
    }

    public void run(final Context context){
        new Runnable() {
            @Override
            public void run() {
                String time = String.valueOf(System.currentTimeMillis());
                String description = TimestampClock.this.name;

                TimestampClock.this.updateListener.callback(context, time, description, TimestampClock.this.resource);
            }
        }.run();
    }

}
