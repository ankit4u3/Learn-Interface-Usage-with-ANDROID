package in.emapps.vidur;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements Triggers {

	newClass mClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		doSomething();

	}

	public void doSomething() {
		mClass = new newClass(this);
		mClass.calledFromMain();

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

}
