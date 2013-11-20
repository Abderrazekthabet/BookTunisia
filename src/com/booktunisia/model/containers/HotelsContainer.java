package com.booktunisia.model.containers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.booktunisia.model.Hotel;

public class HotelsContainer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static final String link = "http://hotel.alwaysdata.net/mobile/bd/hotels.php";

	public ArrayList<Hotel> getAllHotel() {
		ArrayList<Hotel> output = new ArrayList<Hotel>();
		InputStream is = null;
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(link);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Toast.makeText(this, "Error in http connection " + e.toString(),
					Toast.LENGTH_LONG).show();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Toast.makeText(this, "Error converting result " + e.toString(),
					Toast.LENGTH_LONG).show();
		}
		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				int id = json_data.getInt("id");
				String nom = json_data.getString("name");
				String ville = json_data.getString("ville");
				String telephone = json_data.getString("telephone");
				String nbre_etoile = "--";
				int stars = json_data.getInt("stars");
				if (stars > 0 && stars < 6)
					nbre_etoile = String.valueOf(stars);
				double prix_chambre_single = Double.parseDouble(json_data
						.getString("prix_single"));
				double prix_chambre_double = Double.parseDouble(json_data
						.getString("prix_double"));
				double prix_chambre_triple = Double.parseDouble(json_data
						.getString("prix_triple"));
				int promo = json_data.getInt("promo");
				String latitude = json_data.getString("latitude");
				String longitude = json_data.getString("longitude");
				String logo = json_data.getString("logo");

				Hotel hotel = new Hotel(id, nom, ville, telephone, nbre_etoile,
						prix_chambre_single, prix_chambre_double,
						prix_chambre_triple, promo, latitude, longitude, logo);
				output.add(hotel);
			}
		} catch (JSONException e) {
			Toast.makeText(this, "Error parsing data " + e.toString(),
					Toast.LENGTH_LONG).show();
		}
		return output;
	}

	public ArrayList<Hotel> getAllHotelPromo() {
		ArrayList<Hotel> output = new ArrayList<Hotel>();
		InputStream is = null;
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(link);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Toast.makeText(this, "Error in http connection " + e.toString(),
					Toast.LENGTH_LONG).show();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Toast.makeText(this, "Error converting result " + e.toString(),
					Toast.LENGTH_LONG).show();
		}
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				int id = json_data.getInt("id");
				String nom = json_data.getString("name");
				String ville = json_data.getString("ville");
				String telephone = json_data.getString("telephone");
				String nbre_etoile = "--";
				String stars = json_data.getString("stars");
				if (Integer.parseInt(stars) > 0 && Integer.parseInt(stars) < 6)
					nbre_etoile = stars;
				double prix_chambre_single = Double.parseDouble(json_data
						.getString("prix_single"));
				double prix_chambre_double = Double.parseDouble(json_data
						.getString("prix_double"));
				double prix_chambre_triple = Double.parseDouble(json_data
						.getString("prix_triple"));
				int promo = Integer.parseInt(json_data.getString("promo"));
				String latitude = json_data.getString("latitude");
				String longitude = json_data.getString("longitude");

				Hotel hotel = new Hotel(id, nom, ville, telephone, nbre_etoile,
						prix_chambre_single, prix_chambre_double,
						prix_chambre_triple, promo, latitude, longitude);
				if (hotel.getPromo() > 0)
					output.add(hotel);
			}
		} catch (JSONException e) {
			Toast.makeText(this, "Error parsing data " + e.toString(),
					Toast.LENGTH_LONG).show();
		}
		return output;
	}

	public Hotel getHotelByName(String nameHotel) {
		Hotel hotel = new Hotel();
		HotelsContainer mycontainer = new HotelsContainer();
		ArrayList<Hotel> listHotels = mycontainer.getAllHotel();
		int i = 0;

		while (i < listHotels.size()) {
			int id = listHotels.get(i).getId();
			String nom = listHotels.get(i).getNom();
			String ville = listHotels.get(i).getVille();
			String telephone = listHotels.get(i).getTelephone();
			String nbre_etoile = listHotels.get(i).getNbre_etoile();
			double prix_chambre_single = listHotels.get(i)
					.getPrix_chambre_single();
			double prix_chambre_double = listHotels.get(i)
					.getPrix_chambre_double();
			double prix_chambre_triple = listHotels.get(i)
					.getPrix_chambre_triple();
			int promo = listHotels.get(i).getPromo();
			String latitude = listHotels.get(i).getLatitude();
			String longitude = listHotels.get(i).getLongitude();

			hotel = new Hotel(id, nom, ville, telephone, nbre_etoile,
					prix_chambre_single, prix_chambre_double,
					prix_chambre_triple, promo, latitude, longitude);

			if (nom.equalsIgnoreCase(nameHotel))
				return hotel;
			else
				i++;
		}

		return hotel;
	}

	public ArrayList<Hotel> getHotelByName2(String nameHotel) {
		ArrayList<Hotel> output = new ArrayList<Hotel>();
		Hotel hotel = new Hotel();
		HotelsContainer mycontainer = new HotelsContainer();
		ArrayList<Hotel> listHotels = mycontainer.getAllHotel();
		int i = 0;

		while (i < listHotels.size()) {
			int id = listHotels.get(i).getId();
			String nom = listHotels.get(i).getNom();
			String ville = listHotels.get(i).getVille();
			String telephone = listHotels.get(i).getTelephone();
			String nbre_etoile = listHotels.get(i).getNbre_etoile();
			double prix_chambre_single = listHotels.get(i)
					.getPrix_chambre_single();
			double prix_chambre_double = listHotels.get(i)
					.getPrix_chambre_double();
			double prix_chambre_triple = listHotels.get(i)
					.getPrix_chambre_triple();
			int promo = listHotels.get(i).getPromo();
			String latitude = listHotels.get(i).getLatitude();
			String longitude = listHotels.get(i).getLongitude();

			hotel = new Hotel(id, nom, ville, telephone, nbre_etoile,
					prix_chambre_single, prix_chambre_double,
					prix_chambre_triple, promo, latitude, longitude);
			if (nom.contains(nameHotel))
				output.add(hotel);

		}

		return output;
	}

}
