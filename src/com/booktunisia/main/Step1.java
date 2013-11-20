package com.booktunisia.main;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.booktunisia.model.Hotel;
import com.booktunisia.model.Reservation;

/*
 * 1ère étape du process de réservation
 * içi le client va introduire les informations de la réservation
 */

public class Step1 extends SherlockActivity {
	private EditText nbre_single, nbre_double, nbre_triple, nbre_nuits,
			datedeb;
	private TextView prix;
	private Button next, reset;
	private Hotel hotel = null;
	private Reservation reservation = null;
	int _single, _double, _triple, _nuits;
	static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step1);

		Bundle objetbunble = this.getIntent().getExtras();
		hotel = (Hotel) objetbunble.getSerializable("hotelchoisi");

		nbre_single = (EditText) findViewById(R.id.txtsingle);
		nbre_double = (EditText) findViewById(R.id.txtdouble);
		nbre_triple = (EditText) findViewById(R.id.txttriple);
		nbre_nuits = (EditText) findViewById(R.id.txtnuit);
		datedeb = (EditText) findViewById(R.id.txtdatedeb);

		datedeb.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus)
					showDialog(DATE_DIALOG_ID);
				lockUnlock(false, datedeb);
			}
		});
		datedeb.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus)
					showDialog(DATE_DIALOG_ID);
				lockUnlock(false, datedeb);
			}
		});

		prix = (TextView) findViewById(R.id.prixstep1);

		next = (Button) findViewById(R.id.step1button1);
		reset = (Button) findViewById(R.id.step1button2);
		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				reset();
			}
		});

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String _datedeb = datedeb.getText().toString();
				_single = Integer.parseInt(nbre_single.getText().toString());
				_double = Integer.parseInt(nbre_double.getText().toString());
				_triple = Integer.parseInt(nbre_triple.getText().toString());
				_nuits = Integer.parseInt(nbre_nuits.getText().toString());

				if (_nuits > 0) {
					if (_single > 0 || _double > 0 || _triple > 0) {
						reservation = new Reservation(hotel.getId(), _single,
								_double, _triple, _nuits, "", _datedeb,
								getPrice(hotel, _single, _double, _triple,
										_nuits));
						Intent intent = new Intent(getApplicationContext(),
								Step2.class);
						intent.putExtra("reserv", reservation);
						startActivity(intent);
					} else {
						prix.setText("Veuillez taper les informations de réservation");
					}
				} else {
					prix.setText("Veuillez taper le nombre des nuits");
				}
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
		nbre_single.setText("0");
		nbre_double.setText("0");
		nbre_triple.setText("0");
		nbre_nuits.setText("0");
		datedeb.setText("");
	}

	private double getPrice(Hotel hotel, int nbre_single, int nbre_double,
			int nbre_triple, int nbre_nuits) {
		return (nbre_single * hotel.getPrix_chambre_single() + nbre_double
				* hotel.getPrix_chambre_double() + nbre_triple
				* hotel.getPrix_chambre_triple())
				* nbre_nuits;
	}
	
	
	
	
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					if(datedeb.hasFocus()){
						int m = monthOfYear+1;
						datedeb.setText(dayOfMonth+"/"+m+"/"+year);
						lockUnlock(true, datedeb);
					}
				}
			};
		

		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this,mDateSetListener, 2012, 6, Calendar.DAY_OF_MONTH+5);
//			case 1:return new TimePickerDialog(this,mDateSetListener, 9, 0, true);
			}                                                               
			return null;
		}
		

	private void lockUnlock(boolean value, EditText editText) {
		if (value) {
			editText.setFilters(new InputFilter[] { new InputFilter() {

				@Override
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dstart, int dend) {
					// TODO Auto-generated method stub
					return source.length() < 1 ? dest.subSequence(dstart, dend)
							: "";
				}
			} });
		} else {
			editText.setFilters(new InputFilter[] { new InputFilter() {
				@Override
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dstart, int dend) {
					return null;
				}
			} });
		}
	}
}