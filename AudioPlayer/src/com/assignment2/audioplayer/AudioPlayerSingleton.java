package com.assignment2.audioplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioPlayerSingleton {
	private MediaPlayer mediaPlayer;
	private AudioManager audioManager;

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
		audioManager = (AudioManager) AudioPlayerApplication.getContext()
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

	public int currentVolume() {
		int currentValue;
		currentValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		return currentValue;
	}

	public void setVolume(int currentValue) {
		audioManager
				.setStreamVolume(AudioManager.STREAM_MUSIC, currentValue, 0);
	}
}