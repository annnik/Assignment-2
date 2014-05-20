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

public class AudioPlayerActivity extends Activity {

	public boolean isPlayingFlag = false;
	public boolean isFirstPlayingFlag = false;
	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isPlayingFlag = false;
		setContentView(R.layout.a_audioplayer);
		TextView textStatusIdle = (TextView) findViewById(R.id.statusOfMusic);
		textStatusIdle.setText(R.string.idle);
	}

	public void onClickStart(View view) throws IOException {

		switch (view.getId()) {
//manyetsya na play pri povorote
		case R.id.btnPlay:
			if ((!isPlayingFlag)&&(!isFirstPlayingFlag)) {
				Singleton s = Singleton.getInstance();
				mediaPlayer = s.Create();
				mediaPlayer.start();
				TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPlaying.setText(R.string.playing);
				Button btn = (Button) findViewById(R.id.btnPlay);

				btn.setText(R.string.pause);
				isPlayingFlag = true;isFirstPlayingFlag=true;
			} else {
				if(!isPlayingFlag)
				mediaPlayer.pause();
				Button btn = (Button) findViewById(R.id.btnPlay);
				TextView textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
				textStatusPlaying.setText(R.string.paused);
				btn.setText(R.string.play);
				isPlayingFlag = false;
			}
			break;

		}
		if (mediaPlayer == null)
			return;

	}

}
