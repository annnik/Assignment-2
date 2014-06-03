package com.assignment2.audioplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class AudioPlayerService extends Service {

	private MediaPlayer mediaPlayer;
	private AudioManager audioManager;
	private BroadcastReceiver broadcastStartStopReceiver;
	public final static String ACTION_START_PLAYER = "play";
	public final static String ACTION_STOP_PLAYER = "pause";

	public void registerBroadcastReceiver() {
		broadcastStartStopReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(ACTION_START_PLAYER)) {
					start();
				} else if (intent.getAction().equals(ACTION_STOP_PLAYER)) {
					stop();
				}
			}
		};
		IntentFilter startStopFilter = new IntentFilter();
		startStopFilter.addAction(ACTION_START_PLAYER);
		startStopFilter.addAction(ACTION_STOP_PLAYER);
		registerReceiver(broadcastStartStopReceiver, startStopFilter);
	}

	public void onCreate() {

		super.onCreate();
		registerBroadcastReceiver();
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		PendingIntent contentIntent = PendingIntent.getActivity(
				getApplicationContext(), 0, new Intent(),
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);
		Notification notification = builder.build();
		AudioPlayerApplication.getContext();
		audioManager = (AudioManager) AudioPlayerApplication.getContext()
				.getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = MediaPlayer.create(AudioPlayerApplication.getContext(),
				R.raw.music);
		startForeground(1000, notification);
	}

	public void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
		unregisterReceiver(broadcastStartStopReceiver);
	}

	public int currentVolume() {
		int currentValue;
		currentValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		return currentValue;
	}

	public void start() {
		mediaPlayer.start();
	}

	public void stop() {
		mediaPlayer.pause();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	class PlayerCustomBinder extends Binder {
		AudioPlayerService getService() {
			return AudioPlayerService.this;
		}
	}

	public void setVolume(int currentValue) {
		audioManager
				.setStreamVolume(AudioManager.STREAM_MUSIC, currentValue, 0);
	}

	PlayerCustomBinder binder = new PlayerCustomBinder();

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

}
