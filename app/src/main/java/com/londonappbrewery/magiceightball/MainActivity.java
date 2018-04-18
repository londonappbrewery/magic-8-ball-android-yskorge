package com.londonappbrewery.magiceightball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private float acelVal;
    private float acelLast;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sm;

        final ImageView ballDisplay = (ImageView) findViewById(R.id.image_eightBall);

        final int[] ballArray = {
                R.drawable.ball1,
                R.drawable.ball2,
                R.drawable.ball3,
                R.drawable.ball4,
                R.drawable.ball5
        };

        Button myButton = (Button) findViewById(R.id.askButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int number = random.nextInt(5);

                ballDisplay.setImageResource(ballArray[number]);


            }
        });




        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake=0.00f;

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                acelLast = acelVal;
                acelVal= (float) Math.sqrt((double)(x*x + y*y +z*z));

                float delta = acelVal - acelLast;
                shake = (float) ((float) shake * 0.9+delta);

                if(shake > 12){
                    Random random = new Random();
                    int number = random.nextInt(5);

                    ballDisplay.setImageResource(ballArray[number]);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sm =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);



    }
}
