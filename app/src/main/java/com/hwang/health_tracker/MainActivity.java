package com.hwang.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int buttonClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        }

}
