package com.booktunisia.main;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.booktunisia.model.Hotel;
import com.booktunisia.model.containers.HotelsContainer;

public class Rechercher extends TabActivity implements OnTabChangeListener {

	TabHost tabHost;
	SimpleAdapter adapter;
	HotelsContainer myContainer;
	RatingBar ratingbar;
	Button btn_reserver, btn_visite;
	ListView listview;
	HashMap<String, String> map;
	ArrayList<HashMap<String, String>> listehotels;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.rechercher);

			tabHost = getTabHost();

			tabHost.addTab(tabHost
					.newTabSpec("Découvez nos promotions")
					.setIndicator("",
							getResources().getDrawable(R.drawable.cubes))
					// .setIndicator("Type")
					.setContent(R.id.tab1)

			);

			TabSpec tabSpec = tabHost
					.newTabSpec("Chercher un hôtel géographiquement");
			tabSpec.setIndicator("", getResources().getDrawable(R.drawable.map));
			Context ctx = this.getApplicationContext();
			Intent i = new Intent(ctx, Gmap.class);
			tabSpec.setContent(i);
			tabHost.addTab(tabSpec);

			tabHost.setOnTabChangedListener(this);
			tabHost.setCurrentTab(0);
			tabHost.setup();

			listview = (ListView) this.findViewById(R.id.listviewhotelpromo);
			listehotels = new ArrayList<HashMap<String, String>>();
			try {
				myContainer = new HotelsContainer();
				// ArrayList<Hotel> hotels = myContainer.getAllHotelPromo();
				ArrayList<Hotel> hotels = myContainer.getAllHotelPromo();
				int n = 0;
				while (n < hotels.size()) {
					Hotel temp = hotels.get(n);
					map = new HashMap<String, String>();
					map.put("nom", temp.getNom());
					map.put("ville", temp.getVille());
					map.put("stars", temp.getNbre_etoile());
					listehotels.add(map);
					n++;
				}
			} catch (Exception e) {
				Toast.makeText(Rechercher.this, "erreur " + e.toString(),
						Toast.LENGTH_LONG).show();
			}

			SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
					listehotels, R.layout.hotelitem, new String[] { "nom",
							"ville" }, new int[] { R.id.nomm, R.id.villee });
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int position,
						long id) {

					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, String> map = (HashMap<String, String>) listview
							.getItemAtPosition(position);
					ratingbar = (RatingBar) v.findViewById(R.id.star);
					ratingbar.setVisibility(2);
					btn_reserver = (Button) v.findViewById(R.id.btn_reserver);
					btn_reserver.setVisibility(2);
					btn_visite = (Button) v.findViewById(R.id.btn_visite);
					btn_visite.setVisibility(2);
					ratingbar.setRating(Integer.parseInt(map.get("stars")));
					ratingbar.setOnTouchListener(new OnTouchListener() {
						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							// TODO Auto-generated method stub
							return true;
						}
				    });
					final Hotel hotel = myContainer.getHotelByName(map.get("nom"));

					AlertDialog.Builder adb = new AlertDialog.Builder(
							Rechercher.this);
					adb.setTitle("Information sur l'hôtel");
					adb.setMessage(hotel.toString());
					adb.setPositiveButton("Ok", null);
					adb.show();

					btn_reserver.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intentReserver = new Intent(Rechercher.this,
									AddReservation.class);
							intentReserver.putExtra("zzzz", hotel);
							startActivity(intentReserver);
						}
					});
					btn_visite.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(Rechercher.this,
									Panorama.class);
							startActivity(intent);
						}
					});
				}
			});

		} catch (Exception w) {
			Toast.makeText(this, "erreur " + w.toString(), Toast.LENGTH_LONG)
					.show();

		}
	}

	@Override
	public void onTabChanged(String tabId) {
		Toast.makeText(this, tabId, Toast.LENGTH_SHORT).show();
	}

}
