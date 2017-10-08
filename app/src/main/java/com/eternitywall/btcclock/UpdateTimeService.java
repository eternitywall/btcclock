package com.eternitywall.btcclock;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;


public final class UpdateTimeService extends Service implements Clock.UpdateListener {
    static final String UPDATE_TIME = "com.eternitywall.btcclock.action.UPDATE_TIME";

    private final static IntentFilter mIntentFilter = new IntentFilter();

    static {
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        mIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        mIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("widget", "onCreate");
        registerReceiver(mTimeChangedReceiver, mIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mTimeChangedReceiver);
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("widget", "onStartCommand");

        if (intent != null) {
            if (UPDATE_TIME.equals(intent.getAction())) {
                event();
            }
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        Log.d("widget", "onBind");
        return null;
    }

    private final BroadcastReceiver mTimeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            Log.d("widget", "mTimeChangedReceiver");
            event();
        }
    };


    private void event() {
            Context context = getApplicationContext();
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), ClockWidget.class));
        for (int id : ids) {
            Clock clock = ClockWidgetConfigureActivity.loadIdPref(context, id);
            Log.d("widget", String.valueOf(clock.id));
            clock.updateListener = this;
            clock.run(context, id);
        }
    }


    @Override
    public void callback( Context context, int appWidgetId, final String time, String description, final int resource) {

        Log.d("widget", "callback");
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setTextViewText(R.id.tvDescription, description);
        views.setTextViewText(R.id.tvTime, time);
        views.setImageViewResource(R.id.imageView, resource);

        final AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(context);
        mAppWidgetManager.updateAppWidget(appWidgetId,views);
    }
}