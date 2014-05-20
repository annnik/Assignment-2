package com.assignment2.audioplayer;

import android.content.Context;

public class AudioPlayerApplication extends android.app.Application{
	private static AudioPlayerApplication instance;

	public AudioPlayerApplication() {
		instance = this;
		
	}

	public static Context getContext() {
		return instance;
	}
}
