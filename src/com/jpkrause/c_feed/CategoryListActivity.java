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

import com.jpkrause.c_feed.ReadFile;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.AlertDialog;
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

public class CategoryListActivity extends ListActivity {
	
	List<String> categories, searchCat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent i = getIntent();
		String section = i.getExtras().getString(Constants.SELECTED_SECTION);
		String openFile = checkSection(section);
		
		
		//declare reader class and input stream
		ReadFile fileReader;
		InputStream input = null;
		
		//try to read categories file
		fileReader = new ReadFile();
		categories = new ArrayList<String>();
		searchCat = new ArrayList<String>();
		try {
			input = getAssets().open(openFile+".txt");
			categories = fileReader.OpenFile(input);
			input = getAssets().open(openFile+"links.txt");
			searchCat = fileReader.OpenFile(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//populate the list
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories);
		setListAdapter(adapter);
	}
	
	//return selected category when an item is selected
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent r = new Intent();
		r.putExtra(Constants.SELECTED_CATEGORY, categories.get(position));
		r.putExtra(Constants.SELECTED_CATEGORY_CODE, searchCat.get(position));
		setResult(Constants.RESULT_OK,r);
		finish();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private String checkSection(String selected){
		if(selected.equalsIgnoreCase("Community")){
			return "community";
		}
		if(selected.equalsIgnoreCase("Discussion Forums")){
			return "discussion";
		}
		if(selected.equalsIgnoreCase("Housing")){
			return "housing";
		}
		if(selected.equalsIgnoreCase("For Sale")){
			return "forsale";
		}
		if(selected.equalsIgnoreCase("Services")){
			return "services";
		}
		if(selected.equalsIgnoreCase("Jobs")){
			return "jobs";
		}
		if(selected.equalsIgnoreCase("Gigs")){
			return "gigs";
		}
		if(selected.equalsIgnoreCase("Resumes")){
			return "resumes";
		}
		else{
			return "NULL";
		}
	}

}
