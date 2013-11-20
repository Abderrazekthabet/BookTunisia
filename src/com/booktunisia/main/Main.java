package com.booktunisia.main;

import java.util.List;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.booktunisia.sensor.PositionSensor;

public class Main extends SherlockActivity {

	// compass
		private static SensorManager mySensorManager;
		private boolean sersorrunning;
		private MyCompassView myCompassView;
		private PositionSensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sensor = new PositionSensor(this);
		// compass
				myCompassView = (MyCompassView)findViewById(R.id.maboussole);
				  
			     mySensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
			     List<Sensor> mySensors = mySensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
			  
			     if(mySensors.size() > 0){
			      mySensorManager.registerListener(mySensorEventListener, mySensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
			      sersorrunning = true;
			      Toast.makeText(this, "Patientez pendant le calcul de la position courante", Toast.LENGTH_LONG).show();
			    
			     }
			     else{
			      sersorrunning = false;
			      finish();
			     }
	}
	
	 private SensorEventListener mySensorEventListener = new SensorEventListener(){
		 @Override
		 public void onAccuracyChanged(Sensor sensor, int accuracy) {
		  // TODO Auto-generated method stub
		 }
		 @Override
		 public void onSensorChanged(SensorEvent event) {
		  // TODO Auto-generated method stub
		  myCompassView.updateDirection((float)event.values[0]);
		 }
		  };

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
			return true;

		case R.id.itemrechercher:
			Intent intent2 = new Intent(this, Rechercher.class);
			this.startActivity(intent2);
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
			finish();
			return true;
		}
		return false;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(sersorrunning){
		 mySensorManager.unregisterListener(mySensorEventListener);
		}
	}
}
