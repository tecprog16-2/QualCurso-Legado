package unb.mdsgpp.qualcurso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class SearchByIndicatorFragment extends Fragment {

	public SearchByIndicatorFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search_fragment, container,
				false);

		Spinner spinner1 = (Spinner) rootView
				.findViewById(R.id.course_institution);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner spinner2 = (Spinner) rootView.findViewById(R.id.field);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner spinner3 = (Spinner) rootView.findViewById(R.id.year);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		return rootView;
	}

}