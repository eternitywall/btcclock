package com.eternitywall.btcclock;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;


@RequiresApi(api = Build.VERSION_CODES.O)
public class SyncJobService  extends JobService implements Clock.UpdateListener  {
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(this.getPackageName(),"onStartJob");
        Context context = getApplicationContext();
        ClockWidget.tick(context,this);
        start(context);
        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters jobParameters) {
        Log.d(this.getPackageName(),"onStopJob");
        return true;
    }

    public static void stop(final Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancelAll();
    }

    public static void start(final Context context) {
        ComponentName serviceComponent = new ComponentName(context, SyncJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(20 * 1000); // wait at least
        builder.setOverrideDeadline(30 *1000); // maximum delay
        //builder.setPeriodic(10*0000);
        //builder.setPersisted(true);
        //builder.setRequiresDeviceIdle(true); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }


    @Override
    public void callback(final Context context, final int appWidgetId, final String time, final String description, final int resource) {
        ClockWidget.callback(context, appWidgetId, time, description, resource);
    }
}
