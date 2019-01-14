package com.hwang.health_tracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;


public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channelId";
    CarouselView carouselView;
    int[] imageSlide = {R.drawable.man, R.drawable.man2, R.drawable.man3};
    String[] imageTitle = {"You", "Got", "This"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(imageSlide.length);
        carouselView.setSlideInterval(4000);
        carouselView.setViewListener(viewListener);

        createNotificationChannel();

    }


// credit: https://gist.github.com/BrandonSmith/6679223
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


// credit: https://gist.github.com/BrandonSmith/6679223
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                scheduleNotification(getNotification("Drink some water (5 secs)"), 5000);
                return true;
            case R.id.action_10:
                scheduleNotification(getNotification("Drink some water (10 secs)"), 10000);
                return true;
            case R.id.action_30:
                scheduleNotification(getNotification("Drink some water (30 secs)"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

// credit: https://gist.github.com/BrandonSmith/6679223
    private void scheduleNotification(Notification notification, int delay){

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

// credit: https://gist.github.com/BrandonSmith/6679223
    private Notification getNotification(String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Health Tracker";
            String description = "Water";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void clickWorkout(View view){
        Intent intent = new Intent(this, DisplayWorkout.class);
        startActivity(intent);
    }

    public void clickExcerciseLog(View view){
        Intent intent = new Intent(this, ExcerciseActivityLog.class);
        startActivity(intent);
    }


        ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View sliderView = getLayoutInflater().inflate(R.layout.view_custom, null);

            TextView carouselLabel = (TextView) sliderView.findViewById(R.id.labelTextView);
            ImageView carouselSlide = (ImageView) sliderView.findViewById(R.id.slideImageView);

            carouselSlide.setImageResource(imageSlide[position]);
            carouselLabel.setText(imageTitle[position]);

            carouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

            return sliderView;


        }
    };





}
