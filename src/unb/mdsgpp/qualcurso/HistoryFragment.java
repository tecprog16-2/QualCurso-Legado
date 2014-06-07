package unb.mdsgpp.qualcurso;

import models.Search;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HistoryFragment extends Fragment {

	BeanListCallbacks beanCallbacks;

	public HistoryFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_history, container, false);
		final ListView history = (ListView) rootView.findViewById(R.id.listHistory) ; 

		ListHistoryAdapter histotyAdapter = new ListHistoryAdapter(this.getActivity().getApplicationContext(), R.id.listHistory, Search.getAll());

		return rootView;
	}
}
