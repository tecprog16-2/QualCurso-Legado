package unb.mdsgpp.qualcurso;

import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabsFragment extends Fragment implements OnTabChangeListener {

	BeanListCallbacks beanCallbacks;
	private static final String TAG = "FragmentTabs";
	public static final String TAB_INSTITUTIONS = "tabInstitutions";
	public static final String TAB_COURSES = "tabCourses";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.tabs_fragment, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		setupTabs();
		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab
		updateTab(TAB_INSTITUTIONS, R.id.tab_1);
	}

	private void setupTabs() {
		mTabHost.setup(); // you must call this before adding your tabs!
		mTabHost.addTab(mTabHost.newTabSpec(TAB_INSTITUTIONS).setIndicator("Instituições").setContent(R.id.tab_1));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_COURSES).setIndicator("Cursos").setContent(R.id.tab_2));
	}

	@Override
	public void onTabChanged(String tabId) {
		if (TAB_INSTITUTIONS.equals(tabId)) {
			updateTab(tabId, R.id.tab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_COURSES.equals(tabId)) {
			updateTab(tabId, R.id.tab_2);
			mCurrentTab = 1;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentByTag(tabId) == null) {
			if(tabId.equalsIgnoreCase(TAB_INSTITUTIONS)){
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0), placeholder);
				/*fm.beginTransaction()
						.replace(placeholder, new InstitutionListFragment() , tabId)
						.addToBackStack(null)
						.commit();*/
			}else if (tabId.equalsIgnoreCase(TAB_COURSES)){
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0), placeholder);
				/*fm.beginTransaction()
				.replace(placeholder, new CourseListFragment() , tabId)
				.addToBackStack(null)
				.commit();*/
			}
		}
	}

}
