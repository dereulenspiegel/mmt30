package de.itemis.android.mmt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StatisticsFragment extends AbstractBaseFragment implements
		BeerListener, OnClickListener {

	private SharedPreferences beerPreferences;

	private long currentBeers;
	private long timeSinceLastBeer;
	private float beerPerHour;

	private TextView beerCountLabel;
	private TextView beerPerHourLabel;
	private Button shareButton;
	private Button resetButton;

	@Override
	public void currentBeerCount(long totalBeer, long timeDinceLastBeer) {
		this.currentBeers = totalBeer;
		this.timeSinceLastBeer = timeDinceLastBeer;
		calculateValues();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		inflateLayout(R.layout.count_details);
		beerCountLabel = findView(R.id.beerCountLabel);
		beerPerHourLabel = findView(R.id.beerPerHourLabel);
		shareButton = findView(R.id.shareStatusButton);
		shareButton.setOnClickListener(this);
		resetButton = findView(R.id.resetButton);
		resetButton.setOnClickListener(this);
		calculateValues();
		return this.root;
	}

	private void calculateValues() {
		beerCountLabel.setText(String.valueOf(currentBeers));
		if (currentBeers > 1) {
			long timePerBeer = System.currentTimeMillis() - timeSinceLastBeer;
			beerPerHour = 60 * 60 * 1000 / timePerBeer;
			beerPerHourLabel.setText(String.valueOf(beerPerHour));
		} else {
			beerPerHourLabel.setText("");
		}
	}

	public void loadValuesFromPreferences() {
		currentBeers = beerPreferences.getLong(
				BeerCounterFragment.PREF_TOTAL_BEERS, 0);
		timeSinceLastBeer = beerPreferences.getLong(
				BeerCounterFragment.PREF_LAST_BEER_TIME, -1);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		beerPreferences = getActivity().getSharedPreferences(
				AbstractBaseActivity.PREFERENCES_KEY, Activity.MODE_PRIVATE);
	}

	@Override
	public void onStart() {
		super.onStart();
		loadValuesFromPreferences();
		calculateValues();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == shareButton.getId()) {
			shareBeerStatus();
		}
		if (v.getId() == resetButton.getId()) {
			Editor edit = beerPreferences.edit();
			edit.putLong(BeerCounterFragment.PREF_LAST_BEER_TIME, 0);
			edit.putLong(BeerCounterFragment.PREF_TOTAL_BEERS, 0);
			edit.commit();
			currentBeers = 0;
			timeSinceLastBeer = 0;
			calculateValues();
		}
	}

	private void shareBeerStatus() {
		if (currentBeers > 1) {
			String finalText = String.format(getString(R.string.share_message),
					currentBeers, beerPerHour);
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_TEXT, finalText);
			
			startActivity(Intent.createChooser(share,
					getString(R.string.intent_chooser_message)));
		} else {
			Toast.makeText(getActivity(), R.string.beer_count_too_low,
					Toast.LENGTH_SHORT).show();
		}
	}

}
