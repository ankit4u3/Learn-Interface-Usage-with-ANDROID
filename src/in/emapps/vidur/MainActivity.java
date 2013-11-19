package in.emapps.vidur;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Triggers {

	newClass mClass;
	Button search;
	EditText searchremedy;
	TextView rowNumberLabelView;
	TextView epcDataLabelView;
	TextView epcCountLabelView;
	private ListView m_listview;
	SimpleAdapter simpleAdpt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		search = (Button) findViewById(R.id.searchbutton);
		searchremedy = (EditText) findViewById(R.id.searchremedy);
		rowNumberLabelView = (TextView) findViewById(R.id.SNOLabel);
		epcDataLabelView = (TextView) findViewById(R.id.EPCLabel);
		epcCountLabelView = (TextView) findViewById(R.id.COUNTLabel);
		m_listview = (ListView) findViewById(R.id.id_list_view);
		//
		
		// String[] myList = new String[] {"Hello","World","Foo","Bar"};
		// ListView lv = new ListView(this);
		// lv.setAdapter(new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myList));
		// setContentView(lv);

		List<Map<String, String>> planetsList = new ArrayList<Map<String, String>>();
		planetsList.add(createPlanet("planet", "Mercury"));
		planetsList.add(createPlanet("planet", "Venus"));
		planetsList.add(createPlanet("planet", "Mars"));
		planetsList.add(createPlanet("planet", "Jupiter"));
		planetsList.add(createPlanet("planet", "Saturn"));
		planetsList.add(createPlanet("planet", "Uranus"));
		planetsList.add(createPlanet("planet", "Neptune"));

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doSomething(searchremedy.getText().toString());

			}
		});
		
		 String[] items = new String[] {"Item 1", "Item 2", "Item 3"};
		 ArrayAdapter<String> adapter =
		 new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
		 items);
		 

		 m_listview.setAdapter(adapter);
	}

	private HashMap<String, String> createPlanet(String key, String name) {
		HashMap<String, String> planet = new HashMap<String, String>();
		planet.put(key, name);

		return planet;
	}

	public void doSomething() {
		mClass = new newClass(this);
		mClass.calledFromMain();

	}

	public void doSomething(String msg) {
		mClass = new newClass(this);
		mClass.calledFromMain(msg);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void showTost() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTost(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		rowNumberLabelView.setText(msg);
		epcDataLabelView.setText(msg);
		epcCountLabelView.setText(msg);
	}

	@Override
	public void showTost(String msg, String msg_one) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Closing Activity")
				.setMessage("Are you sure you want to close this activity?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (this != null) {
									moveTaskToBack(true);
								} else {
									System.gc();
									finish();
								}
							}
						}).setNegativeButton("No", null).setCancelable(false)
				.show();
	}

	class getOtherCVE extends AsyncTask<String, String, String> {

		// ALL THE DATA FETCHED WILL BE SAVED IN THIS LIST MAP OF ARRAYLIST TYPE
		DatabaseConnector db = new DatabaseConnector(getApplicationContext());
		private String build;
		private String qNum;
		private String question;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			db.open();
		}

		@Override
		protected String doInBackground(String... params) {

			try {

				HttpClient client = new DefaultHttpClient();

				String getURL = "http://emapps.host56.com/php/vulnerability.php?line="
						+ params[0];//

				HttpGet get = new HttpGet(getURL);
				HttpResponse responseGet = client.execute(get);
				HttpEntity resEntityGet = responseGet.getEntity();

				if (resEntityGet != null) {

					InputStream instream = resEntityGet.getContent();
					BufferedReader str = new BufferedReader(
							new InputStreamReader(instream));

					String ans = new String("");
					build = new String("");
					while ((ans = str.readLine()) != null) {
						build = build + ans;

					}

					JSONObject jobj = new JSONObject(build);
					JSONArray arr = jobj.getJSONArray("questions");
					String arrlen = Integer.toString(arr.length());
					// Log.d(

					for (int i = 0; i < arr.length(); i++) {
						JSONObject qs = arr.getJSONObject(i);

						qNum = qs.getString("name");// nick &points
						question = qs.getString("line")
								+ " \n\n Published Date :"
								+ qs.getString("publish_date")
								+ " \n\n Updated  Date :"
								+ qs.getString("update_date");

						db.InsertNote(qNum.trim(), question.trim());
					
						setTitle("Downloading CVE" +qNum);
					}

				}

			}

			catch (Exception e) {
				// TODO: handle exception
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			db.close();

		}

	}
}
