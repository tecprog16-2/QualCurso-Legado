package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.HashMap;

import helpers.Indicator;
import models.Course;
import models.GenericBeanDAO;
import models.Institution;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CompareFragment extends Fragment{
	BeanListCallbacks beanCallbacks;
	private static final String COURSE = "course";

	private Spinner yearSpinner = null;
	private AutoCompleteTextView autoCompleteField = null;
	private ListView institutionList = null;
	private Button compareButton = null;

	private int selectedYar;
	private Course selectedCourse;
	private Institution [] selectedInstitutions = new Institution[2];

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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.compare_choose_fragment, container,
				false);

		if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState
						.getParcelable(COURSE));

			}
		}

		// bound variables with layout objects
		this.yearSpinner = (Spinner) rootView.findViewById(R.id.compare_year);
		this.autoCompleteField = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
		this.institutionList = (ListView) rootView.findViewById(R.id.institutionList);
		this.compareButton = (Button) rootView.findViewById(R.id.compare_button);

		this.autoCompleteField.setAdapter(new ArrayAdapter<Course>(getActivity().getApplicationContext(), R.layout.custom_textview, Course.getAll()));

		// Set objects events
		this.yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if( yearSpinner.getSelectedItemPosition() != 0)
					selectedYar = Integer.parseInt(yearSpinner.getSelectedItem().toString());
				else
					selectedYar = 0;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				selectedYar = 0;
			}
		});

		this.autoCompleteField.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();
			}
		});

		this.institutionList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});

		this.compareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( selectedYar == 0 )
					displayToastMessage(getResources().getString(R.string.select_a_year));
			}
		});

		return rootView;
	}

	public void setCurrentSelection(Course currentSelection) {
		this.selectedCourse = currentSelection;
	}

	public void updateList() {
		ListCompareAdapter compareAdapterList = new ListCompareAdapter(this.getActivity().getApplicationContext(), R.layout.compare_show_list_item);

		if( this.selectedCourse != null ) {
			ArrayList<Institution> courseInstitutions = this.selectedCourse.getInstitutions();
			compareAdapterList.addAll(courseInstitutions);

			this.institutionList.setAdapter(compareAdapterList);
		} else {
			displayToastMessage(getResources().getString(R.string.select_a_course));
		}
	}

	private void displayToastMessage(String textMenssage) {
		Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), textMenssage, Toast.LENGTH_SHORT);
		toast.show();
	}
}
