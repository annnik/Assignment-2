package com.assignment2.audioplayer;

import java.io.IOException;

import android.widget.SeekBar;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	public boolean isPlayingFlag = false;
	private boolean firstCreate = true;
	MediaPlayer mediaPlayer;
	private static final String IS_PLAYING_FLAG = "isPlayingFlag";
	private SeekBar mSeekBar;
	TextView mTextValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (firstCreate) {

			isPlayingFlag = false;
			setContentView(R.layout.a_audioplayer);
			mTextValue = (TextView) findViewById(R.id.currentVolume);
			mSeekBar = (SeekBar) findViewById(R.id.seekBar);
			mSeekBar.setOnSeekBarChangeListener(this);
			Button btn = (Button) findViewById(R.id.btnPlay);
			if (savedInstanceState != null) {
				isPlayingFlag = savedInstanceState.getBoolean(IS_PLAYING_FLAG,
						true);
				if (isPlayingFlag == true) {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.playing);

					btn.setText(R.string.pause);

				} else {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);
					btn.setText(R.string.play);
				}
			} else {
				if ((!isPlayingFlag) && (firstCreate)) {

					TextView textStatusIdle = (TextView) findViewById(R.id.statusOfMusic);
					textStatusIdle.setText(R.string.idle);
				}

			}
		}

	}

	public void onClickStart(View view) throws IOException {

		switch (view.getId()) {
		case R.id.btnPlay:
			Button btn = (Button) findViewById(R.id.btnPlay);
			if ((!isPlayingFlag) && (firstCreate)) {
				Singleton s = Singleton.getInstance();
				s.Create();
				s.Start();
				TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPlaying.setText(R.string.playing);
				btn.setText(R.string.pause);
				isPlayingFlag = true;
			} else {
				if (isPlayingFlag) {
					Singleton s = Singleton.getInstance();
					s.Stop();
					btn.setText(R.string.play);
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);

					isPlayingFlag = false;
				} else {
					if ((!isPlayingFlag) && (!firstCreate)) {
						Singleton s = Singleton.getInstance();
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
		Singleton s = Singleton.getInstance();
		if (s.isPlaying()) {
			isPlayingFlag = true;
		}
		outState.putBoolean(IS_PLAYING_FLAG, isPlayingFlag);

		super.onSaveInstanceState(outState);
	}

	@Override
	public void onProgressChanged(SeekBar mSeekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		mTextValue.setText(String.valueOf(mSeekBar.getProgress()));

	}

	@Override
	public void onStartTrackingTouch(SeekBar mSeekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar mSeekBar) {
		// TODO Auto-generated method stub
		Singleton s = Singleton.getInstance();
		if (s.returnMediaplayer() == null) {
			s.Create();
		}
		mediaPlayer = s.returnMediaplayer();
		s.returnMediaplayer().setVolume(mSeekBar.getProgress(),
				mSeekBar.getProgress());

	}

}
