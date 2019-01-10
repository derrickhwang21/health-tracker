package com.hwang.health_tracker;

import android.content.Intent;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
