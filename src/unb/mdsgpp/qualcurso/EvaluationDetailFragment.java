package unb.mdsgpp.qualcurso;

import models.Article;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EvaluationDetailFragment extends Fragment{
	
	private static final String ID_COURSE = "idCourse";
	private static final String ID_INSTITUTION = "idInstitution";
	BeanListCallbacks beanCallbacks;
	
	public EvaluationDetailFragment() {
		super();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		args.putInt(ID_INSTITUTION, 0);
		this.setArguments(args);
	}
	
	public static EvaluationDetailFragment newInstance(int id_institution, int id_course){
		EvaluationDetailFragment fragment = new EvaluationDetailFragment();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id_course);
		args.putInt(ID_INSTITUTION, id_institution);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		TextView textView1 = (TextView) rootView
				.findViewById(R.id.university_acronym);
		textView1.setText(Institution.get(getArguments().getInt(ID_INSTITUTION)).getAcronym());
		
		return rootView;
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


}
