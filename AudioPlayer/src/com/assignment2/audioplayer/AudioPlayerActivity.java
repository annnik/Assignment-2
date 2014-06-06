package com.assignment2.audioplayer;

import java.io.IOException;

import android.widget.SeekBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	public final static String PARAM_TIME = "time";
	public final static String PARAM_TASK = "task";
	public final static String PARAM_RESULT = "result";
	public final static String PARAM_STATUS = "status";
	/*
	 * public final static String ACTION_START_PLAYER =
	 * "com.assignment2.audioplayer.ACTION_START_PLAYER"; public final static
	 * String ACTION_STOP_PLAYER =
	 * "com.assignment2.audioplayer.ACTION_STOP_PLAYER";
	 */

	private MediaPlayer mediaPlayer;
	private SeekBar seekBarVolume;
	private TextView currentVolumeNumber;
	private Button buttonPlay;
	private TextView textStatusPlaying;
	private BroadcastReceiver broadcastReceiver;
	private ServiceConnection serviceConnection;
	////private boolean isPlayingFlag;
	private int currentValue;
	private AudioPlayerService.PlayerCustomBinder playerServicebinder;

	public void registerBroadcastReceiver() {

		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getIntExtra(AudioPlayerService.ACTION_PLAYER_ID, 0) == R.string.idle) {
					buttonPlay.setText(R.string.play);
					textStatusPlaying.setText(R.string.idle);
					//isPlayingFlag = false;
				}
			}
		};
		IntentFilter progressfilter = new IntentFilter(
				AudioPlayerService.ACTION_PLAYER_ID);
		registerReceiver(broadcastReceiver, progressfilter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent playerServiceIntent;
		super.onCreate(savedInstanceState);
		registerBroadcastReceiver();
		playerServiceIntent = new Intent(this, AudioPlayerService.class);
		serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder binder) {
				playerServicebinder = (AudioPlayerService.PlayerCustomBinder) binder;
				//isPlayingFlag = ((AudioPlayerService.PlayerCustomBinder) binder)
						//.getService().isPlaying();
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
			}
		};

		bindService(playerServiceIntent, serviceConnection, BIND_AUTO_CREATE);
		setContentView(R.layout.a_audioplayer);
		buttonPlay = (Button) findViewById(R.id.btnPlay);
		textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		textStatusPlaying.setText(R.string.idle);

		if (savedInstanceState == null) {
			startService(new Intent(this, AudioPlayerService.class));
			// startService(playerServiceIntent);
			textStatusPlaying.setText(R.string.idle);
		} else {
			if (savedInstanceState != null)
				updateUI();
		}

		currentVolumeNumber = (TextView) findViewById(R.id.currentVolumeNumber);
		seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolume);
		seekBarVolume.setMax(99);
		seekBarVolume.setOnSeekBarChangeListener(this);
	}

	private void updateUI() {

		textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		if (playerServicebinder.getService().isPlaying()) {

			textStatusPlaying.setText(R.string.paused);
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.play);
		} else  {

			textStatusPlaying.setText(R.string.playing);
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.pause);
		}
	}

	private void playerStart() {
		Intent playerServiceIntent = new Intent(
				AudioPlayerService.ACTION_START_PLAYER);
		AudioPlayerActivity.this.sendBroadcast(playerServiceIntent);
		updateUI();
	}

	private void playerPause() {
		Intent playerServiceIntent = new Intent(
				AudioPlayerService.ACTION_STOP_PLAYER);
		this.sendBroadcast(playerServiceIntent);
		updateUI();
	}

	public void onClickStart(View view) throws IOException {

		switch (view.getId()) {
		case R.id.btnPlay:
			if (!playerServicebinder.getService().isPlaying()) {
				playerStart();
				//isPlayingFlag=true;
				// playerServicebinder.getService().isPlaying() = true;
			} else {
				playerPause();
				//isPlayingFlag=false;
				// playerServicebinder.getService().isPlaying() = false;
			}
			break;
		}
		if (mediaPlayer == null) {
			return;
		}
	}

	private void updateVolume() {
		seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolume);
		currentValue = seekBarVolume.getProgress();
		playerServicebinder.getService().setVolume(currentValue);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		currentVolumeNumber.setText(String.valueOf(seekBar.getProgress()));
		updateVolume();

	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(serviceConnection);
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		updateVolume();
	}

}
