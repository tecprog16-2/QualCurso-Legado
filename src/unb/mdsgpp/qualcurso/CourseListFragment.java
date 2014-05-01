package unb.mdsgpp.qualcurso;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class CourseListFragment extends ListFragment{

	private static final String ID_INSTITUTION = "idInstitution";
	BeanListCallbacks beanCallbacks;
	
	
	public CourseListFragment() {
		super();
	}

	public static CourseListFragment newInstance(int id){
		CourseListFragment fragment = new CourseListFragment();
		Bundle args = new Bundle();
		args.putInt(ID_INSTITUTION, id);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance((int)id+1));
		super.onListItemClick(l, v, position, id);
	}
	
}
