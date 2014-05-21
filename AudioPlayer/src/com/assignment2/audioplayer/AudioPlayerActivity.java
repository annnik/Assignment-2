package com.assignment2.audioplayer;

import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity {

	public boolean isPlayingFlag = false;
	public boolean isFirstPlayingFlag = false;
	private boolean turningFlag = false;
	private boolean firstCreate = false;
	MediaPlayer mediaPlayer;
	private static final String TURNING_FLAG = "turningFlag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!firstCreate) {
			firstCreate=true;
			isPlayingFlag = false;
			setContentView(R.layout.a_audioplayer);
			if (savedInstanceState != null) {
				turningFlag = savedInstanceState.getBoolean(TURNING_FLAG, true);
				if (turningFlag == false) {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.playing);
					turningFlag = true;
				}

			} else {
				if ((!isPlayingFlag) && (!isFirstPlayingFlag)) {

					TextView textStatusIdle = (TextView) findViewById(R.id.statusOfMusic);
					textStatusIdle.setText(R.string.idle);
				}
				turningFlag = false;
			}
		}

	}

	public void onClickStart(View view) throws IOException {

		switch (view.getId()) {
		// manyetsya na play pri povorote
		case R.id.btnPlay:
			if ((!isPlayingFlag) && (!isFirstPlayingFlag) && (!turningFlag)) {
				Singleton s = Singleton.getInstance();
				mediaPlayer = s.Create();
				mediaPlayer.start();
				TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPlaying.setText(R.string.playing);
				Button btn = (Button) findViewById(R.id.btnPlay);

				btn.setText(R.string.pause);
				isPlayingFlag = true;
				isFirstPlayingFlag = true;
			} else {
				if ((isPlayingFlag) && (isFirstPlayingFlag)) {
					mediaPlayer.pause();
					Button btn = (Button) findViewById(R.id.btnPlay);
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);
					btn.setText(R.string.play);
					isPlayingFlag = false;
				} else if ((!isPlayingFlag) && (isFirstPlayingFlag)) {
					mediaPlayer.start();
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.playing);
					Button btn = (Button) findViewById(R.id.btnPlay);

					btn.setText(R.string.pause);
					isPlayingFlag = true;

				} else if ((!isPlayingFlag) && (!isFirstPlayingFlag)
						&& (turningFlag)) {
					Singleton s = Singleton.getInstance();
					mediaPlayer = s.Create();
					mediaPlayer.start();
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.playing);
					Button btn = (Button) findViewById(R.id.btnPlay);

					btn.setText(R.string.pause);
					isPlayingFlag = true;
				} else if ((isPlayingFlag) && (!isFirstPlayingFlag)
						&& (turningFlag)) {
					mediaPlayer.pause();
					Button btn = (Button) findViewById(R.id.btnPlay);
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);
					btn.setText(R.string.play);
					isPlayingFlag = false;
				}
			}
			break;

		}
		if (mediaPlayer == null)
			return;

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		outState.putBoolean(TURNING_FLAG, turningFlag);

		super.onSaveInstanceState(outState);
	}

}
