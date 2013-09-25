package com.jiahaoliuliu.android.absherlockslides;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v4.view.GravityCompat;

public class MainActivity extends SherlockFragmentActivity {

	// Variables
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private MenuListAdapter mMenuAdapter;
	private String[] title;
	private String[] subtitle;
	private int[] icon;
	private Fragment fragment1 = new Fragment1();
	private Fragment fragment2 = new Fragment2();
	private Fragment fragment3 = new Fragment3();
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

		// Get the title
		mTitle = mDrawerTitle = getTitle();
		
		// Generate content
		title = new String[] {"Title Fragment 1", "Title Fragment 2", "Title Fragment 3"};
		subtitle = new String[] {"Subtitle Fragment 1", "Subtitle Fragment 2", "Subtitle Fragment 3"};
		icon = new int[] {R.drawable.action_about, R.drawable.action_settings, R.drawable.collections_cloud};
		
		// Link the content
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		
		mDrawerList = (ListView)findViewById(R.id.listview_drawer);

		mMenuAdapter = new MenuListAdapter(MainActivity.this, title, subtitle, icon);
		
		mDrawerList.setAdapter(mMenuAdapter);
		
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		if (mDrawerLayout != null) {
			// Set a custom shadow that overlays the main content when the drawer opens
			mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
			// Enable ActionBar app icon to behave as action to toggle nav drawer
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			
			// ActionBarDrawerToggle ties together the proper interactions
			// between the sliding drawer and the action bar app icon
			mDrawerToggle = new ActionBarDrawerToggle(
					this,
					mDrawerLayout,
					R.drawable.ic_drawer,
					R.string.drawer_open,
					R.string.drawer_close) {
				
				public void onDrawerClosed(View view) {
					super.onDrawerClosed(view);
				}
				
				public void onDrawerOpened(View drawerView) {
					// Set the title on the action when drawer open
					getSupportActionBar().setTitle(mDrawerTitle);
					super.onDrawerOpened(drawerView);
				}
			};
			
			mDrawerLayout.setDrawerListener(mDrawerToggle);
		}

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	
	private void selectItem(int position) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		switch (position) {
		case 0:
			ft.replace(R.id.content_frame, fragment1);
			break;
		case 1:
			ft.replace(R.id.content_frame, fragment2);
			break;
		case 2:
			ft.replace(R.id.content_frame, fragment3);
			break;
		}
		
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		
		// Get the title followed by the position
		setTitle(title[position]);
		
		if (mDrawerLayout != null) {
			// Close drawer
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (mDrawerLayout != null) {
			mDrawerToggle.syncState();
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mDrawerLayout != null) {
			// Pass any configuration change to the drawer toggles
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

}
