package com.android.tab;

import com.android.rhythm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class LiveActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Tab2 "Live" homepage
		setContentView(R.layout.live_main);

		//subTab setting
		// 以下三句代码，注意顺序
		TabHost mTabHost = (TabHost) findViewById(R.id.live_tabhost);
		mTabHost.setup();
		TabWidget tabWidget = mTabHost.getTabWidget();

		mTabHost.addTab(mTabHost.newTabSpec("Tab2_subTab1").setIndicator("排行")
				.setContent(R.id.live_tableRow1));
		mTabHost.addTab(mTabHost.newTabSpec("Tab2_subTab2").setIndicator("歌单")
				.setContent(R.id.live_tableRow2));
		mTabHost.addTab(mTabHost.newTabSpec("Tab2_subTab3").setIndicator("电台")
				.setContent(R.id.live_tableRow3));
		mTabHost.addTab(mTabHost.newTabSpec("Tab2_subTab4").setIndicator("分类")
				.setContent(R.id.live_tableRow4));
		mTabHost.setCurrentTab(0);

		int height = 40;
		// int width =45;

		for (int i = 0; i < tabWidget.getChildCount(); i++) {

			// 设置高度、宽度，由于宽度设置为fill_parent，在此对它没效果
			tabWidget.getChildAt(i).getLayoutParams().height = height;
			// tabWidget.getChildAt(i).getLayoutParams().width = width;
			// 设置tab中标题文字的颜色，不然默认为黑色
			final TextView tv = (TextView) tabWidget.getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextColor(this.getResources().getColorStateList(
					android.R.color.white));
		}

	}
}
