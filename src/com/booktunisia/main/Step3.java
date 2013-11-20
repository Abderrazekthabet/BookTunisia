package com.booktunisia.main;

import android.app.Dialog;
import android.content.Intent;
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
import com.booktunisia.model.Reservation;

/*
 * 3ème étape du process de réservation
 * içi l'affichage du bilan de la réservation
 */
public class Step3 extends SherlockActivity {

	Reservation reservation = null;
	TextView txtnom, txtprenom, txtcin, txtemail, txttel,
			txtsingle, txtdouble, txttriple, txtnuits, txtdatedeb;
	TextView txtprix;
	Button payer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step3);

		payer = (Button) findViewById(R.id.step3button1);

		txtnom = (TextView) findViewById(R.id.recaptxtnomm);
		txtprenom = (TextView) findViewById(R.id.recaptxtprenomm);
		txtcin = (TextView) findViewById(R.id.recaptxtcinn);
		txtemail = (TextView) findViewById(R.id.recaptxtemail);
		txttel = (TextView) findViewById(R.id.recaptxttel);

		txtsingle = (TextView) findViewById(R.id.recaptxtsingle);
		txtdouble = (TextView) findViewById(R.id.recaptxtdouble);
		txttriple = (TextView) findViewById(R.id.recaptxttriple);
		txtnuits = (TextView) findViewById(R.id.recaptxtnuits);
		txtdatedeb = (TextView) findViewById(R.id.recaptxtdatedeb);
		txtprix = (TextView) findViewById(R.id.prixstep3);

		Bundle bundle = this.getIntent().getExtras();
		reservation = (Reservation) bundle.getSerializable("reserv3");

		txtnom.setText(reservation.getNom_client());
		txtprenom.setText(reservation.getPrenom_client());
		txtcin.setText(String.valueOf(reservation.getCin_client()));
		txtemail.setText(reservation.getEmail_client());
		txttel.setText(String.valueOf(reservation.getTel_client()));
		txtsingle.setText(String.valueOf(reservation.getNbre_single()));
		txtdouble.setText(String.valueOf(reservation.getNbre_double()));
		txttriple.setText(String.valueOf(reservation.getNbre_triple()));
		txtnuits.setText(String.valueOf(reservation.getNbre_nuits()));
		txtdatedeb.setText(reservation.getDatedeb());
		txtprix.setText("Prix Total en TTC = "+String.valueOf(reservation.getPrix_total()));

		payer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), Step4.class);
				intent.putExtra("reserv4", reservation);
				startActivity(intent);
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


}