package de.itemis.android.mmt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class BeerCounterFragment extends AbstractBaseFragment implements
		OnClickListener {

	private Button counterButton;
	private SharedPreferences beerPreferences;

	public final static String PREF_TOTAL_BEERS = "TOTAL_BEERS";
	public final static String PREF_LAST_BEER_TIME = "LAST_BEER_TIME";

	private BeerListener listener;

	@Override
	public void onClick(View v) {
		incrementBeerCount();
	}

	public void setBeerListener(BeerListener listener) {
		this.listener = listener;
	}

	private void incrementBeerCount() {
		long currentCount = beerPreferences.getLong(PREF_TOTAL_BEERS, 0);
		long lastTime = beerPreferences.getLong(PREF_LAST_BEER_TIME, -1);
		currentCount = currentCount + 1;
		Editor edit = beerPreferences.edit();
		edit.putLong(PREF_TOTAL_BEERS, currentCount);
		edit.putLong(PREF_LAST_BEER_TIME, System.currentTimeMillis());
		edit.commit();
		if (listener != null) {
			listener.currentBeerCount(currentCount, lastTime);
		}
		Toast.makeText(getActivity(), R.string.salute, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		inflateLayout(R.layout.counter_button);
		counterButton = findView(R.id.counterButton);
		counterButton.setOnClickListener(this);

		return this.root;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerPreferences = getActivity().getSharedPreferences(
				AbstractBaseActivity.PREFERENCES_KEY, Activity.MODE_PRIVATE);
	}

}
