package de.itemis.android.mmt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class AbstractBaseFragment extends SherlockFragment {

	protected LayoutInflater inflater;
	protected ViewGroup container;
	protected View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		return root;
	}

	protected View inflateLayout(int resId) {
		root = inflater.inflate(resId, container);
		return root;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id) {
		return (T) root.findViewById(id);
	}

}
