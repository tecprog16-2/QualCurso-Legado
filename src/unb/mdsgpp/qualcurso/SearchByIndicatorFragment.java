package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Article;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
		
		final Spinner listSelectionSpinner = (Spinner) rootView
				.findViewById(R.id.course_institution);

		listSelectionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		final Spinner filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);

		filterFieldSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		final Spinner yearSpinner = (Spinner) rootView.findViewById(R.id.year);

		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		final CheckBox maximum = (CheckBox) rootView.findViewById(R.id.maximum);
		final EditText firstNumber = (EditText) rootView.findViewById(R.id.firstNumber);
		final EditText secondNumber = (EditText) rootView.findViewById(R.id.secondNumber);
		Button searchButton = (Button) rootView.findViewById(R.id.buttonSearch);

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int number1, number2, year, max, listSelectionPosition;
				String filterField;

				if( firstNumber.getText().length() == 0) {
					firstNumber.setText("0");
				}

				if( secondNumber.getText().length() == 0 ) {
					maximum.setChecked(true);
				}

				String firstNumberValue = firstNumber.getText().toString();
				String secondNumberValue = secondNumber.getText().toString();

				number1 = Integer.parseInt(firstNumberValue);
				number2 = maximum.isChecked() ? -1 : Integer.parseInt(secondNumberValue);
				listSelectionPosition = listSelectionSpinner.getSelectedItemPosition();

				if( yearSpinner.getSelectedItemPosition() != 0 ) {
					year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
				} else {
					year = Integer.parseInt(yearSpinner.getAdapter().getItem(yearSpinner.getAdapter().getCount()-1).toString());
				}

				if(maximum.isChecked()){
					max = -1;
				}else{
					max = number2;
				}

				switch (filterFieldSpinner.getSelectedItemPosition()) {
				case 0:
					filterField = "";
					break;

				case 1:
					filterField = new Evaluation().fieldsList().get(5);
					break;

				case 2:
					filterField = new Evaluation().fieldsList().get(6);
					break;

				case 3:
					filterField = new Evaluation().fieldsList().get(8);
					break;

				case 4:
					filterField = new Evaluation().fieldsList().get(9);
					break;

				case 5:
					filterField = new Evaluation().fieldsList().get(10);
					break;

				case 6:
					filterField = new Evaluation().fieldsList().get(13);
					break;

				case 7:
					filterField = new Book().fieldsList().get(2);
					break;

				case 8:
					filterField = new Book().fieldsList().get(1);
					break;

				case 9:
					filterField = new Book().fieldsList().get(3);
					break;

				case 10:
					filterField = new Book().fieldsList().get(4);
					break;

				case 11:
					filterField = new Article().fieldsList().get(1);
					break;

				case 12:
					filterField = new Article().fieldsList().get(2);
					break;

				default:
					filterField = "";
					break;
				}

			this.updateSearchList(number1, max, year, listSelectionPosition, filterField);
			}

			private void updateSearchList(int min, int max, int year, int listSelectionPosition, String filterField) {
				if(filterField == "") {
					Context c = QualCurso.getInstance();
					String emptySearchFilter = getResources().getString(R.string.empty_search_filter);

					Toast toast = Toast.makeText(c, emptySearchFilter, Toast.LENGTH_SHORT);
					toast.show();
				} else {
						switch (listSelectionPosition) {
						case 0:
							callInstitutionList(min, max, year, filterField);
							break;

						case 1:
							callCourseList(min, max, year, filterField);
							break;

						case 2:
							callInstitutionList(min, max, year, filterField);
							break;

						default:
							break;
						}
				}
			}

			private void callInstitutionList(int min, int max, int year, String filterField){
				ArrayList<Institution> beanList = Institution.getInstitutionsByEvaluationFilter(filterField, year, min, max);
				beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(beanList,filterField, year, min, max), R.id.search_list);
			}

			private void callCourseList(int min, int max, int year, String filterField){
				ArrayList<Course> beanList = Course.getCoursesByEvaluationFilter(filterField, year, min, max);
				beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(beanList,filterField, year, min, max), R.id.search_list);
			}
		};

		searchButton.setOnClickListener(listener);

		// Event to disable second number when MAX is checked
		maximum.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if( maximum.isChecked() ) {
					secondNumber.setEnabled(false);
				} else {
					secondNumber.setEnabled(true);
				}
			}
		});

		return rootView;
	}

}