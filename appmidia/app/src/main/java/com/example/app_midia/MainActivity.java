package com.example.app_midia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private SeekBar barVolume;
    private ImageButton btnStop;
    private ImageButton btnPause;
    private ImageButton btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botstrap();
    }

    private void botstrap() {
        //mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);

        barVolume = findViewById(R.id.barVolume);
        btnStop = findViewById(R.id.btnPause);
        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        bootSeekBar();
        // no final do bootStrap

        // para liberar recursos de mídia
        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayerResources(); // implementar main
            }
        };   // vai pedir para implementar o método, implementar em main
    }

    public void OnClickPlay(View v) {
        if (mediaPlayer != null)
            mediaPlayer.start();
    }

    public void OnClickPause(View v) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void OnClickStop(View v) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    protected void bootSeekBar() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int volMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        barVolume.setMax(volMax);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        barVolume.setProgress(volume);
        barVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStop() {            // se o aplicativo parar (home p.ex)

        releaseMediaPlayerResources();

        super.onStop();
    }

    private void releaseMediaPlayerResources() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();  // libera recursos da memória
        }
    }
}
