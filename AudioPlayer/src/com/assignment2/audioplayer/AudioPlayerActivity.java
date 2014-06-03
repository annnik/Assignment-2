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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	public final static String BROADCAST_ACTION = "com.assignment2.audioplayer";
	public final static String PARAM_TIME = "time";
	public final static String PARAM_TASK = "task";
	public final static String PARAM_RESULT = "result";
	public final static String PARAM_STATUS = "status";
	public final static int STATUS_START = 100;
	public final static int STATUS_FINISH = 200;
	public final static String START_PLAYER_ACTION = "play";
	public final static String STOP_PLAYER_ACTION = "pause";
	private MediaPlayer mediaPlayer;
	private SeekBar seekBarVolume;
	private TextView currentVolumeNumber;
	private Button buttonPlay;
	private TextView textStatusPlaying;
	private BroadcastReceiver broadcastReceiver;	
	private ServiceConnection serviceConnection;
	private boolean isPlayingFlag;
	private int currentValue;
	private AudioManager audioManager;
	private AudioPlayerService.PlayerCustomBinder playerServicebinder;

	public static final String PLAYER_ID = "AUDIOPLAYER_ID";

	public void registerBroadcastReceivers() {

		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				switch (intent.getIntExtra(PLAYER_ID, 0)) {
				case R.string.idle:

					buttonPlay.setText(R.string.play);
					textStatusPlaying.setText(R.string.idle);
					isPlayingFlag = false;
					break;
				}
			}
		};
		IntentFilter progressfilter = new IntentFilter(PLAYER_ID);
		registerReceiver(broadcastReceiver, progressfilter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent playerServiceIntent;
		super.onCreate(savedInstanceState);
		registerBroadcastReceivers();
		playerServiceIntent = new Intent(this, AudioPlayerService.class);
		serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder binder) {
				playerServicebinder = (AudioPlayerService.PlayerCustomBinder) binder;
				isPlayingFlag = ((AudioPlayerService.PlayerCustomBinder) binder)
						.getService().isPlaying();
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
			}
		};
		buttonPlay = (Button) findViewById(R.id.btnPlay);
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		setContentView(R.layout.a_audioplayer);
		textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		textStatusPlaying.setText(R.string.idle);
		if (savedInstanceState == null) {
			startService(new Intent(this, AudioPlayerService.class));
		}
		bindService(playerServiceIntent, serviceConnection, BIND_AUTO_CREATE);
		if ((savedInstanceState != null) && (isPlayingFlag)) {
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.play);
		} else if ((savedInstanceState != null) && (!isPlayingFlag)) {
			buttonPlay = (Button) findViewById(R.id.btnPlay);
			buttonPlay.setText(R.string.pause);
		}

		currentVolumeNumber = (TextView) findViewById(R.id.currentVolumeNumber);
		seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolume);
		seekBarVolume.setMax(99);
		seekBarVolume.setProgress(currentValue);
		seekBarVolume.setOnSeekBarChangeListener(this);

	}

	private void updateUI() {

		textStatusPlaying = (TextView) findViewById(R.id.statusOfMusic);
		if (isPlayingFlag) {

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
		
		//playerServicebinder.getService().start();
		Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);;
		playerServiceIntent.setAction(START_PLAYER_ACTION);
		sendBroadcast(playerServiceIntent);
		updateUI();
	}

	private void playerPause() {
		Intent playerServiceIntent = new Intent(this, AudioPlayerService.class);;
		playerServiceIntent.setAction(STOP_PLAYER_ACTION);
		sendBroadcast(playerServiceIntent);
		updateUI();
	}

	public void onClickStart(View view) throws IOException {

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

		serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder binder) {
				playerServicebinder = (AudioPlayerService.PlayerCustomBinder) binder;
				isPlayingFlag = ((AudioPlayerService.PlayerCustomBinder) binder)
						.getService().isPlaying();
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
			}
		};
		return isPlayingFlag;
	}

	private void updateVolume() {
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
