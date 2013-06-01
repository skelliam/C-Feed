package com.jpkrause.c_feed;

import android.app.Application;
import android.content.SharedPreferences;

public class CFeedApplication extends Application {
	
	@Override
	  public void onCreate()
	  {
	    super.onCreate();
	     
	    // Initialize the singletons so their instances
	    // are bound to the application process.
	    initSingletons();
	  }
	 
	  protected void initSingletons()
	  {
	    // Initialize the instance of MySingleton
		SharedPreferences prefs = getSharedPreferences(Constants.PREFS, 0);
	    SearchCriteria.initInstance(prefs);
	  }
}
