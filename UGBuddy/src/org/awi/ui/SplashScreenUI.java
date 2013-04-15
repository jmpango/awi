package org.awi.ui;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenUI extends BaseActivity {
	private long _seconds = 0;
	private long _splashTime = 5000;
	private boolean _isSplashActive = true;
	private boolean _splashPaused = false;
	private Thread _splashThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_layout);
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
			SplashScreenUI.this.finish();
		} else {
			
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
						Intent intent = new Intent(SplashScreenUI.this, DashboardUI.class);
						startActivity(intent);
					}
				}
			};
			
			_splashThread.start();
			
		}
	}
}
