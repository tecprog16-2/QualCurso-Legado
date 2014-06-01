package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchByIndicatorFragment extends Fragment {
	
	BeanListCallbacks beanCallbacks;
	
	public SearchByIndicatorFragment() {
		super();
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search_fragment, container,
				false);
		
		ArrayList<Institution> beanList = Institution.getInstitutionsByEvaluationFilter("triennial_evaluation", 2007, 7, -1);
		beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(beanList,"triennial_evaluation", 2007, 7, -1), R.id.search_list);
		
		
		Spinner listSelectionSpinner = (Spinner) rootView
				.findViewById(R.id.course_institution);

		listSelection.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);

		filterField.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner yearSpinner = (Spinner) rootView.findViewById(R.id.year);

		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		CheckBox maximum = (CheckBox) rootView.findViewById(R.id.maximum);
		EditText firstNumber = (EditText) rootView.findViewById(R.id.firstNumber);
		EditText secondNumber = (EditText) rootView.findViewById(R.id.secondNumber);
		Button searchButton = (Button) rootView.findViewById(R.id.search_button);
		
		int number1, number2, year, max;
		
		number1 = Integer.parseInt(firstNumber.getText().toString());
		number2 = Integer.parseInt(secondNumber.getText().toString());
		year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
		
		if(maximum.isChecked()){
			max = -1;
		}else{
			max = number2;
		}
		
		
		OnClickListener listener = new OnClickListener() {
				
			@Override
			public void onClick(View arg0) {
				
				
				
			}
		};
		searchButton.setOnClickListener(listener);
		
		
		return rootView;
	}

}