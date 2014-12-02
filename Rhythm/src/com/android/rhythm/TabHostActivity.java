package com.android.rhythm;

import android.app.NotificationManager;
import android.app.TabActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.tab.FindActivity;
import com.android.tab.LiveActivity;
import com.android.tab.MoreActivity;
import com.android.tab.MymusicActivity;
import com.android.rhythm.MainPlaying;
import com.android.rhythm.TabHostActivity;
import com.android.rhythm.R;
import com.android.service.MediaPlayService;
import com.android.resource.GetMusicData;

public class TabHostActivity extends TabActivity {

	View local_music, viewminiplay, mini_play_btn;
	MediaPlayService mediaPlayService;
	public static Button playButton;
	int play_state, pesition;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setting common page for all Tab
		setContentView(R.layout.tabhost_main);
		setView();
		//every Tab and it's Activity class
		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost
				.newTabSpec("Tab1")
				.setIndicator("My music",
						getResources().getDrawable(R.drawable.tab_mymusic_clicked))
				.setContent(new Intent(this, MymusicActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("Tab2")
				.setIndicator("Live",
						getResources().getDrawable(R.drawable.tab_live_clicked))
				.setContent(new Intent(this, LiveActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("Tab3")
				.setIndicator("Find",
						getResources().getDrawable(R.drawable.tab_find_clicked))
				.setContent(new Intent(this, FindActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("Tab4")
				.setIndicator("More",
						getResources().getDrawable(R.drawable.tab_more_clicked))
				.setContent(new Intent(this, MoreActivity.class)));
		mTabHost.setCurrentTab(0);
		
/*		//local music button
		local_music.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(TabHostActivity.this,
						SongActivity.class);
				startActivity(intent);
			}
		});
*/		
		//mini play button, values: pause/restart
		mini_play_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (play_state == 1) {
					Intent intent = new Intent(TabHostActivity.this,
							MediaPlayService.class);
					intent.putExtra("what", "pause");
					intent.putExtra("pesition", pesition);
					startService(intent);
					play_state = 0;
					playButton
							.setBackgroundResource(R.drawable.mini_pause_button);
				} else if (play_state == 0) {
					Intent intent = new Intent(TabHostActivity.this,
							MediaPlayService.class);
					intent.putExtra("what", "restart");
					intent.putExtra("pesition", pesition);
					startService(intent);
					play_state = 1;
					playButton
							.setBackgroundResource(R.drawable.mini_play_button_pressed);
				}

			}
		});
		//on click to mainplay page
		viewminiplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TabHostActivity.this,
						MainPlaying.class);
				startActivity(intent);
			}
		});
		Intent intent = new Intent();
		intent.setAction("UI");
		intent.putExtra("UISTATE", "1");
		sendBroadcast(intent);

	
		
	}
	public void setView() {

		//GetMusicData.GetMusicData(this);
		local_music = findViewById(R.id.local_music);
		viewminiplay = findViewById(R.id.click_to_playing);
		playButton = (Button) findViewById(R.id.play_or_pause);
		mini_play_btn = findViewById(R.id.mini_play_btn);
	}
	//quit system
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings2:
			NotificationManager manager=(NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
			manager.cancel(10);
			Intent intent = new Intent(TabHostActivity.this,
					MediaPlayService.class);
			stopService(intent);
			
			System.exit(0);  

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
