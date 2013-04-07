package com.jpkrause.c_feed;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ExpandableListAdapter mAdapter;
	
	public String citySelected;
	public String categorySelected;
	public Button searchBtn;
	public Button cityBtn;
	public Button categoryBtn;
	public EditText searchTxt;
	

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchBtn = (Button) findViewById(R.id.searchBtn);
		cityBtn = (Button) findViewById(R.id.cityBtn);
		categoryBtn = (Button) findViewById(R.id.categoryBtn);
		searchTxt = (EditText) findViewById(R.id.searchText);
		
		cityBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent chooseCity = new Intent(getApplicationContext(), CityListActivity.class);
				startActivityForResult(chooseCity,100);
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100){
			citySelected = data.getExtras().getString("city");
			cityBtn.setText(citySelected);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String getCitySelected() {
		return citySelected;
	}

	public String getCategorySelected() {
		return categorySelected;
	}

	public void setCitySelected(String citySelected) {
		this.citySelected = citySelected;
	}

	public void setCategorySelected(String categorySelected) {
		this.categorySelected = categorySelected;
	}
	

}
