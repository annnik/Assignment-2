package com.assignment2.audioplayer;

import android.content.Context;
import android.media.MediaPlayer;

//RENAME THIS CLASS TO AudioPlayer
public class AudioPlayerSingleton {
	MediaPlayer mediaPlayer;
	private static AudioPlayerApplication app;
	

	public static class SingletonHolder {
		public static final AudioPlayerSingleton HOLDER_INSTANCE = new AudioPlayerSingleton();

	}

	public static AudioPlayerSingleton getInstance() {

		return SingletonHolder.HOLDER_INSTANCE;
	}

	

	public void Create() {

		mediaPlayer = MediaPlayer.create(app.getContext(), R.raw.music);

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