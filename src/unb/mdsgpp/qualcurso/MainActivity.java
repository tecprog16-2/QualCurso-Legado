package unb.mdsgpp.qualcurso;

import models.Course;
import models.Institution;
import models.Search;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, BeanListCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private int drawerPosition = 10;
	public static String DRAWER_POSITION = "drawerPosition";

	public static String CURRENT_TITLE = "currentTitle";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(savedInstanceState != null){
			drawerPosition = savedInstanceState.getInt(DRAWER_POSITION);
			mTitle = savedInstanceState.getCharSequence(CURRENT_TITLE);
		}else{
			mTitle = getTitle();
		}
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(DRAWER_POSITION, drawerPosition);
		outState.putCharSequence(CURRENT_TITLE, mTitle);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		//fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.container)).addToBackStack(null).commit();
		ActionBar actionBar = getSupportActionBar();	
		Fragment fragment = null;
		switch (position) {
			case 0:
				mTitle = getString(R.string.title_section1);
				actionBar.setTitle(R.string.title_section1);
				fragment = new TabsFragment();
				drawerPosition = 0;
			break;
			case 1:
				mTitle = getString(R.string.title_section2);
				actionBar.setTitle(R.string.title_section2);
				fragment = new SearchByIndicatorFragment();
				drawerPosition = 1;
				break;
			case 2:
				mTitle = getString(R.string.title_section3);
				actionBar.setTitle(R.string.title_section3);
				fragment = new RankingFragment();
				drawerPosition = 2;
				break;
			case 3:
				mTitle = getString(R.string.title_section4);
				actionBar.setTitle(R.string.title_section4);
				fragment = new HistoryFragment();
				drawerPosition = 3;
				break;
			case 4:
				mTitle = getString(R.string.title_section5);
				actionBar.setTitle(R.string.title_section5);
				fragment = new CompareChooseFragment();
				drawerPosition = 4;
				break;

			default:
				fragment = null;
				break;
			}		
		if(fragment != null){
			if(fragment instanceof TabsFragment){
				fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,fragment).commit();
			}else{
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,fragment).addToBackStack(null).commit();
			}
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		//Nothing
		}
	}
	
	

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch(item.getItemId()) {
			case R.id.action_about:
				aboutApplication();
				return true;
			case R.id.action_exit:
				closeApplication();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void closeApplication() {
		finish();
		System.exit(1);
	}
	
	private void aboutApplication() {
		onBeanListItemSelected(AboutFragment.newInstance());
	}

	@Override
	public void onBeanListItemSelected(Fragment fragment) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();

				fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								fragment).addToBackStack(null).commit();
	}

	@Override
	public void onBeanListItemSelected(Fragment fragment, int container) {
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager
				.beginTransaction()
				.replace(container,
						fragment).commit();
	}

	@Override
	public void onSearchBeanSelected(Search search, Parcelable bean) {
		if(bean instanceof Institution){
			onBeanListItemSelected(CourseListFragment.newInstance(((Institution)bean).getId(),
					search.getYear(),
					Institution.getCoursesByEvaluationFilter(((Institution)bean).getId(),search)));
		}else if(bean instanceof Course){
			onBeanListItemSelected(InstitutionListFragment.newInstance(((Course)bean).getId(),
					search.getYear(),
					Course.getInstitutionsByEvaluationFilter(((Course)bean).getId(),search)));
		}		
	}
}
