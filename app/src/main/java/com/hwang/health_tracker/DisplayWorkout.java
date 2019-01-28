package com.hwang.health_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayWorkout extends AppCompatActivity {

    int buttonClick = 0;

    int seconds = 0;
    boolean startRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_workout);
        showUsername();
    }

    public void onButtonClick(View v){
        buttonClick = buttonClick + 1;
        if(buttonClick == 1){
            Toast.makeText(getApplicationContext(), "Button Clicked first time", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Button clicked count is" + buttonClick, Toast.LENGTH_LONG).show();
        }
        updateCounter(buttonClick);

    }

    public void onClickStart(View view){
        startRun = true;
        Timer();
    }

    public void onClickStop(View view){
        startRun = false;
    }

    public void onClickReset(View view){
        startRun = false;
        seconds = 0;
    }

    public void Timer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run(){
                int mili = seconds%100*10;
                int secs = seconds/100;
                int minutes = secs/60;
                int hours = minutes/3600;
                String time = String.format("%d:%02d:%02d.%03d", hours, minutes, secs, mili);
                timeView.setText(time);
                if(startRun){
                    seconds++;
                }

                handler.postDelayed(this, 0);
            }
        });
    }

    // Increment finger counter for shared preference
    public void updateCounter(int counter) {
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.counter), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.counter), counter);
        editor.commit();
    }
// show shared preference for username
  public void showUsername() {
    Context context = this;
    SharedPreferences sharedPref = context.getSharedPreferences(
            getString(R.string.username), Context.MODE_PRIVATE);
    String username = sharedPref.getString(getString(R.string.username), "Enter Username on homepage");
    TextView userData = findViewById(R.id.username);
    userData.setText("Hello, " + username);
  }
}
