package com.assignment2.audioplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioPlayerSingleton {
	MediaPlayer mediaPlayer;
	int currentValue;
	AudioManager mAudioManager;
	private static final String AUDIO_SERVICE="audio"; 
	public static class SingletonHolder {
		public static final AudioPlayerSingleton HOLDER_INSTANCE = new AudioPlayerSingleton();

	}

	public static AudioPlayerSingleton getInstance() {

		return SingletonHolder.HOLDER_INSTANCE;
	}

	public void create() {

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

	public MediaPlayer returnMediaplayer() {
		return mediaPlayer;
	}

	public int currentVolume() {
		
		mAudioManager = (AudioManager) AudioPlayerApplication.getContext().getSystemService(AUDIO_SERVICE);
		currentValue = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		return currentValue;
	}
	public void setVolume(int currentValue)
	{
		mAudioManager = (AudioManager) AudioPlayerApplication.getContext().getSystemService(AUDIO_SERVICE);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentValue,
				0);
	}
}