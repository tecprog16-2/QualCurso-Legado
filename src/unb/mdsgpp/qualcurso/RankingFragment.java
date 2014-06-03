package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.HashMap;

import models.Article;
import models.Bean;
import models.Book;
import models.Course;
import models.Evaluation;
import models.GenericBeanDAO;
import models.Institution;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RankingFragment extends Fragment{
	BeanListCallbacks beanCallbacks;
	private static final String ID_COURSE = "idCourse";
	public RankingFragment() {
		super();
		// TODO Auto-generated constructor stub
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
		View rootView = inflater.inflate(R.layout.ranking_fragment, container,
				false); 
		// TODO Auto-generated method stub
		final Spinner filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
		final Spinner yearSpinner = (Spinner) rootView.findViewById(R.id.year);
		final ListView evaluationList = (ListView) rootView.findViewById(R.id.evaluationList);
		
		
		ArrayList<Course> courses = Course.getAll();
		AutoCompleteTextView autoCompleteField = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
		autoCompleteField.setAdapter(new ArrayAdapter<Course>(getActivity().getApplicationContext(), R.layout.custom_textview, courses));


		final GenericBeanDAO gDB = new GenericBeanDAO();
		final ArrayList<String> fields = new ArrayList<String>();

		fields.add("id_institution");
		fields.add("id_course");
		fields.add("acronym");
		fields.add("year");

		OnItemClickListener listener = new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				String filterField = "";
				int year;
				if( yearSpinner.getSelectedItemPosition() != 0 ) {
					year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
				} else {
					year = Integer.parseInt(yearSpinner.getAdapter().getItem(yearSpinner.getAdapter().getCount()-1).toString());
				}
				
				switch (filterFieldSpinner.getSelectedItemPosition()) {
				case 0:
					filterField = "";
					break;

				case 1:
					filterField = new Evaluation().fieldsList().get(7);
					break;

				case 2:
					filterField = new Evaluation().fieldsList().get(5);
					break;

				case 3:
					filterField = new Evaluation().fieldsList().get(6);
					break;

				case 4:
					filterField = new Evaluation().fieldsList().get(8);
					break;

				case 5:
					filterField = new Evaluation().fieldsList().get(9);
					break;

				case 6:
					filterField = new Evaluation().fieldsList().get(10);
					break;

				case 7:
					filterField = new Evaluation().fieldsList().get(13);
					break;

				case 8:
					filterField = new Book().fieldsList().get(2);
					break;

				case 9:
					filterField = new Book().fieldsList().get(1);
					break;

				case 10:
					filterField = new Book().fieldsList().get(3);
					break;

				case 11:
					filterField = new Book().fieldsList().get(4);
					break;

				case 12:
					filterField = new Article().fieldsList().get(1);
					break;

				case 13:
					filterField = new Article().fieldsList().get(2);
					break;

				default:
					filterField = "";
					break;
				}

				if( filterField.length() != 0 ) {
					fields.add(filterField);
					ListAdapter adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.list_item,gDB.selectOrdered(fields, filterField,"id_course ="+((Course)parent.getItemAtPosition(position)).getId()+" AND year ="+year ,"e.id_institution", true));
					evaluationList.setAdapter(adapter);
				} else {
					Context c = QualCurso.getInstance();
					String emptySearchFilter = getResources().getString(R.string.empty_search_filter);

					Toast toast = Toast.makeText(c, emptySearchFilter, Toast.LENGTH_SHORT);
					toast.setGravity(0, 20, 50);
					toast.show();
				}
		    }
		};
		autoCompleteField.setOnItemClickListener(listener);
		
		
		evaluationList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.newInstance(Integer.parseInt(((HashMap<String,String>)parent.getItemAtPosition(position)).get("id_institution")), Integer.parseInt(((HashMap<String,String>)parent.getItemAtPosition(position)).get("id_course")), Integer.parseInt(((HashMap<String,String>)parent.getItemAtPosition(position)).get("year"))));
				
			}
		});
		return rootView;
	}

}
