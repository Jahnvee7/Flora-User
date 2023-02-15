package com.example.florausher;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class videostriming extends AppCompatActivity{
        private VideoView mainVideoView;
        private ImageButton playbtn;
        private TextView currentTime;
        private TextView timer;
        private ProgressBar currentProgress;
        private Uri videouri;
        private ProgressBar buffer;
        private int current = 0;
        private int durration = 0;

        private boolean isPalying;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_videostriming);

            isPalying = false;
            mainVideoView = (VideoView)findViewById(R.id.videoView);
            playbtn = (ImageButton)findViewById(R.id.playbutton);
            currentProgress =(ProgressBar)findViewById(R.id.videoprogress);
            currentProgress.setMax(100);
            currentTime = (TextView)findViewById(R.id.currenttime);
            timer = (TextView)findViewById(R.id.timer);
            buffer = (ProgressBar)findViewById(R.id.bufferProgress);


            videouri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/gardenup-152a0.appspot.com/o/Useful_plants.mp4?alt=media&token=d9070f07-01f9-4567-96ed-ea7c13efcea3");


            mainVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {


                    if(what == mp.MEDIA_INFO_BUFFERING_START)
                    {
                        buffer.setVisibility(View.VISIBLE);

                    }
                    else if(what == mp.MEDIA_INFO_BUFFERING_END){
                        Toast.makeText(videostriming.this,"working2",Toast.LENGTH_SHORT).show();
                        buffer.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
            mainVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    durration = mp.getDuration()/1000;
                    String durationString = String.format("%02d:%02d",durration/60,durration%60);
                    timer.setText(durationString);
                }
            });
            mainVideoView.setVideoURI(videouri);
            mainVideoView.requestFocus();
            mainVideoView.pause();
            isPalying = true;

            playbtn.setImageResource(R.drawable.pause);

            new VideoProgress().execute();

            playbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPalying)
                    {
                        mainVideoView.pause();
                        isPalying = false;
                        playbtn.setImageResource(R.drawable.play);
                        buffer.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mainVideoView.start();
                        isPalying = true;
                        playbtn.setImageResource(R.drawable.pause);
                        buffer.setVisibility(View.INVISIBLE);

                    }
                }
            });


        }

        @Override
        protected void onStop() {
            isPalying = false;
            super.onStop();
        }

public class VideoProgress extends AsyncTask<Void,Integer,Void> {


    @SuppressLint("WrongThread")
    @Override
    protected Void doInBackground(Void... voids) {

        if(isPalying) {

                    /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {*/
            do {
                current = mainVideoView.getCurrentPosition() / 1000;
                try {
                    int currentposition = current * 100 / durration;
                    publishProgress(currentposition);
                } catch (Exception e) {
                }

            } while (currentProgress.getProgress() <= 100);
            //  }


        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        currentProgress.setProgress(values[0]*100/durration);
        String currentString = String.format("%02d:%02d",values[0]/60,values[0]%60);
        currentTime.setText(currentString);

    }
}
    }


