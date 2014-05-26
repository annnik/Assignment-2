package com.assignment2.audioplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

//RENAME THIS CLASS TO AudioPlayer
public class Singleton {
	MediaPlayer mediaPlayer;
	private static AudioPlayerApplication app;
	

	public static class SingletonHolder {
		public static final Singleton HOLDER_INSTANCE = new Singleton();

	}

	public static Singleton getInstance() {

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

	/*public MediaPlayer dataForSeekBar() {
		seek = (SeekBar) getView().findViewById(R.id.seekBar);
		mediaPlayer.setVolume(seek.getProgress(), seek.getProgress());
	}*/
}