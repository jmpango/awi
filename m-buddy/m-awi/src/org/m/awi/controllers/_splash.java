package org.m.awi.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Application splash screen.
 * 
 * @author jompango@gmail.com
 *
 */
public class _splash extends Activity {

	private long _seconds = 0;
	private long _splashTime = 5000;
	private boolean _isSplashActive = true;
	private boolean _splashPaused = false;
	private Thread _splashThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.splash_view);

		_splashThread = new Thread() {
			public void run() {
				try {
					while (_isSplashActive && _seconds < _splashTime) {
						if (!_splashPaused)
							_seconds += 100;
						sleep(100);
					}
				} catch (Exception e) {
				} finally {
					Intent intent = new Intent(_splash.this, Dashboard.class);
					startActivity(intent);
				}
			}
		};
		
		_splashThread.start();
	}

}
