package de.itemis.android.mmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MMT30Activity extends AbstractBaseActivity implements
		OnClickListener {
	/** Called when the activity is first created. */

	private BeerCounterFragment beerCounterFragment;
	private StatisticsFragment statisticsFragment;
	private Button detailsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		beerCounterFragment = (BeerCounterFragment) getSupportFragmentManager()
				.findFragmentById(R.id.beerCounterFragment);
		statisticsFragment = (StatisticsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.statisticsFragment);
		if (statisticsFragment != null) {
			beerCounterFragment.setBeerListener(statisticsFragment);
		}
		detailsButton = findView(R.id.detailsButton);
		if (detailsButton != null) {
			detailsButton.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, StatisticsActivity.class);
		startActivity(i);
	}
}