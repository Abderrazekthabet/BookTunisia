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

public class ImgContainer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static final String link = "http://hotel.alwaysdata.net/mobile/bd/images.php";

	public ArrayList<String> getAllImg(String id_hotel) {
		ArrayList<String> output = new ArrayList<String>();
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
				String id = json_data.getString("hotel_id");
				String src = json_data.getString("src");

				if (id.equals(id_hotel)) {
					output.add(src);
				}
			}
		} catch (JSONException e) {
			Toast.makeText(this, "Error parsing data " + e.toString(),
					Toast.LENGTH_LONG).show();
		}
		return output;
	}

}
