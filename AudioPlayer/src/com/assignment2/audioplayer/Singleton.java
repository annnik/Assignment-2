package com.assignment2.audioplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.TextView;

public class Singleton extends android.app.Application {
	MediaPlayer mediaPlayer;// =Create();
	private static Singleton instance;

	public Singleton() {
		instance = this;
		Create();
	}

	public static Context getContext() {
		return instance;
	}

	public static class SingletonHolder {
		public static final Singleton HOLDER_INSTANCE = new Singleton();

	}

	public static Singleton getInstance() {
		return SingletonHolder.HOLDER_INSTANCE;
	}

	protected void Play() {
		releaseMP();
		mediaPlayer.start();
	}

	private void releaseMP() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
				mediaPlayer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void Create() {
		MediaPlayer.create(instance, R.raw.music);

	}

	public void Stop() {

		mediaPlayer.pause();
	}

}