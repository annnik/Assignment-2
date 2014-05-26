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
	private boolean turningFlagIsPlaying = false;
	private boolean firstCreate = true;
	MediaPlayer mediaPlayer;
	private static final String TURNING_FLAG = "turningFlag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (firstCreate) {

			isPlayingFlag = false;
			setContentView(R.layout.a_audioplayer);
			Button btn = (Button) findViewById(R.id.btnPlay);
			if (savedInstanceState != null) {
				turningFlagIsPlaying = savedInstanceState.getBoolean(
						TURNING_FLAG, true);
				if (turningFlagIsPlaying == true) {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.playing);

					btn.setText(R.string.pause);

				} else {
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);
					btn.setText(R.string.play);
				}
			} else {
				if ((!turningFlagIsPlaying) && (!isFirstPlayingFlag)) {

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
			if ((!turningFlagIsPlaying) && (firstCreate)) {
				Singleton s = Singleton.getInstance();
				s.Create();
				s.Start();
				TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPlaying.setText(R.string.playing);
				btn.setText(R.string.pause);
				turningFlagIsPlaying = true;
			} else {
				if (turningFlagIsPlaying) {
					Singleton s = Singleton.getInstance();
					s.Stop();
					btn.setText(R.string.play);
					TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
					textStatusPlaying.setText(R.string.paused);

					turningFlagIsPlaying = false;
				} else {
					if ((!turningFlagIsPlaying) && (!firstCreate)) {
						Singleton s = Singleton.getInstance();

						s.Start();
						TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
						textStatusPlaying.setText(R.string.playing);
						btn.setText(R.string.pause);
						turningFlagIsPlaying = true;
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
			turningFlagIsPlaying = true;
		}
		outState.putBoolean(TURNING_FLAG, turningFlagIsPlaying);

		super.onSaveInstanceState(outState);
	}

}
