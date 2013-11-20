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
 * 2ème étape du process de réservation
 * içi le client va introduire ses informations personnelles
 */
public class Step2 extends SherlockActivity {

	EditText nom, prenom, cin, tel, email;
	TextView tprix;
	Button suivant, annuler;
	Reservation reservation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step2);

		Bundle bundle = this.getIntent().getExtras();
		reservation = (Reservation) bundle.getSerializable("reserv");

		suivant = (Button) findViewById(R.id.step2button1);
		annuler = (Button) findViewById(R.id.step2button2);

		nom = (EditText) findViewById(R.id.txtnom2);
		prenom = (EditText) findViewById(R.id.txtprenom2);
		cin = (EditText) findViewById(R.id.txtcin2);
		tel = (EditText) findViewById(R.id.txttel2);
		email = (EditText) findViewById(R.id.txtemail2);
		tprix = (TextView) findViewById(R.id.prixstep2);

		tprix.setText("Prix Total en TTC = "+String.valueOf(reservation.getPrix_total()));

		suivant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String _nom = nom.getText().toString();
				String _prenom = prenom.getText().toString();
				int _cin = Integer.parseInt(cin.getText().toString());
				int _tel = Integer.parseInt(tel.getText().toString());
				String _email = email.getText().toString();
				if (_nom != "" && _cin >= 0 && _email != "" && _prenom != ""
						&& _tel >= 0) {
					Reservation reservation2 = new Reservation(reservation
							.getId(), reservation.getNbre_single(), reservation
							.getNbre_double(), reservation.getNbre_triple(),
							reservation.getNbre_nuits(), reservation
									.getRemarque(), reservation.getDatedeb(),
							reservation.getPrix_total(), _nom, _prenom, _cin,
							_email, _tel);
					Intent intent = new Intent(getApplicationContext(),
							Step3.class);
					intent.putExtra("reserv3", reservation2);
					startActivity(intent);
				}
			}
		});

		annuler.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				reset();
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
	
	
	private void reset() {
		nom.setText("");
		prenom.setText("");
		cin.setText("");
		tel.setText("");
		email.setText("");
	}
}
