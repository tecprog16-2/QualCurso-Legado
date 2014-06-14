package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import helpers.Indicator;
import models.Course;
import models.GenericBeanDAO;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CompareFragment extends Fragment{
	BeanListCallbacks beanCallbacks;
	private static final String COURSE = "course";

	public CompareFragment(){
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

	Spinner yearSpinner = null;
	AutoCompleteTextView autoCompleteField = null;
	Course currentSelection = null;
	ListView institutionList = null; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.compare_fragment, container,
				false);
		
		if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState
						.getParcelable(COURSE));

			}

		this.yearSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		
		}
		this.institutionList = (ListView) rootView
				.findViewById(R.id.evaluationList);

		ArrayList<Course> courses = Course.getAll();
		AutoCompleteTextView autoCompleteField = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView);
		autoCompleteField.setAdapter(new ArrayAdapter<Course>(getActivity()
				.getApplicationContext(), R.layout.custom_textview, courses));

		OnItemClickListener listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();

			}
		};
		autoCompleteField.setOnItemClickListener(listener);
		evaluationList.setOnItemClickListener(new OnItemClickListener() {

		
		return rootView;
	}
	
	public void setCurrentSelection(Course currentSelection) {
		this.currentSelection = currentSelection;
	}
	
	public void updateList() {
		
		final ArrayList<String> fields = new ArrayList<String>();
		fields.add("id_institution");
		fields.add("id_course");
		fields.add("year");
		int year;

		if (yearSpinner.getSelectedItemPosition() != 0) {
			year = Integer.parseInt(yearSpinner.getSelectedItem()
					.toString());
		} else {
			yearSpinner
					.setSelection(yearSpinner.getAdapter().getCount() - 1);
			year = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(yearSpinner.getAdapter().getCount() - 1)
					.toString());
		}

		GenericBeanDAO gDB = new GenericBeanDAO();
		ListAdapter adapter = new ListAdapter(getActivity()
				.getApplicationContext(), R.layout.list_item,
				gDB.selectOrdered(fields, fields.get(0), "id_course ="
						+ this.currentSelection.getId() + " AND year ="
						+ year, "id_institution", true));
		institutionList.setAdapter(adapter);
	}

}
