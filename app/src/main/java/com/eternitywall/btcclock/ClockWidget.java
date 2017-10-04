package com.eternitywall.btcclock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link ClockWidgetConfigureActivity ClockWidgetConfigureActivity}
 */
public class ClockWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        Log.d("widget", "onUpdate");
        Intent intent = new Intent(UpdateTimeService.UPDATE_TIME);
        intent.setPackage("com.eternitywall.btcclock");
        context.startService(intent);
    }

    @Override
    public void onDeleted(final Context context, final int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    @Override
    public void onEnabled(final Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("widget", "onEnabled");
        final Intent intent = new Intent(UpdateTimeService.UPDATE_TIME);
        intent.setPackage("com.eternitywall.btcclock");
        context.startService(intent);
    }

    @Override
    public void onDisabled(final Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.stopService(new Intent(context, UpdateTimeService.class));
    }

}

