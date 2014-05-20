package com.assignment2.audioplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
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

	public MediaPlayer Create() {

		return mediaPlayer=MediaPlayer.create(app.getContext(), R.raw.music);

	}

	public void Stop() {

		mediaPlayer.pause();
	}

}