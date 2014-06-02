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
	boolean isPlayingFlag = false;
	BroadcastReceiver broadcastReceiver;
	public static final String PLAYER_ID = "AUDIOPLAYER_ID";
	public final static String START_PLAYER_ACTION = "play";
	public final static String STOP_PLAYER_ACTION = "pause";

	public void onCreate() {
		super.onCreate();
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				
				if (intent.getAction() == START_PLAYER_ACTION) {
					start();
				} else if (intent.getAction() == STOP_PLAYER_ACTION) {
					stop();
				}
			}
		};
		IntentFilter progressfilter = new IntentFilter(PLAYER_ID);
		registerReceiver(broadcastReceiver, progressfilter);
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
