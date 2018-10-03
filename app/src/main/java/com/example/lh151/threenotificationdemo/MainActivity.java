package com.example.lh151.threenotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_norNotification)
    Button btnNorNotification;
    @BindView(R.id.btn_foldNotification)
    Button btnFoldNotification;
    @BindView(R.id.btn_hungNotification)
    Button btnHungNotification;
    @BindView(R.id.rb_pub)
    RadioButton rbPub;
    @BindView(R.id.rb_pri)
    RadioButton rbPri;
    @BindView(R.id.rb_sec)
    RadioButton rbSec;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;

    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void NormalNotification() {
        //创建Builder对象,用PendingIntent跳转
        Notification.Builder mbuilder = new Notification.Builder(this);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hao123.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        //通过Builder给Notification添加属性
        mbuilder.setContentIntent(pendingIntent);
        mbuilder.setSmallIcon(R.drawable.ad);
        mbuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ad));
        mbuilder.setAutoCancel(true);
        mbuilder.setContentTitle("普通通知");
        selectNotificationLevel(mbuilder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            manager.notify(0, mbuilder.build());
        }

    }

    private void FoldNotification() {
        Notification.Builder mbuilder = new Notification.Builder(this);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hao123.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        mbuilder.setContentIntent(pendingIntent);
        mbuilder.setSmallIcon(R.drawable.ad);
        mbuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ad));
        mbuilder.setAutoCancel(true);
        mbuilder.setContentTitle("折叠通知");
        selectNotificationLevel(mbuilder);
        //用RemoteViews 来创建自定义Notification视图
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_fold);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification = mbuilder.build();
            notification.bigContentView = remoteViews;
            manager.notify(1, notification);
        }
    }

    private void HangNotification() {
        Notification.Builder mBuilder = new Notification.Builder(this);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hao123.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                mIntent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ad);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ad));
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle("悬挂式通知");
        selectNotificationLevel(mBuilder);
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, MainActivity.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0,
                hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setFullScreenIntent(hangPendingIntent, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            manager.notify(2, mBuilder.build());
        }
    }

    @OnClick({R.id.btn_norNotification, R.id.btn_foldNotification, R.id.btn_hungNotification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_norNotification:
                NormalNotification();
                break;
            case R.id.btn_foldNotification:
                FoldNotification();
                break;
            case R.id.btn_hungNotification:
                HangNotification();
                break;
        }
    }

    private void selectNotificationLevel(Notification.Builder builder) {
        switch (rgGroup.getCheckedRadioButtonId()){
            case R.id.rb_pub:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                    builder.setContentText("public");
                }
                break;
            case R.id.rb_pri:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                    builder.setContentText("private");
                }
                break;
            case R.id.rb_sec:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setVisibility(Notification.VISIBILITY_SECRET);
                    builder.setContentText("secret");
                }
                break;
                default:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                        builder.setContentText("public");
                    }
                    break;
        }
    }
}
