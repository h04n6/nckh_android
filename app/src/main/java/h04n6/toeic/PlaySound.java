package h04n6.toeic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.os.Handler;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by hoang on 3/24/2018.
 */

public class PlaySound implements SeekBar.OnSeekBarChangeListener{

    MediaPlayer mediaPlayer;
    Handler seekBarHandler = new Handler();
    SeekBar seekBar;
    Utilites utils;
    Handler mHandler;
    Button button;
    TextView current_time, song_duration;
    int check; //0 = pause; 1 = play

    public PlaySound(SeekBar mSeekBar, Button mButton, TextView msong_duration, TextView mcurrent_time){
        this.seekBar = mSeekBar;
        this.button = mButton;
        utils = new Utilites();
        mHandler = new Handler();
        current_time = mcurrent_time;
        song_duration = msong_duration;
        mediaPlayer = new MediaPlayer();

        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setMax(mediaPlayer.getDuration());
    }

    public void startSound(String url) {
        //start a new sound
        song_duration.setText("");
        current_time.setText("");
        seekBar.setProgress(0);
        button.setBackgroundResource(R.drawable.ic_pause);
        try{
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    updateProgressBar();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    button.setBackgroundResource(R.drawable.ic_play);
                    mHandler.removeCallbacks(mUpdateTimeTask);
                }
            });

            seekBar.setProgress(0);
            seekBar.setMax(100);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //pause sound
    /*public void pauseSound(){
        if(mediaPlayer.isPlaying()){
            mHandler.removeCallbacks(mUpdateTimeTask);
            mediaPlayer.pause();
            button.setTag("PLAY");
            button.setBackgroundResource(R.drawable.ic_play);
        }
    }

    public void startSoundAgain(){
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        mediaPlayer.seekTo(currentPosition);

        updateProgressBar();
    }*/

    //stop the sound before
    public void stopSound(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.reset();
            mHandler.removeCallbacks(mUpdateTimeTask); //todo : stop current runable to start next runable.
            //mediaPlayer.release();
            //todo : notice: once the MediaPlayer object is in the End State, it can no longer be
            //todo : used and there is no way to bring it back to any other state.
            //todo : SO => when u call mediaPlayer.release(), the media player WILL go to that state
        }
    }

    public void updateProgressBar(){
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            song_duration.setText(utils.milliSecondsToTimer(totalDuration));
            current_time.setText(utils.milliSecondsToTimer(currentDuration));

            int progress = (utils.getProgressPercentage(currentDuration, totalDuration));
            seekBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        mediaPlayer.seekTo(currentPosition);

        updateProgressBar();
    }
}
