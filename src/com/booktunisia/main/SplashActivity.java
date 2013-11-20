package com.booktunisia.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private static final int STOPSPLASH = 0;

	/**
	 * Default duration for the splash screen (milliseconds)
	 */
	private static final long SPLASHTIME = 2000;

	/**
	 * Handler to close this activity and to start automatically
	 * {@link MainActivity} after <code>SPLASHTIME</code> seconds.
	 */
	private final transient Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == STOPSPLASH) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = connectivityManager
						.getActiveNetworkInfo();
				if(activeNetworkInfo != null){
					Intent intent = new Intent(SplashActivity.this,
							Main.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"vérifiez votre connexion internet", 2000).show();
					finish();
				}
			}

			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		final Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	}
}