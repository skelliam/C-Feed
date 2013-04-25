package com.jpkrause.c_feed;

import com.jpkrause.c_feed.ReadFile;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

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

public class CategoryListActivity extends ListActivity {
	
	List<String> categories, searchCat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		//declare reader class and input stream
		ReadFile fileReader;
		InputStream input = null;
		
		//try to read categories file
		fileReader = new ReadFile();
		categories = new ArrayList<String>();
		searchCat = new ArrayList<String>();
		try {
			input = getAssets().open("categories.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			categories = fileReader.OpenFile(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input = getAssets().open("searchCat.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category_list, menu);
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

}
