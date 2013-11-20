package com.booktunisia.main;

import java.net.URL;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.booktunisia.model.Hotel;
import com.booktunisia.model.containers.ImgContainer;

public class HotelDetails extends SherlockActivity {

	Bitmap bmp;
	Button btn_reserver;
	ImgContainer imgContainer;
	ArrayList<String> list;
	TextView tel, hotell, promo, prixs, prixd, prixt, ville;
	RatingBar ratingbar;
	Gallery g;
	public static final String url_img = "http://hotel.alwaysdata.net/mobile/hotels/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_details);

		hotell = (TextView) findViewById(R.id.nomhotel);
		ville = (TextView) findViewById(R.id.villehotel);
		tel = (TextView) findViewById(R.id.telhotel);
		promo = (TextView) findViewById(R.id.promohotel);
		prixs = (TextView) findViewById(R.id.prixsinglehotel);
		prixd = (TextView) findViewById(R.id.prixdoublehotel);
		prixt = (TextView) findViewById(R.id.prixtriplehotel);
		ratingbar = (RatingBar) findViewById(R.id.ratingBar1);

		Bundle objetbunble = this.getIntent().getExtras();
		final Hotel h = (Hotel) objetbunble.getSerializable("zz");
		try {
			if (objetbunble != null) {
				hotell.setText(h.getNom());
				ville.setText(h.getVille());
				tel.setText(h.getTelephone());
				promo.setText(String.valueOf(h.getPromo()));
				prixs.setText(String.valueOf(h.getPrix_chambre_single()));
				prixd.setText(String.valueOf(h.getPrix_chambre_double()));
				prixt.setText(String.valueOf(h.getPrix_chambre_triple()));
				ratingbar.setRating(Integer.parseInt(h.getNbre_etoile()));
				ratingbar.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						return true;
					}
			    });
				try {
					imgContainer = new ImgContainer();
					list = imgContainer.getAllImg(String.valueOf(h.getId()));
				} catch (Exception e) {
					Toast.makeText(HotelDetails.this, "erreur " + e.toString(),
							Toast.LENGTH_LONG).show();
				}

				g = (Gallery) findViewById(R.id.gallery);
				g.setAdapter(new ImageAdapter(this));

				btn_reserver = (Button) findViewById(R.id.btn_reserver2);
				btn_reserver.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(HotelDetails.this,
								AddReservation.class);
						intent.putExtra("zzzz", h);
						startActivity(intent);
					}
				});
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					list.get(0) + " " + e.toString(), Toast.LENGTH_LONG).show();
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

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;

		private Integer[] mImageIds = new Integer[list.size()];

		public ImageAdapter(Context c) {
			mContext = c;
			TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.HelloGallery_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			try {

				if (list.get(position) != "null") {
					URL url = new URL(url_img + list.get(position));
					bmp = BitmapFactory.decodeStream(url.openConnection()
							.getInputStream());
				} else
					g.setVisibility(8);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i.setImageBitmap(bmp);
			i.setLayoutParams(new Gallery.LayoutParams(150, 100));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryItemBackground);

			return i;

		}
	}

}
