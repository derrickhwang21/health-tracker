package com.hwang.health_tracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;



public class MainActivity extends AppCompatActivity {



    CarouselView carouselView;
    CarouselView carouselLabel;

    int[] imageSlide = {R.drawable.man, R.drawable.man2, R.drawable.man3};
    int[] imageTitle = {R.drawable.label1, R.drawable.label2, R.drawable.label3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselLabel = (CarouselView) findViewById(R.id.carouselLabel);
        carouselView.setPageCount(imageSlide.length);
        carouselView.setImageListener(imageListener);
        carouselLabel.setPageCount(imageTitle.length);
        carouselLabel.setImageListener(imageListenerLabel);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    public void onClickNotification(View v){
            scheduleNotification(getNotification("Drink some water"), 5000);
    }




    private void scheduleNotification(Notification notification, int delay){

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content){
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification_icon);
        return builder.build();

    }

    public void clickWorkout(View view){
        Intent intent = new Intent(this, DisplayWorkout.class);
        startActivity(intent);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Log.d("position", "" + position +carouselLabel + imageTitle);
            imageView.setImageResource(imageSlide[position]);
        }
    };

    ImageListener imageListenerLabel = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Log.d("position", "" + position +carouselLabel + imageTitle);
            imageView.setImageResource(imageTitle[position]);
        }
    };




}
