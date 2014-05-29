package com.assignment2.audioplayer;

import java.util.concurrent.ExecutorService;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class AudioPlayerService extends Service {

	ExecutorService es;
	private MediaPlayer mediaPlayer;
	private AudioManager audioManager;
	boolean isPlayingFlag = false;
	public static final String PLAYER_ID = "AUDIOPLAYER_ID";

	public void onCreate() {
		super.onCreate();
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this);
		PendingIntent contentIntent = PendingIntent.getActivity(
				getApplicationContext(), 0, new Intent(),
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(contentIntent);
		Notification notification = mBuilder.build();
		AudioPlayerApplication.getContext();
		audioManager = (AudioManager) AudioPlayerApplication.getContext()
				.getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = MediaPlayer.create(AudioPlayerApplication.getContext(),
				R.raw.music);

		startForeground(1313, notification);
	}

	public void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
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
