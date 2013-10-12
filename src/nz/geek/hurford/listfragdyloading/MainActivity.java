package nz.geek.hurford.listfragdyloading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import nz.geek.hurford.listfragdyloading.service.RSSService;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent serviceIntent = new Intent(this, RSSService.class);
		startService(serviceIntent);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
