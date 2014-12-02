package com.android.rhythm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.android.resource.GetMusicData;
import com.android.resource.MusicUtils;
import com.android.service.MediaPlayService;

import android.R;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainPlaying extends Activity {
	ImageView backimageView, main_playing_image, play_mode1;
	TextView title, time1, time2;
	View cView;
	int play_state, pesition, duration, current;
	int mode = 3;

	ImageButton button1, button3;
	ImageButton button2;
	Button main_playing_button2;
	List<Map<String, Object>> list;
	public static SeekBar seekBar;
	boolean isdroadcast = false;
	private int index = 0;
	private int CurrentTime = 0;
	private int CountTime = 0;
	LocalActivityManager manager;
	//ViewPager viewPager;
	AudioManager audioManager;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mian_playing);
		
		backimageView = (ImageView) findViewById(R.id.main_playing_back);
		title = (TextView) findViewById(R.id.main_playing_title);
		button1 = (ImageButton) findViewById(R.id.main_pre);
		button2 = (ImageButton) findViewById(R.id.mian_playing_button);
		button3 = (ImageButton) findViewById(R.id.main_next);
		main_playing_image = (ImageView) findViewById(R.id.main_playing_image);
		main_playing_button2 = (Button) findViewById(R.id.main_playing_button2);
		play_mode1 = (ImageView) findViewById(R.id.play_mode1);

		seekBar = (SeekBar) findViewById(R.id.seekbar);

		time1 = (TextView) findViewById(R.id.time1);
		time2 = (TextView) findViewById(R.id.time2);
		cView = findViewById(R.id.control_view);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		ArrayList<View> viewlist = new ArrayList<View>();
		//
		title.setMovementMethod(ScrollingMovementMethod.getInstance());
		list = GetMusicData.GetMusicData(this);
		//go back
		backimageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainPlaying.this,
						TabHostActivity.class);
				startActivity(intent);
			}
		});
		//previous song
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mode == 3) {
					int c = pesition - 1;
					if (c == -1) {
						c = list.size() - 1;
					}
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("pesition", c);
					intent.putExtra("what", "play");
					startService(intent);

				}
				if (mode == 2) {
					Random random = new Random();
					pesition = random.nextInt(list.size() - 1);
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("pesition", pesition);
					intent.putExtra("what", "play");
					startService(intent);
				}
				if (mode == 1) {
					int c = pesition - 1;
					if (c == -1) {
						c = list.size() - 1;
					}
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("pesition", c);
					intent.putExtra("what", "play");
					startService(intent);
				}
			}
		});
		//next song
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mode == 3) {
					int c = pesition + 1;
					if (c == list.size()) {
						c = 0;
					}
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("pesition", c);
					intent.putExtra("what", "play");
					startService(intent);
				}
				if (mode == 2) {
					Random random = new Random();
					pesition = random.nextInt(list.size() - 1);
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("pesition", pesition);
					intent.putExtra("what", "play");
					startService(intent);
				}
				if (mode == 1) {
					int c = pesition + 1;
					if (c == list.size()) {
						c = 0;
					}
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("pesition", c);
					intent.putExtra("what", "play");
					startService(intent);
				}
			}
		});
		//play button, values: start/pause
		main_playing_button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (play_state == 1) {
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("what", "pause");
					intent.putExtra("pesition", pesition);
					startService(intent);
					play_state = 0;
					main_playing_button2
							.setBackgroundResource(R.drawable.pause_button_pressed);

				} else if (play_state == 0) {
					Intent intent = new Intent(MainPlaying.this,
							MediaPlayService.class);
					intent.putExtra("what", "restart");
					intent.putExtra("pesition", pesition);
					startService(intent);
					play_state = 1;
					main_playing_button2
							.setBackgroundResource(R.drawable.playing_button_pressed);

				}
			}
		});
		//song info:image
		main_playing_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cView.setVisibility(View.VISIBLE);
			}
		});
		//change time progress bar
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			//stop tracking
			public void onStopTrackingTouch(SeekBar seekBar) {
				Intent intent = new Intent(MainPlaying.this,
						MediaPlayService.class);
				intent.putExtra("what", "seekto");
				intent.putExtra("pesition", pesition);
				intent.putExtra("seekto", seekBar.getProgress());
				startService(intent);
			}
			//start tracking
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			//value change
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}
		});
		//play mode, values: random/single/list  mode1->mode2->mode3->mode1 ..
		play_mode1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("MODE");
				if (mode == 3) {
					intent.putExtra("m1", 1);
				}
				if (mode == 1) {
					intent.putExtra("m1", 2);
				}
				if (mode == 2) {
					intent.putExtra("m1", 3);
				}
				sendBroadcast(intent);
			}
		});

		//
		Intent intent = new Intent();
		intent.setAction("UI2");
		intent.putExtra("UISTATE2", "2");
		sendBroadcast(intent);

		IntentFilter filter = new IntentFilter();
		filter.addAction("isplay");
		filter.addAction("info");
		filter.addAction("current");
		filter.addAction("BTNMODE");
		filter.addAction("M");
		registerReceiver(receiver, filter);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("isplay")) {
				play_state = intent.getIntExtra("state", 0);
				if (play_state == 1) {
					main_playing_button2
							.setBackgroundResource(R.drawable.playing_button_pressed);
				} else {
					main_playing_button2
							.setBackgroundResource(R.drawable.pause_button_pressed);
				}
			}
			if (intent.getAction().equals("info")) {
				pesition = intent.getIntExtra("pesition", -1);
				duration = (Integer) list.get(pesition).get("TIME");
				time2.setText(GetFormatTime(duration).toString());
				seekBar.setMax(duration);
				CountTime = duration;
				title.setText(list.get(pesition).get("TITLE").toString());

				long musicID = Integer.parseInt(list.get(pesition).get("ID")
						.toString());
				long musicAlbum_ID = Integer.parseInt(list.get(pesition)
						.get("ALBUMID").toString());

				Bitmap bm = MusicUtils.getArtWork(MainPlaying.this, musicID,
						musicAlbum_ID, true);
				main_playing_image.setImageBitmap(bm);

			}
			if (intent.getAction().equals("current")) {
				current = intent.getIntExtra("current", 0);
				time1.setText(GetFormatTime(current).toString());
				seekBar.setProgress(current);
				CurrentTime = current;
			}
			if (intent.getAction().equals("BTNMODE")) {
				mode = intent.getIntExtra("m", 3);
				if (mode == 1) {
					play_mode1
							.setBackgroundResource(R.drawable.playmode_repeate_single_hover);
				}
				if (mode == 2) {
					play_mode1
							.setBackgroundResource(R.drawable.playmode_repeate_random_hover);
				}
				if (mode == 3) {
					play_mode1
							.setBackgroundResource(R.drawable.playmode_repeate_all_hover);
				}
			}

		}
	};

	private static String GetFormatTime(int time) {
		SimpleDateFormat sim = new SimpleDateFormat("mm:ss");
		return sim.format(time);
	}
	//print screen
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	public class MyPagerAdapter extends PagerAdapter {
		List<View> list = new ArrayList<View>();

		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

}
