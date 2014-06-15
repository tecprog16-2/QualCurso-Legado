package unb.mdsgpp.qualcurso;

import java.text.SimpleDateFormat;
import java.util.List;

import models.Institution;
import models.Search;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListCompareAdapter extends ArrayAdapter<Institution>{

	public ListCompareAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	public ListCompareAdapter(Context context, int resource, List<Institution> item) {
		super(context, resource, item);
	}
	
	@Override
	public View getView(int position, View contextView, ViewGroup parent){
		View currentView = contextView;

		if(currentView == null){
			LayoutInflater li;
			li = LayoutInflater.from(getContext());
			currentView = li.inflate(R.layout.compare_choose_list_item, null);
		}

		Institution i = getItem(position);

		if(i != null){
			CheckBox cb = (CheckBox) currentView.findViewById(R.id.compate_institution_checkbox);
			cb.setText(i.getAcronym());
		}

		return currentView;
	}
}
