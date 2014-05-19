package com.assignment2.audioplayer;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.TextView;


public class Singleton {
	//MediaPlayer mediaPlayer;
	final String LOG_TAG = "myLogs";
	MediaPlayer mediaPlayer;//= MediaPlayer.create(this,R.raw.music);
	private Singleton() {
		
	
	}

	public static class SingletonHolder {
		public static final Singleton HOLDER_INSTANCE = new Singleton();
	}

	public static Singleton getInstance() {

		return SingletonHolder.HOLDER_INSTANCE;
	}
	protected void Play(){
		Log.d(LOG_TAG, "TRYING TO START Raw");
		mediaPlayer.create(null, R.raw.music);
		mediaPlayer.start();
	}
	protected void Stop(){
		
		mediaPlayer.pause();
	}

}