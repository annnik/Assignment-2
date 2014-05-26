package com.assignment2.audioplayer;

import java.io.IOException;

import android.widget.SeekBar;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	public boolean isPlayingFlag = false;
	private boolean firstCreate = true;
	MediaPlayer mediaPlayer;
	private static final String IS_PLAYING_FLAG = "isPlayingFlag";
	private SeekBar mSeekBar;
	TextView mTextValue;
	private int currentValue;
	AudioManager mAudioManager;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_audioplayer);
		

			isPlayingFlag = false;

			mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			currentValue = mAudioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			mTextValue = (TextView) findViewById(R.id.currentVolume);
			mSeekBar = (SeekBar) findViewById(R.id.seekBar);
			mSeekBar.setMax(99);
			mSeekBar.setProgress(currentValue);
			mSeekBar.setOnSeekBarChangeListener(this);
			AudioPlayerSingleton s = AudioPlayerSingleton.getInstance();
			if ((savedInstanceState != null) && (s.returnMediaplayer() != null)) {

				isPlayingFlag = savedInstanceState.getBoolean(IS_PLAYING_FLAG,
						true);
				if (isPlayingFlag == true) {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.playing);
					btn = (Button) findViewById(R.id.btnPlay);
					btn.setText(R.string.pause);

				} else {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);
					btn = (Button) findViewById(R.id.btnPlay);
					btn.setText(R.string.play);
				}

			} else {
				if (!isPlayingFlag)  {

					TextView textStatusIdle = (TextView) findViewById(R.id.statusOfMusic);
					textStatusIdle.setText(R.string.idle);
				}

			
		}

	}

	public void onClickStart(View view) throws IOException {

		switch (view.getId()) {
		case R.id.btnPlay:
			Button btn = (Button) findViewById(R.id.btnPlay);
			if ((!isPlayingFlag) && (firstCreate)) {
				AudioPlayerSingleton s = AudioPlayerSingleton.getInstance();
				s.Create();
				s.Start();
				TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPlaying.setText(R.string.playing);
				btn.setText(R.string.pause);
				isPlayingFlag = true;
			} else {
				if (isPlayingFlag) {
					AudioPlayerSingleton s = AudioPlayerSingleton.getInstance();
					s.Stop();
					btn.setText(R.string.play);
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);

					isPlayingFlag = false;
				} else {
					if ((!isPlayingFlag) && (!firstCreate)) {
						AudioPlayerSingleton s = AudioPlayerSingleton
								.getInstance();
						s.Start();
						TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
						textStatusPlaying.setText(R.string.playing);
						btn.setText(R.string.pause);
						isPlayingFlag = true;
					}
				}

			}

			firstCreate = false;
			break;

		}
		if (mediaPlayer == null)
			return;

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		AudioPlayerSingleton s = AudioPlayerSingleton.getInstance();
		if (s.returnMediaplayer() != null) {
			if (s.isPlaying()) {
				isPlayingFlag = true;
			}
			outState.putBoolean(IS_PLAYING_FLAG, isPlayingFlag);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onProgressChanged(SeekBar mSeekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		mTextValue.setText(String.valueOf(mSeekBar.getProgress()));
		currentValue = mSeekBar.getProgress();
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentValue,
				0);
	}

	@Override
	public void onStartTrackingTouch(SeekBar mSeekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar mSeekBar) {
		// TODO Auto-generated method stub
		currentValue = mSeekBar.getProgress();
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentValue,
				0);

	}

}
