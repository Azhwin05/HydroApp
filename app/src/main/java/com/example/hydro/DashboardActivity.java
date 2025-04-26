package com.example.hydro;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity {

    TextView roomTempValue, waterTempValue, phValue;
    SeekBar motorSlider;
    Handler handler;
    Random random;
    boolean isPumpOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Link UI elements
        roomTempValue = findViewById(R.id.roomTempValue);
        waterTempValue = findViewById(R.id.waterTempValue);
        phValue = findViewById(R.id.phValue);
        motorSlider = findViewById(R.id.motorSlider);

        handler = new Handler();
        random = new Random();

        updateSensorValues();
        setupSliderToggle();
    }

    private void updateSensorValues() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int roomTemp = random.nextInt(15) + 20;  // 20°C - 35°C
                int waterTemp = random.nextInt(10) + 22; // 22°C - 32°C
                float phSensor = 5.0f + random.nextFloat() * 3.0f; // 5.0 - 8.0

                roomTempValue.setText(roomTemp + "°");
                waterTempValue.setText(waterTemp + "°");
                phValue.setText(String.format("%.1f", phSensor));

                updateSensorValues(); // Repeat every 5 sec
            }
        }, 5000);
    }

    private void setupSliderToggle() {
        motorSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Optional: live preview if needed
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();

                if (progress >= 50) {
                    // Snap to ON
                    seekBar.setProgress(100);
                    if (!isPumpOn) {
                        isPumpOn = true;
                        Toast.makeText(getApplicationContext(), "Pump ON", Toast.LENGTH_SHORT).show();
                        // TODO: Turn ON the pump hardware
                    }
                } else {
                    // Snap to OFF
                    seekBar.setProgress(0);
                    if (isPumpOn) {
                        isPumpOn = false;
                        Toast.makeText(getApplicationContext(), "Pump OFF", Toast.LENGTH_SHORT).show();
                        // TODO: Turn OFF the pump hardware
                    }
                }
            }
        });
    }
}
