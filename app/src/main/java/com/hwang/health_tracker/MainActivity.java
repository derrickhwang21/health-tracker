package com.hwang.health_tracker;

import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;


public class MainActivity extends AppCompatActivity {

    int buttonClick = 0;

    int seconds = 0;
    boolean startRun;

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



        Timer();
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

        public void onClickStart(View view){
            startRun = true;
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

                handler.postDelayed(this, 1);
            }
        });
        }
}
