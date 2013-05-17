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

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	public String url;
	public Button searchBtn;
	public Button cityBtn;
	public Button categoryBtn;
	public EditText searchTxt;
	public SearchCriteria criteria;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// check and show startup dialog
		prefs = getSharedPreferences(Constants.PREFS, 0);
		editor = prefs.edit();
		
		criteria = new SearchCriteria(prefs);	
		
		boolean firstRun = prefs.getBoolean(Constants.FIRST_RUN, true);
		if (firstRun) {
			openAboutDialog();
			editor.putBoolean(Constants.FIRST_RUN, false);
			editor.commit();
		}

		searchBtn = (Button) findViewById(R.id.searchBtn);
		cityBtn = (Button) findViewById(R.id.cityBtn);
		categoryBtn = (Button) findViewById(R.id.categoryBtn);
		searchTxt = (EditText) findViewById(R.id.searchText);
		
		cityBtn.setText(criteria.getCityName());
		categoryBtn.setText(criteria.getSectionName() + " - "
				+ criteria.getCategoryName());
		searchTxt.setText(criteria.getSearchQuery().replace('+', ' '));

		

		// city button action
		cityBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent chooseCity = new Intent(getApplicationContext(),
						ContinentListActivity.class);
				startActivityForResult(chooseCity, Constants.CITY);
			}
		});

		// category button action
		categoryBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent chooseCategory = new Intent(getApplicationContext(),
						SectionListActivity.class);
				startActivityForResult(chooseCategory, Constants.CATEGORIES);
			}
		});

		// search button action
		searchBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				criteria.setSearchQuery(searchTxt.getText().toString()
						.replaceAll("\\s+", "+"));
				editor.putString(Constants.LAST_QUERY, criteria.getSearchQuery());
				editor.commit();
				if (criteria.getSearchQuery().equals("")) {
					url = criteria.getCityCode() + "/"
							+ criteria.getCategoryCode() + "/index.rss";
				} else {
					url = criteria.getCityCode() + "/search/"
							+ criteria.getCategoryCode() + "?query="
							+ criteria.getSearchQuery() + "&format=rss";
				}
				Intent initiateSearch = new Intent(getApplicationContext(),
						ResultsListActivity.class);
				initiateSearch.putExtra("url", url);
				startActivity(initiateSearch);

			}
		});

	}

	// gets results of category and city
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.CITY) {
			if (resultCode == Constants.RESULT_OK) {
				criteria.setCityCode(data
						.getStringExtra(Constants.SELECTED_CITY_CODE));
				criteria.setCityName(data
						.getStringExtra(Constants.SELECTED_CITY));
				cityBtn.setText(criteria.getCityName());
				editor.putString(Constants.LAST_CITY_CODE, criteria.getCityCode());
				editor.putString(Constants.LAST_CITY, criteria.getCityName());
				editor.commit();
			}
		}
		if (requestCode == Constants.CATEGORIES) {
			if (resultCode == Constants.RESULT_OK) {
				criteria.setCategoryCode(data
						.getStringExtra(Constants.SELECTED_CATEGORY_CODE));
				criteria.setCategoryName(data
						.getStringExtra(Constants.SELECTED_CATEGORY));
				criteria.setSectionName(Constants.selectedSection);
				categoryBtn.setText(Constants.selectedSection + " - "
						+ criteria.getCategoryName());
				editor.putString(Constants.LAST_CATEGORY_CODE, criteria.getCategoryCode());
				editor.putString(Constants.LAST_CATEGORY, criteria.getCategoryName());
				editor.putString(Constants.LAST_SECTION, criteria.getSectionName());
				editor.commit();
			}
		}
	}

	private void openAboutDialog() {
		// create about dialog
		final AlertDialog aboutC = aboutDialog.create(this);
		aboutC.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		openAboutDialog();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
