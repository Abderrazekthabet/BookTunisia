package com.booktunisia.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.booktunisia.model.Hotel;
import com.booktunisia.model.containers.HotelsContainer;

public class Consulter extends SherlockActivity {
	/** Called when the activity is first created. */

	private ListView listview = null;
	HotelsContainer myContainer = null;
	public HashMap<String, String> map = null;
	ArrayList<HashMap<String, String>> listehotels = null;
	RatingBar ratingbar;
	private ProgressDialog pDialog;
//	ArrayList<Hotel> hotels = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.consulter);
			listview = (ListView) this.findViewById(R.id.hotels);
			listehotels = new ArrayList<HashMap<String, String>>();
			try {
			//	new AsyncConsult().execute();
			//	ArrayList<Hotel> hotels = new AsyncConsult().doInBackground(null);
				myContainer = new HotelsContainer();
				ArrayList<Hotel> hotels = myContainer.getAllHotel();
//				 ArrayList<Hotel> hotels = new ArrayList<Hotel>();
//				 Hotel h = new Hotel(99, "nom", "ville", "22", "4", 30, 40, 50,
	//			 0, "36", "10");
	//			 hotels.add(h);
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
				Toast.makeText(Consulter.this, "erreur " + e.toString(),
						Toast.LENGTH_LONG).show();
			}

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
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

					ratingbar.setRating(Integer.parseInt(map.get("stars")));
					Hotel hotel = myContainer.getHotelByName(map.get("nom"));

					Bundle objetbunble = new Bundle();
					objetbunble.putSerializable("zz", hotel);

					Intent intent = new Intent(Consulter.this,
							HotelDetails.class);
					intent.putExtras(objetbunble);
					startActivity(intent);
				}

			});

		} catch (Exception x) {
			Toast.makeText(this, "problème de connexion avec le serveur",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu, menu);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.layout.menu, menu);
		// inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Object localObject;
		switch (item.getItemId()) {

		case R.id.itemconsulter:
			Intent intent = new Intent(this, Consulter.class);
			this.startActivity(intent);
			finish();
			return true;
		
		case R.id.itemrechercher:
			Intent intent2 = new Intent(this, Rechercher.class);
			this.startActivity(intent2);
			finish();
			return true;

		case R.id.itemabout:
			 localObject = new Dialog(this);
	         ((Dialog)localObject).setContentView(R.layout.apropos);
	         ((Dialog)localObject).setTitle("A propos");
	         ((Dialog)localObject).setCanceledOnTouchOutside(true);
	         TextView localTextView = (TextView)((Dialog)localObject).findViewById(R.id.txt1);
	         localTextView = (TextView)((Dialog)localObject).findViewById(R.id.site);
	         localTextView.setText(Html.fromHtml("<a href=\"http://hotel.alwaysdata.net/\"><u>" + localTextView.getText().toString() + "</u></a>"));
	         localTextView.setMovementMethod(LinkMovementMethod.getInstance());
	         ((Dialog)localObject).show();
			return true;

		case R.id.itemquitter:
			localObject = new Intent("android.intent.action.MAIN");
            ((Intent)localObject).addCategory("android.intent.category.HOME");
            ((Intent)localObject).setFlags(268435456);
            startActivity((Intent)localObject);
 			return true;
		}
		return false;
	}
	
	
	class AsyncConsult extends AsyncTask<Void, Void, ArrayList<Hotel>> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Consulter.this);
			pDialog.setMessage("Chargement..");
	//		pDialog.setIndeterminate(false);
	//		pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

		@Override
		protected ArrayList<Hotel> doInBackground(Void... params) {
			HotelsContainer	myContainer = new HotelsContainer();
			ArrayList<Hotel> hotels = myContainer.getAllHotel();
			return hotels;
		}
	}
}
