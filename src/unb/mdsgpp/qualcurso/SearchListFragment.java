package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Bean;
import android.support.v4.app.ListFragment;

public class SearchListFragment extends ListFragment{
	
	private static final String YEAR = "year";
	private static final String FIELD = "field";
	private static final String RANGE_A = "rangeA";
	private static final String RANGE_B = "rangeB";
	private static final String BEAN_TYPE = "beanType";
	private ArrayList<? extends Bean> list = null;
	
	public SearchListFragment() {
	}
	
	public SearchListFragment initialize(ArrayList<? extends Bean> list, Bean type, int year, String field, int rangeA, int rangeB){
		this.list = list;
		return this;
	}
	
	

}
