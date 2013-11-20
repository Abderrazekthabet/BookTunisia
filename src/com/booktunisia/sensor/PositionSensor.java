package com.booktunisia.sensor;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.booktunisia.main.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public final class PositionSensor implements LocationListener,
		SensorEventListener {

	public static PositionMarker CURRENT_POSITION = null;

	private final static int SENSOR_REFRESH_MS = 500;
	private long lastAccelerometerUpdate = System.currentTimeMillis();
	private long lastOrientationUpdate = System.currentTimeMillis();
	private double longitude, latitude;
	private long lastGpsUpdate = System.currentTimeMillis();
	private Activity activity = null;
	private long lastRefreshLayoutUpdate = System.currentTimeMillis();

	public PositionSensor(Activity activity) {
		this.activity = activity;

		{
			SensorManager m = (SensorManager) activity
					.getSystemService(Context.SENSOR_SERVICE);
			m.registerListener(this,
					m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);

			m.registerListener(this,
					m.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_NORMAL);

			{
				LocationManager locationManager = (LocationManager) activity
						.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(
				// LocationManager.GPS_PROVIDER,
						LocationManager.NETWORK_PROVIDER, 0, 0, this);
			}
		}
	}

	/**
	 * Met à jour les données longitude,latitude,... dans la vue principale.
	 */
	public void refreshLayout() {

		// Contrôle la fréquence de mise à jour du layout
		{
			long currTime = System.currentTimeMillis();
			if (currTime - lastRefreshLayoutUpdate < SENSOR_REFRESH_MS)
				return;
			lastRefreshLayoutUpdate = System.currentTimeMillis();
		}

		// Récupère la dernière position enregistrée
		CURRENT_POSITION = null;
		{
			CURRENT_POSITION = new PositionMarker(0, Calendar.getInstance(),
					longitude, latitude);
		}

		{

			Geocoder geo = new Geocoder(activity.getApplicationContext());
			LocationManager locationManager = (LocationManager) activity
					.getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			TextView longitude = (TextView) activity
					.findViewById(R.id.textView2);
			TextView latitude = (TextView) activity
					.findViewById(R.id.textView3);
			TextView txtadresse = (TextView) activity
					.findViewById(R.id.textView5);

			longitude.setText("Longitude: " + CURRENT_POSITION.getLongitude());
			latitude.setText("Latitude: " + CURRENT_POSITION.getLatitude());
			txtadresse.setText("Adresse: ");

			try {
				// Ici on récupère la premiere adresse trouvé gràce à la
				// position que l'on a récupéré
				List<Address> adresses = geo.getFromLocation(
						location.getLatitude(), location.getLongitude(), 1);
				if (adresses != null && adresses.size() == 1) {
					Address adresse = adresses.get(0);
					// Si le geocoder a trouver une adresse, alors on l'affiche
					txtadresse.setText(String.format("%s, %s",
							adresse.getAddressLine(0), adresse.getLocality()));
				} else {
					// sinon on affiche un message d'erreur
					txtadresse.setText("catch unable to find address 1");
				}
			} catch (IOException e) {
				// e.printStackTrace();
				txtadresse.setText("catch unable to find address 2");
			}

		} // end-block
	}

	@Override
	public void onLocationChanged(Location location) {
		{
			long currTime = System.currentTimeMillis();
			if (currTime - lastGpsUpdate < SENSOR_REFRESH_MS)
				return;
			lastGpsUpdate = System.currentTimeMillis();
		}
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		refreshLayout();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	/**
	 * Exécuté par les sondes accéléromètre et boussole pour mettre à jour les
	 * attributs x,y,z et angle.
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {

		long currTime = System.currentTimeMillis();

		@SuppressWarnings("unused")
		float values[] = event.values;

		switch (event.sensor.getType()) {

		case Sensor.TYPE_ACCELEROMETER:
			if (currTime - lastAccelerometerUpdate < SENSOR_REFRESH_MS)
				break;
			lastAccelerometerUpdate = System.currentTimeMillis();
			break;

		case Sensor.TYPE_ORIENTATION:

			if (currTime - lastOrientationUpdate < SENSOR_REFRESH_MS)
				break;
			lastOrientationUpdate = System.currentTimeMillis();
			break;

		}

		refreshLayout();

	}

}
