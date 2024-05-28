package com.example.campuslink.link;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.campuslink.MainActivity;
import com.example.campuslink.R;
import com.example.campuslink.model.Preview;
import com.example.campuslink.model.VoluModel;
import com.example.campuslink.ui.home.HomeFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PollingService extends IntentService {
    private static final String TAG = "PollingService";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.campuslink.link.action.FOO";
    private static final String ACTION_BAZ = "com.example.campuslink.link.action.BAZ";
    public static final String ACTION_POLL = "com.example.app.ACTION_POLL";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.campuslink.link.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.campuslink.link.extra.PARAM2";
    private static long POLL_INTERVAL = 5000;

    public PollingService() {
        super("PollingService");
        
    }

    private String olderData = "";
    private HashSet<String> containVolu = new HashSet<>();
    private Gson gson = new Gson();
    private Context context;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001){
                if (!olderData.equals((String)msg.obj)){
                    olderData = (String)msg.obj;

                    List<VoluModel> newVoluList = new ArrayList<>();
                    //发出notification
                    for (Preview model :
                            HomeFragment.dataListVolu) {
                        VoluModel volu = (VoluModel) model;
                        if (containVolu.add(volu.getVoluId())) {
                            Log.d(TAG, "initVoluNotificaiton: add new Volu");
                            //发布notification
                            //new PushNotification(LoginActivity.this,BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp),(NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotification("title","text","subText","ticker",new Intent(LoginActivity.this,LoginActivity.class));
                            new PushNotification(context, BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp),(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotification(volu.getTitle(),volu.getVoluContent(),"补充信息","新-志愿活动",new Intent(context, MainActivity.class));
                            newVoluList.add(volu);
                        }
                    }
                    if (newVoluList.size()>0)
                        HomeFragment.dataListVolu.addAll(newVoluList);
                }
            }
        }
    };

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PollingService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PollingService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "onHandleIntent: "+action);
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }else if (ACTION_POLL.equals(action)){
                // 这里实现轮询服务端数据的逻辑
                // 比如使用HttpClient或者OkHttp等工具发起请求
                // 请求完成后可以通过Notification或者其他方式通知用户
                new ReThread(null,handler,1001).start();
                setNextPoll(this);
            }
        }
    }

    // 设置下一次轮询的闹钟
    public void setNextPoll(Context context) {
        this.context = context;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // 要执行的Intent，包括要启动的Service
        Intent pollIntent = new Intent(context, PollingService.class);
        pollIntent.setAction(PollingService.ACTION_POLL);
        //PendingIntent pendingIntent = PendingIntent.getService(context, 0, new Intent(context, PollingService.class), 0);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,pollIntent,0);

        long triggerAtMillis = System.currentTimeMillis() + POLL_INTERVAL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "setNextPoll: >=M");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "setNextPoll: >=KITKAT");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            Log.d(TAG, "setNextPoll: else");
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}