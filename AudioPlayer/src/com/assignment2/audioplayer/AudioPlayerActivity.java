package com.assignment2.audioplayer;

import java.io.IOException;

import android.widget.SeekBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	MediaPlayer mediaPlayer;
	private static final String IS_PLAYING_FLAG = "isPlayingFlag";
	private SeekBar mSeekBar;
	TextView mTextValue;
	private int currentValue;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		boolean isPlayingFlag = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_audioplayer);
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		currentValue = singletonPlayer.currentVolume();
		mTextValue = (TextView) findViewById(R.id.currentVolume);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mSeekBar.setMax(99);
		mSeekBar.setProgress(currentValue);
		mSeekBar.setOnSeekBarChangeListener(this);

		if ((savedInstanceState != null)
				&& (singletonPlayer.returnMediaplayer() != null)) {

			isPlayingFlag = savedInstanceState
					.getBoolean(IS_PLAYING_FLAG, true);

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
			if (!isPlayingFlag) {
				playerCreate();
				TextView textStatusIdle = (TextView) findViewById(R.id.statusOfMusic);
				textStatusIdle.setText(R.string.idle);
			}

		}

	}

	private void playerCreate() {

		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		singletonPlayer.create();
	}

	private void playerStart() {
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		singletonPlayer.start();
		TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		textStatusPlaying.setText(R.string.playing);
		btn = (Button) findViewById(R.id.btnPlay);
		btn.setText(R.string.pause);
	}

	private void playerPause() {
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		singletonPlayer.stop();

		TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		textStatusPlaying.setText(R.string.paused);
		btn = (Button) findViewById(R.id.btnPlay);
		btn.setText(R.string.play);
	}

	public void onClickStart(View view) throws IOException {
		boolean isPlayingFlag = playerIsPlaying();

		switch (view.getId()) {
		case R.id.btnPlay:
			if (!isPlayingFlag) {
				playerStart();

				isPlayingFlag = true;
			} else {
				playerPause();
				isPlayingFlag = false;

			}
			break;

		}
		if (mediaPlayer == null)
			return;

	}

	public boolean playerIsPlaying() {
		boolean isPlayingFlag = false;
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		if (singletonPlayer.returnMediaplayer() != null) {
			if (singletonPlayer.isPlaying()) {
				isPlayingFlag = true;
			}
		}
		return isPlayingFlag;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		boolean isPlayingFlag = false;
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		if (singletonPlayer.returnMediaplayer() != null) {
			if (singletonPlayer.isPlaying()) {
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
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		singletonPlayer.setVolume(currentValue);
	}

	@Override
	public void onStartTrackingTouch(SeekBar mSeekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar mSeekBar) {
		// TODO Auto-generated method stub
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		currentValue = mSeekBar.getProgress();
		singletonPlayer.setVolume(currentValue);
	}

}
