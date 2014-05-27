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

	private MediaPlayer mediaPlayer;
	private SeekBar seekBarVolume;
	private TextView currentVolumeNumber;
	private Button buttonPlay;
	private TextView textStatusPlaying;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		int currentValue;
		boolean isPlayingFlag = playerIsPlaying();
		setContentView(R.layout.a_audioplayer);
		textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		textStatusPlaying.setText(R.string.idle);
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		currentValue = singletonPlayer.currentVolume();
		currentVolumeNumber = (TextView) findViewById(R.id.currentVolumeNumber);
		seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolume);
		seekBarVolume.setMax(99);
		seekBarVolume.setProgress(currentValue);
		seekBarVolume.setOnSeekBarChangeListener(this);
		if (isPlayingFlag == true) {

			textStatusPlaying.setText(R.string.playing);
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.pause);
		} else {

			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.play);
		}
	}

	private void updateUI() {

		boolean isPlayingFlag = playerIsPlaying();
		textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		if (!isPlayingFlag) {

			textStatusPlaying.setText(R.string.paused);
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.play);
		} else {

			textStatusPlaying.setText(R.string.playing);
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.pause);
		}
	}

	private void playerStart() {

		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		singletonPlayer.start();
		updateUI();
	}

	private void playerPause() {

		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		singletonPlayer.stop();
		updateUI();
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
		if (mediaPlayer == null) {
			return;
		}
	}

	public boolean playerIsPlaying() {

		boolean isPlayingFlag = false;
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		if (singletonPlayer.isPlaying()) {

			isPlayingFlag = true;
		}
		return isPlayingFlag;
	}

	private void updateVolumeLabel() {

		int currentValue;
		AudioPlayerSingleton singletonPlayer = AudioPlayerSingleton
				.getInstance();
		currentValue = seekBarVolume.getProgress();
		singletonPlayer.setVolume(currentValue);
	}

	@Override
	public void onProgressChanged(SeekBar mSeekBar, int progress,
			boolean fromUser) {

		currentVolumeNumber.setText(String.valueOf(mSeekBar.getProgress()));
		updateVolumeLabel();

	}

	@Override
	public void onStartTrackingTouch(SeekBar mSeekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar mSeekBar) {
		updateVolumeLabel();
	}

}
