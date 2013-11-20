package com.booktunisia.main;

import java.util.ArrayList;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.booktunisia.model.JSONParser;
import com.booktunisia.model.Reservation;

/*
 * 4ème étape du process de réservation
 * içi le client faire le paiement de son réservation
 */
public class Step4 extends SherlockActivity {

	private Reservation reservation = null;
	private EditText carte, password;
	private TextView prix;
	private Button terminer;
	private String _carte = "", _password = "";
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static String url_reservation = "http://hotel.alwaysdata.net/mobile/bd/reservation.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step4);

		Bundle bundle = this.getIntent().getExtras();
		reservation = (Reservation) bundle.getSerializable("reserv4");

		carte = (EditText) findViewById(R.id.numerocarte);
		password = (EditText) findViewById(R.id.password);
		
		prix = (TextView) findViewById(R.id.prixstep4);
		prix.setText("Prix Total en TTC = " + String.valueOf(reservation.getPrix_total()));
		
		
		_carte = carte.getText().toString();
		_password = password.getText().toString();
		
		terminer = (Button) findViewById(R.id.step4button1);

		terminer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					new CreateNewReservation().execute();
					final Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					String address = reservation.getEmail_client();
				//	String address = "zwawa.med@gmail.com";
					String subject = "Réservation Hôtel";
					String emailtext = "Les détails de la réservation: \n"
							+ reservation.toString();
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { address });
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							subject);
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							emailtext);
					startActivity(Intent.createChooser(emailIntent,
							"Send mail..."));
		//			Intent intent2 = new Intent(getApplicationContext(), Main.class);
		//			startActivity(intent2);
					finish();
				
			}
		});

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
	
	
	
	class CreateNewReservation extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Step4.this);
			pDialog.setMessage("Réservation..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
//			int id = reservation.getId();
			int id_hotel = reservation.getId_hotel();

			int nbre_single = reservation.getNbre_single();
			int nbre_double = reservation.getNbre_double();
			int nbre_triple = reservation.getNbre_triple();
			int nbre_nuits = reservation.getNbre_nuits();
//			String remarque = reservation.getRemarque();
			String remarque = "";
			String datedeb = reservation.getDatedeb();

			String nom_client = reservation.getNom_client();
			String prenom_client = reservation.getPrenom_client();
			int cin_client = reservation.getCin_client();
			String email_client = reservation.getEmail_client();
			int tel_client = reservation.getTel_client();
			double prix_total = reservation.getPrix_total();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id", String.valueOf(id)));
			params.add(new BasicNameValuePair("id_hotel", String
					.valueOf(id_hotel)));
			params.add(new BasicNameValuePair("nbre_single", String
					.valueOf(nbre_single)));
			params.add(new BasicNameValuePair("nbre_double", String
					.valueOf(nbre_double)));
			params.add(new BasicNameValuePair("nbre_triple", String
					.valueOf(nbre_triple)));
			params.add(new BasicNameValuePair("nbre_nuits", String
					.valueOf(nbre_nuits)));
			params.add(new BasicNameValuePair("remarque", remarque));
			params.add(new BasicNameValuePair("datedeb", datedeb));
			params.add(new BasicNameValuePair("nom_client", nom_client));
			params.add(new BasicNameValuePair("prenom_client", prenom_client));
			params.add(new BasicNameValuePair("cin_client", String
					.valueOf(cin_client)));
			params.add(new BasicNameValuePair("email_client", email_client));
			params.add(new BasicNameValuePair("tel_client", String
					.valueOf(tel_client)));
			params.add(new BasicNameValuePair("prix_total", String
					.valueOf(prix_total)));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_reservation, "POST", params);

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}