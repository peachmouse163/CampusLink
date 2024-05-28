package com.example.campuslink.link;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.campuslink.MainActivity;
import com.example.campuslink.R;
import com.example.campuslink.login.LoginActivity;

public class PushNotification {

    //test
    private NotificationManager mNManager;
    private Notification notify1;
    private Bitmap LargeBitmap = null;
    private static final int NOTIFYID_1 = 1;
    private Context mContext;

    public PushNotification(Context context,Bitmap bitmap,NotificationManager manager) {
        //创建大图标的Bitmap
        LargeBitmap = bitmap;
        mNManager =manager;

        mContext = context;
    }

    public void createNotification(String title, String text, String subText, String ticker,Intent intent){

        //定义一个PendingIntent点击Notification后启动一个Activity
        PendingIntent pit = PendingIntent.getActivity(mContext, 0, intent, 0);

        //设置图片,通知标题,发送时间,提示方式等属性
        Notification.Builder mBuilder = new Notification.Builder(mContext);
        mBuilder.setContentTitle(title)                         //标题
                .setContentText(text)                           //内容
                .setSubText(subText)                            //内容下面的一小段文字
                .setTicker(ticker)                              //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())            //设置通知时间
                .setSmallIcon(R.mipmap.ic_launcher)             //设置小图标
                .setLargeIcon(LargeBitmap)                      //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setContentIntent(pit);                        //设置PendingIntent
        notify1 = mBuilder.build();
        mNManager.notify(NOTIFYID_1, notify1);
    }

}
