package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Bean;
import models.Course;
import models.Institution;
import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabsFragment extends Fragment implements OnTabChangeListener, OnQueryTextListener {

	BeanListCallbacks beanCallbacks;
	private static final String TAG = "FragmentTabs";
	public static final String TAB_INSTITUTIONS = "tabInstitutions";
	public static final String TAB_COURSES = "tabCourses";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;
	private SearchView mSearchView;
	private ArrayList<Course> allCourses = Course.getAll();
	private ArrayList<Institution> allInstitutions = Institution.getAll();

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
		setHasOptionsMenu(true);
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab
		updateTab(TAB_INSTITUTIONS, R.id.tab_1);
	}

	private void setupTabs() {
		mTabHost.setup(); // you must call this before adding your tabs!
		mTabHost.addTab(mTabHost.newTabSpec(TAB_INSTITUTIONS).setIndicator(getString(R.string.institutions)).setContent(R.id.tab_1));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_COURSES).setIndicator(getString(R.string.courses)).setContent(R.id.tab_2));
		TabWidget widget = mTabHost.getTabWidget();
		for(int i = 0; i < widget.getChildCount(); i++){
			View v = widget.getChildAt(i);
			TextView tv = (TextView) v.findViewById(android.R.id.title);
			if(tv==null){
				continue;
			}
			v.setBackgroundResource(R.drawable.tab_indicator_ab_light_green_acb);
		}
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		setupSearchView(searchItem);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	private void setupSearchView(MenuItem searchItem){
		searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		mSearchView.setOnQueryTextListener(this);
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentByTag(tabId) == null) {
			if(tabId.equalsIgnoreCase(TAB_INSTITUTIONS)){
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0,2010), placeholder);
			}else if (tabId.equalsIgnoreCase(TAB_COURSES)){
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0,2010), placeholder);
			}
		}
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		if(arg0.length()>=1){
			if(mCurrentTab == 0){
				ArrayList<Bean> beans = getFilteredList(arg0, allInstitutions);
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0, 2010, castToInstitutions(beans)), R.id.tab_1);
			}else if (mCurrentTab == 1){
				ArrayList<Bean> beans = getFilteredList(arg0, allCourses);
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0, 2010, castToCourses(beans)), R.id.tab_2);
			}
		}else{
			if(mCurrentTab == 0){
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0, 2010), R.id.tab_1);
			}else if (mCurrentTab == 1){
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0, 2010), R.id.tab_2);
			}
		}
		return false;
	}
	
	public ArrayList<Institution> castToInstitutions(ArrayList<Bean> beans){
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		for(Bean b : beans){
			institutions.add((Institution)b);
		}
		return institutions;
	}
	
	public ArrayList<Course> castToCourses(ArrayList<Bean> beans){
		ArrayList<Course> courses = new ArrayList<Course>();
		for(Bean b : beans){
			courses.add((Course)b);
		}
		return courses;
	}
	
	private ArrayList<Bean> getFilteredList(String filter, ArrayList<? extends Bean> list){
		ArrayList<Bean> beans = new ArrayList<Bean>();
		for(Bean b : list){
			if(b.toString().toLowerCase().startsWith(filter.toLowerCase())){
				beans.add(b);
			}
		}
		return beans;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		return false;
	}

}
