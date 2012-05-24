package de.itemis.android.mmt;

import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class AbstractBaseActivity extends SherlockFragmentActivity {

	public final static String PREFERENCES_KEY = "BEER_PREFERENCES";

	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id) {
		return (T) findViewById(id);
	}

}
