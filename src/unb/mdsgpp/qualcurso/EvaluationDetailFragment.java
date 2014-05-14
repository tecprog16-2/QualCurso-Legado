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
		TextView textView = (TextView) rootView
				.findViewById(R.id.section_label);
		textView.setText("Data da Avaliação: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE)).get(0).getYear() + 
				"\nUniversidade: " + Institution.get(getArguments().getInt(ID_INSTITUTION)).getAcronym() +
				"\nCurso: " + Course.get(getArguments().getInt(ID_COURSE)).getName() +
				
				"\n\nModalidade do Curso: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getModality() +
						
				"\n\nAno de início do mestrado: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getMasterDegreeStartYear() +
				"\nAno de início do doutorado: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getDoctorateStartYear() +
						
				"\n\nConceito no ano " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getYear() + ": " + 
						Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getTriennialEvaluation() +
						
				"\n\nMédia Anual de Docentes permanentes: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getPermanentTeachers() +
						
				"\n\nTotal de Teses Defendidas: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getTheses() +
				"\nTotal de Dissertações Defendidas: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getDissertations() +
				"\nTotal de Trabalhos Artísticos Defendidos: " + Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
						getArguments().getInt(ID_COURSE)).get(0).getArtisticProduction() +
						
				"\n\nQuantidade de Capítulos de Livros: " + Book.get(getArguments().getInt(ID_COURSE)).getChapters() +
				"\nQuantidade de Textos Integrais: " + Book.get(getArguments().getInt(ID_COURSE)).getIntegralText() +
				"\nQuantidade de Coletâneas: " + Book.get(getArguments().getInt(ID_COURSE)).getCollections() +
				"\nQuantidade de Verbetes e Outros: " + Book.get(getArguments().getInt(ID_COURSE)).getEntries() +
				"\n\nQuantidade de Artigos Publicados: " + Article.get(getArguments().getInt(ID_COURSE)).getPublishedJournals() +
				"\nQuantidade de Trabalhos Publicados: " + 
				Article.get(getArguments().getInt(ID_COURSE)).getPublishedConferenceProceedings());
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
