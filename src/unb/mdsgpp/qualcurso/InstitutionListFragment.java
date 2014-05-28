package unb.mdsgpp.qualcurso;

import android.R.color;
import android.database.SQLException;

import java.util.ArrayList;

import models.Bean;
import models.Course;
import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InstitutionListFragment extends ListFragment{
	
	private static final String ID_COURSE = "idCourse";
	BeanListCallbacks beanCallbacks;
	private ArrayList<Institution> list = null;
	public InstitutionListFragment() {
		super();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		this.setArguments(args);
	}
	
	public InstitutionListFragment initialize(ArrayList<Institution> list){
		this.list = list;
		return this;
	}

	public static InstitutionListFragment newInstance(int id){
		InstitutionListFragment fragment = new InstitutionListFragment().initialize(getInstitutionsList(id));
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id);
		fragment.setArguments(args);
		return fragment;
	}
	public static InstitutionListFragment newInstance(int id, ArrayList<Institution> institutions){
		InstitutionListFragment fragment = new InstitutionListFragment().initialize(institutions);
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, container,
				false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		try {
			if(list != null){
				rootView.setAdapter(new ArrayAdapter<Institution>(
						getActionBar().getThemedContext(),
						R.layout.custom_textview, list));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if(getArguments().getInt(ID_COURSE)==0){
			beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(((Institution)l.getItemAtPosition(position)).getId()));
		}else {
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.newInstance(((Institution)l.getItemAtPosition(position)).getId() ,getArguments().getInt(ID_COURSE)));
		}
			super.onListItemClick(l, v, position, id);
	}
	
	private static ArrayList<Institution> getInstitutionsList(int idCourse) throws SQLException{
		if(idCourse == 0){
			return Institution.getAll();
		}else{
			return Course.get(idCourse).getInstitutions();
		}
	}
	
	private static ArrayList<Institution> getInstitutionsFromIds(ArrayList<Integer> ids){
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		for(Integer id : ids){
			institutions.add(Institution.get(id));
		}
		return institutions;
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
