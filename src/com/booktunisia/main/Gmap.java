package com.booktunisia.main;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.booktunisia.model.Hotel;
import com.booktunisia.model.containers.HotelsContainer;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class Gmap extends MapActivity {

	GeoPoint geoPoint;
	Projection projection;
	MapView map = null;
	ArrayList<Hotel> listehotels;
	LocationManager locationManager;
	HotelsContainer mContainer;
	MyLocationOverlay me = null;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.gmap);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		map = (MapView) findViewById(R.id.map);
		me = new MyLocationOverlay(this, map);

		map.setBuiltInZoomControls(true);
		map.getController().setCenter(getPoint(36.843184, 10.196241));
		map.getController().setZoom(5);

		map.getOverlays().add(me);
		me.enableMyLocation();

		Drawable marker = getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		try {
			map.getOverlays().add(new SitesOverlay(marker));
		} catch (Exception e) {
			Toast.makeText(Gmap.this, "erreur1 " + e.toString(),
					Toast.LENGTH_LONG).show();
		}

	}

	private GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	@Override
	public void onResume() {
		super.onResume();
		me.enableCompass();
	}

	@Override
	public void onPause() {
		super.onPause();
		me.disableCompass();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			map.setSatellite(!map.isSatellite());
			return (true);
		} else if (keyCode == KeyEvent.KEYCODE_Z) {
			map.displayZoomControls(true);
			return (true);
		}

		return (super.onKeyDown(keyCode, event));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 100, 0, "Map View");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 100:
			map.setSatellite(!map.isSatellite());
			break;
		}
		return true;
	}

	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items = new ArrayList<OverlayItem>();
		private Drawable marker = null;

		public SitesOverlay(Drawable marker) {
			super(marker);
			this.marker = marker;

			mContainer = new HotelsContainer();
			listehotels = mContainer.getAllHotel();
			Toast.makeText(
					Gmap.this,
					"Il y a " + listehotels.size()
							+ " hôtels à votre disposition", Toast.LENGTH_LONG)
					.show();

			// ********************************************************************
			// Location location =
			// locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			// if (location != null) {
			// items.add(new OverlayItem(getPoint(location.getLatitude(),
			// location.getLongitude()), "Ma Position", "Je suis içi"));
			// }

			// ********************************************************************
			int n = 0;
			while (n < listehotels.size()) {
				Hotel temp = listehotels.get(n);
				// *****************************************************************
				if (location != null) {
					Location loc = new Location("Ma Position");
					loc.setLatitude(Double.parseDouble(listehotels.get(n)
							.getLatitude()));
					loc.setLongitude(Double.parseDouble(listehotels.get(n)
							.getLongitude()));
					float distance = location.distanceTo(loc);
					items.add(new OverlayItem(getPoint(
							Double.parseDouble(temp.getLatitude()),
							Double.parseDouble(temp.getLongitude())), temp
							.getNom(), "Hôtel: "+temp.getNom()+" "+temp.getVille()+"\nDistance % position actuelle: "
							+ distance / 1000 + " km"));
				} else
					items.add(new OverlayItem(getPoint(
							Double.parseDouble(temp.getLatitude()),
							Double.parseDouble(temp.getLongitude())), temp
							.getNom(), temp.getVille()));
				n++;
			}

			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return (items.get(i));
		}

		@Override
		public void draw(Canvas canvas, MapView map, boolean shadow) {
			super.draw(canvas, map, shadow);
			boundCenterBottom(marker);
		}

		@Override
		protected boolean onTap(int i) {
			AlertDialog.Builder adb = new AlertDialog.Builder(Gmap.this);
			adb.setTitle("Détails");
			adb.setMessage(items.get(i).getSnippet());
			adb.setPositiveButton("Ok", null);
			adb.show();
			return (true);
		}

		@Override
		public int size() {
			return (items.size());
		}
	}
}
