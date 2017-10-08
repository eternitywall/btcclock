package com.eternitywall.btcclock.clocks;

import android.content.Context;
import android.text.format.DateFormat;

import com.eternitywall.btcclock.Clock;
import com.eternitywall.btcclock.R;

import java.util.Calendar;

/**
 * Created by luca on 26/09/2017.
 */

public class StandardClock extends Clock {

    public StandardClock() {
        super(0, "Standard time", R.drawable.clock);
    }

    public void run(final Context context, final int appWidgetId){
        new Runnable() {
            @Override
            public void run() {
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(System.currentTimeMillis());
                CharSequence time = DateFormat.format("HH:mm", mCalendar);
                CharSequence description = DateFormat.format("d MMM yyyy", mCalendar);

                StandardClock.this.updateListener.callback(context, appWidgetId, time.toString(), description.toString(), StandardClock.this.resource);
            }
        }.run();
    }

}
