package com.android.rhythm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.content.Intent;
import android.content.res.Resources;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//welcome page
		setContentView(R.layout.activity_main);
		
		//implement class TabHostActivity
		final Intent intent = new Intent(MainActivity.this,
				TabHostActivity.class);
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
					startActivity(intent);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
