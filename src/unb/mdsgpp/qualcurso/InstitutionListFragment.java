package unb.mdsgpp.qualcurso;

import unb.mdsgpp.qualcurso.NavigationDrawerFragment.NavigationDrawerCallbacks;
import android.app.Activity;
import android.os.Bundle;
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
	
	
	public InstitutionListFragment() {
		super();
	}

	public static InstitutionListFragment newInstance(int id){
		InstitutionListFragment fragment = new InstitutionListFragment();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list, container,
				false);
		ListView listView = (ListView) rootView.findViewById(R.id.list_view);
		listView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section1),
                        getString(R.string.title_section2),
                        getString(R.string.title_section3),
                }));
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
		beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance((int)id+1));
		super.onListItemClick(l, v, position, id);
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
