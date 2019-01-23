package com.hwang.health_tracker;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    private String TAG = "tag";
    private static final String CHANNEL_ID = "channelId";
    CarouselView carouselView;
    int[] imageSlide = {R.drawable.man, R.drawable.man2, R.drawable.man3};
    String[] imageTitle = {"You", "Got", "This"};
    private static int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(imageSlide.length);

        carouselView.setSlideInterval(4000);
        carouselView.setViewListener(viewListener);

        createNotificationChannel();

      Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
      buttonLoadImage.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

          Intent i = new Intent(
                  Intent.ACTION_PICK,
                  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

          startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
      });
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

  public void clickPictureActivity(View view){
    Intent intent = new Intent(this, PictureActivity.class);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
      Uri selectedImage = data.getData();
      String[] filePathColumn = { MediaStore.Images.Media.DATA };

      Cursor cursor = getContentResolver().query(selectedImage,
              filePathColumn, null, null, null);
      cursor.moveToFirst();

      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
      String picturePath = cursor.getString(columnIndex);
      cursor.close();

      ImageView imageView = (ImageView) findViewById(R.id.imgView);
      imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

    }


  }





















}
