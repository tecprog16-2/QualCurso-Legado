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
		
		TextView textView2 = (TextView) rootView
				.findViewById(R.id.general_data);
		textView2.setText("DATA DA AVALIAÇÃO: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE)).get(0).getYear() +
				"\nCURSO: " + Course.get(getArguments().getInt(ID_COURSE)).getName() +
				"\nMODALIDADE DO CURSO: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
					getArguments().getInt(ID_COURSE)).get(0).getModality());
		
		TextView textView3 = (TextView) rootView
				.findViewById(R.id.indicator1);
		textView3.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
			getArguments().getInt(ID_COURSE)).get(0).getMasterDegreeStartYear());
		
		TextView textView4 = (TextView) rootView
				.findViewById(R.id.indicator2);
		textView4.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
			getArguments().getInt(ID_COURSE)).get(0).getDoctorateStartYear());
		
		TextView textView5 = (TextView) rootView
				.findViewById(R.id.indicator3);
		textView5.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE)).get(0).getTriennialEvaluation());
		
		TextView textView6 = (TextView) rootView
				.findViewById(R.id.indicator4);
		textView6.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE)).get(0).getPermanentTeachers());
		
		TextView textView7 = (TextView) rootView
				.findViewById(R.id.indicator5);
		textView7.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getTheses());
		
		TextView textView8 = (TextView) rootView
				.findViewById(R.id.indicator6);
		textView8.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getDissertations());
		
		TextView textView9 = (TextView) rootView
				.findViewById(R.id.indicator7);
		textView9.setText("" + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getArtisticProduction());
		
		TextView textView10 = (TextView) rootView
				.findViewById(R.id.indicator8);
		textView10.setText("" + Book.get(getArguments().getInt(ID_COURSE)).getChapters());
		
		TextView textView11 = (TextView) rootView
				.findViewById(R.id.indicator9);
		textView11.setText("" + Book.get(getArguments().getInt(ID_COURSE)).getIntegralText());
		
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
