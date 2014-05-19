package com.assignment2.audioplayer;

import java.io.IOException;

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

public class AudioPlayerActivity extends Activity implements
		OnPreparedListener, OnCompletionListener {

	

	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_audioplayer);
		TextView textStatusIdle = (TextView) findViewById(R.id.statusOfMusic);
		textStatusIdle.setText(R.string.idle);
	}

	public void onClickStart(View view) throws IOException {
		releaseMP();

		switch (view.getId()) {

		case R.id.btnPlay:
			// mediaPlayer = MediaPlayer.create(this, R.raw.music);
			// mediaPlayer.start();
			Singleton s = Singleton.getInstance();
			s.Play();
			TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
			textStatusPlaying.setText(R.string.playing);
			Button btn = (Button) findViewById(R.id.btnPlay);

			btn.setText(R.string.pause);
			break;

		}
		if (mediaPlayer == null)
			return;

	}

	private void releaseMP() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
				mediaPlayer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onClick(View view) {
		if (mediaPlayer == null)
			return;
		switch (view.getId()) {
		case R.id.btnPause:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				TextView textStatusPaused = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPaused.setText(R.string.paused);
			}
			break;

		}
	}
	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}
	
	public void onCompletion(MediaPlayer mp) {
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMP();
	}
}
