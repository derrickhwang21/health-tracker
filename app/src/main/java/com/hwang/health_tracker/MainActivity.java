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

    CameraDevice mCamera;
    CameraPreview mPreview;
    CameraDevice.StateCallback mPicture;
    private Button capture, switchCamera;
    Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(imageSlide.length);

        carouselView.setSlideInterval(4000);
        carouselView.setViewListener(viewListener);

        createNotificationChannel();

        if(checkAndRequestPermissions()){
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent i  = new Intent(MainActivity.this, CameraPreview.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

        mCamera =  Camera.open();
        mCamera.setDisplayOrientation(90);
        cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);

        capture = (Button) findViewById(R.id.btnCam);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
            }
        });

        switchCamera = (Button) findViewById(R.id.btnSwitch);
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the number of cameras
                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {
                    //release the old camera instance
                    //switch camera, from the front and the back and vice versa

                    releaseCamera();
                    chooseCamera();
                } else {

                }
            }
        });

        mCamera.startPreview();


    }

    private boolean checkAndRequestPermissions(){
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPersmissionNeeded = new ArrayList<>();

        if(cameraPermission != PackageManager.PERMISSION_GRANTED){
            listPersmissionNeeded.add(Manifest.permission.CAMERA);
        }
        if(writePermission != PackageManager.PERMISSION_GRANTED){
            listPersmissionNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!listPersmissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPersmissionNeeded.toArray(new String[listPersmissionNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults){
        Log.d(TAG, "Permission callback called...");
        switch(requestCode){
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                if(grantResults.length > 0){
                    for(int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if(perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "services permission granted");

                        Intent i = new Intent(MainActivity.this, Camera.class);
                        startActivity(i);
                        finish();
                    } else{
                        Log.d(TAG, "Some permissions are not granted ask again");

                        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            showDialogOK("Service Permissions are required for this app", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                      switch(which){
                          case DialogInterface.BUTTON_POSITIVE:
                              checkAndRequestPermissions();
                              break;
                          case DialogInterface.BUTTON_NEGATIVE:
                              finish();
                              break;
                      }
                                }
                            });
                        }

                        else{
                            explain("You need to give some mandatory permissions to continue");
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    private void explain(String msg){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt){
                        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.hwang.health_tracker.CameraPreview")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt){
                        finish();
                    }
                });
        dialog.show();
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

        private int findFrontFacingCamera() {

            int cameraId = -1;
            // Search for the front facing camera
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cameraId = i;
                    cameraFront = true;
                    break;
                }
            }
            return cameraId;

        }

        private int findBackFacingCamera() {
            int cameraId = -1;
            //Search for the back facing camera
            //get the number of cameras
            int numberOfCameras = Camera.getNumberOfCameras();
            //for every camera check
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    cameraId = i;
                    cameraFront = false;
                    break;

                }

            }
            return cameraId;
        }

        public void onResume() {

                super.onResume();
                if(mCamera == null) {
                    mCamera = Camera.open();
                    mCamera.setDisplayOrientation(90);
                    mPicture = getPictureCallback();
                    mPreview.refreshCamera(mCamera);
                    Log.d("nu", "null");
                }else {
                    Log.d("nu","no null");
                }

        }

        public void chooseCamera() {
            //if the camera preview is the front
            if (cameraFront) {
                int cameraId = findBackFacingCamera();
                if (cameraId >= 0) {
                    //open the backFacingCamera
                    //set a picture callback
                    //refresh the preview

                    mCamera = Camera.open(cameraId);
                    mCamera.setDisplayOrientation(90);
                    mPicture = getPictureCallback();
                    mPreview.refreshCamera(mCamera);
                }
            } else {
                int cameraId = findFrontFacingCamera();
                if (cameraId >= 0) {
                    //open the backFacingCamera
                    //set a picture callback
                    //refresh the preview
                    mCamera = Camera.open(cameraId);
                    mCamera.setDisplayOrientation(90);
                    mPicture = getPictureCallback();
                    mPreview.refreshCamera(mCamera);
                }
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            //when on Pause, release camera in order to be used from other applications
            releaseCamera();
        }

        private void releaseCamera() {
            // stop and release camera
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.release();
                mCamera = null;
            }
        }

        private Camera.PictureCallback getPictureCallback() {
            Camera.PictureCallback picture = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Intent intent = new Intent(MainActivity.this,PictureActivity.class);
                    startActivity(intent);
                }
            };
            return picture;
        }
















}
