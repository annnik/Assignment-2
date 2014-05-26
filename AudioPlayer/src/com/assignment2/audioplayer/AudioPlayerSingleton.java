package com.assignment2.audioplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioPlayerSingleton {
	MediaPlayer mediaPlayer;
	int currentValue;
	AudioManager mAudioManager;

	public static class SingletonHolder {
		public static final AudioPlayerSingleton HOLDER_INSTANCE = new AudioPlayerSingleton();

	}

	public static AudioPlayerSingleton getInstance() {

		return SingletonHolder.HOLDER_INSTANCE;
	}

	private AudioPlayerSingleton() {
		create();
	}

	private void create() {
		AudioPlayerApplication.getContext();
		mAudioManager = (AudioManager) AudioPlayerApplication.getContext()
				.getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = MediaPlayer.create(AudioPlayerApplication.getContext(),
				R.raw.music);

	}

	public void start() {

		mediaPlayer.start();
	}

	public void stop() {

		mediaPlayer.pause();
	}

	public boolean isPlaying() {

		return mediaPlayer.isPlaying();
	}

	public boolean existanceOfMediaplayer() {
		boolean flag=false;
		if( mediaPlayer==null) flag=false;
		else flag=true;
		return flag;
	}

	public int currentVolume() {

		
		currentValue = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		return currentValue;
	}

	public void setVolume(int currentValue) {
		
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentValue,
				0);
	}
}