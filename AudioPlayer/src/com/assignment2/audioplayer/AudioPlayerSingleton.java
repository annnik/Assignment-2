package com.assignment2.audioplayer;

import android.media.MediaPlayer;

public class AudioPlayerSingleton {
	MediaPlayer mediaPlayer;
	

	public static class SingletonHolder {
		public static final AudioPlayerSingleton HOLDER_INSTANCE = new AudioPlayerSingleton();

	}

	public static AudioPlayerSingleton getInstance() {

		return SingletonHolder.HOLDER_INSTANCE;
	}

	

	public void Create() {

		mediaPlayer = MediaPlayer.create(AudioPlayerApplication.getContext(), R.raw.music);

	}

	public void Start() {

		mediaPlayer.start();
	}

	public void Stop() {

		mediaPlayer.pause();
	}

	public boolean isPlaying() {

		return mediaPlayer.isPlaying();
	}

	public MediaPlayer returnMediaplayer() {
		return mediaPlayer;
	}

}