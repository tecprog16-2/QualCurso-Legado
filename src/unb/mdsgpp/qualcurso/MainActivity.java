package unb.mdsgpp.qualcurso;

import models.Bean;
import models.Course;
import models.Institution;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		if(position == drawerPosition){
			
		}else{
			switch (position) {
			case 0:
				if(fragmentManager.findFragmentById(R.id.container) == null){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new TabsFragment()).commit();
				}else if(!(fragmentManager.findFragmentById(R.id.container) instanceof TabsFragment)){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
							new TabsFragment()).commit();
				}
				drawerPosition = 0;
			break;
			case 1:
				if(fragmentManager.findFragmentById(R.id.container) == null){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new SearchByIndicatorFragment()).commit();
				}else if(!(fragmentManager.findFragmentById(R.id.container) instanceof SearchListFragment)){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new SearchByIndicatorFragment()).commit();
				}
				drawerPosition = 1;
				break;
			case 2:
				if(fragmentManager.findFragmentById(R.id.container) == null){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new SearchByIndicatorFragment()).commit();
				}else if(!(fragmentManager.findFragmentById(R.id.container) instanceof RankingFragment)){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new RankingFragment()).commit();
				}
				drawerPosition = 2;
			
				break;
			case 3:
				if(fragmentManager.findFragmentById(R.id.container) == null){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new SearchByIndicatorFragment()).commit();
				}else if(!(fragmentManager.findFragmentById(R.id.container) instanceof HistoricFragment)){
					fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new HistoricFragment()).commit();
				}
				drawerPosition = 3;
			default:
				break;
			}
		}
		
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	public void onSearchBeanSelected(Parcelable bean, String field, int year,
			int rangeA, int rangeB) {
		if(bean instanceof Institution){
			onBeanListItemSelected(CourseListFragment.newInstance(((Institution)bean).getId(),
					year,
					Institution.getCoursesByEvaluationFilter(((Institution)bean).getId(),field, 
							year, rangeA, 
							rangeB)));
		}else if(bean instanceof Course){
			onBeanListItemSelected(InstitutionListFragment.newInstance(((Course)bean).getId(),
					year,
					Course.getInstitutionsByEvaluationFilter(((Course)bean).getId(),field, 
							year, rangeA, 
							rangeB)));
		}
	}


}

	
