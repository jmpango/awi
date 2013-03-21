package org.awi.ui.server.util;



public class trial {
	private String[]data;
	public trial() {
		data = new String[]{};
		data[0] = "";
	}
	
	public static void main(String[] args){
		String x = "airways Listing";
		
		System.out.println(x.replaceAll(" ", ""));
	}
}


/*
 * startActivityForResult
 * http://www.visituganda.com/directory/?jc=tour&page=1
 * 
 * button.setOnClickListener(new OnClickListener() {
	 
	@Override
	public void onClick(View arg0) {

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:0377778888"));
		startActivity(callIntent);

	}

});*/

/*@Override
public void onItemClick(AdapterView<?> a,
        View v, int position, long id) {
    City city = (City) a.getItemAtPosition(position);
    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
    intent.putExtra("com.example.cities.City", city);
    startActivity(intent);
}
});

In your details activity, get the city from the extras on the intent:

Bundle bundle = getIntent().getExtras();
City city = bundle.getParcelable("com.example.cities.City");*/

/*private long _seconds = 0;
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
}*/


/*String uri = "drawable/rating_ico";
int imageResource = vi
		.getContext()
		.getApplicationContext()
		.getResources()
		.getIdentifier(
				uri,
				null,
				vi.getContext().getApplicationContext()
						.getPackageName());
Drawable image = vi.getContext().getResources()
		.getDrawable(imageResource);
holder.image.setImageDrawable(image);*/

/*PhoneCallListener phoneListener = new PhoneCallListener();
TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);


private class PhoneCallListener extends PhoneStateListener {
		 
		private boolean isPhoneCalling = false;
		public void onCallStateChanged(int state, String incomingNumber) {
 
			if (TelephonyManager.CALL_STATE_RINGING == state) {
			}
 
			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				isPhoneCalling = true;
			}
 
			if (TelephonyManager.CALL_STATE_IDLE == state) {
				if (isPhoneCalling) {
					/*Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
 
					isPhoneCalling = false;
				}
 
			}
		}
	}*/

	
