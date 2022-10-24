package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    ImageView eggImage;
    Button startButton;
    TextView timerTextView;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;



    public void updateTimer (int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;

        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer() {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        startButton.setText("START");
        counterIsActive = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        eggImage = findViewById(R.id.eggImage);
        startButton = findViewById(R.id.startButton);
        timerTextView = findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counterIsActive) {
                    resetTimer();


                } else {
                    counterIsActive = true;
                    timerSeekBar.setEnabled(false);
                    startButton.setText("STOP");
                    countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimer((int) millisUntilFinished / 1000);

                        }

                        @Override
                        public void onFinish() {
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                            mediaPlayer.start();
                            resetTimer();
                            Log.e("finish", "yes");
                        }
                    }.start();
                }
            }
        });



        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("timer", Integer.toString(progress));
                updateTimer(progress);


                }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
