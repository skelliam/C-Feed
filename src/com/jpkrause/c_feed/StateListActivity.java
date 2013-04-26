/* This file is part of C-Feed for Android <http://github.com/jpkrause>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 * for more details.
 *
 * Copyright (C) 2013 John Krause
 */
package com.jpkrause.c_feed;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class StateListActivity extends ListActivity {

	private List<String> states;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//get selected country
		Intent i = getIntent();
		String country = i.getExtras().getString(Constants.SELECTED_COUNTRY);
		String openFile = checkCountry(country);

		// declare reader class and input stream
		ReadFile fileReader;
		InputStream input = null;

		// try to read categories file
		fileReader = new ReadFile();
		states = new ArrayList<String>();
		try {
			input = getAssets().open(openFile);
			states = fileReader.OpenFile(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, states);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent r = new Intent(getApplicationContext(),
				CityListActivity.class);
		r.putExtra(Constants.SELECTED_STATE, states.get(position));
		startActivityForResult(r, Constants.CITY);
	}

	// gets results of category and city
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.CITY) {
			if (resultCode == Constants.RESULT_OK) {
				Intent r2 = new Intent();
				r2.putExtra(Constants.SELECTED_CITY,
						data.getStringExtra(Constants.SELECTED_CITY));
				r2.putExtra(Constants.SELECTED_CITY_CODE,
						data.getStringExtra(Constants.SELECTED_CITY_CODE));
				setResult(Constants.RESULT_OK, r2);
				finish();
			}
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String checkCountry(String selected){
		if(selected.equalsIgnoreCase("United States")){
			return "unitedStates.txt";
		}
		else{
			return "NULL";
		}
	}

}