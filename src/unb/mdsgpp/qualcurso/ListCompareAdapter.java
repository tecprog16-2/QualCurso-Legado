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
		View v = contextView;

		if(v == null){
			LayoutInflater li;
			li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.compare_list_item, null);
		}

	Institution i = getItem(position);

	if(i != null){
		CheckBox cb = (CheckBox) v.findViewById(R.id.compare_list_checkbox);
		if (option != null){
			if (s.getOption() == Search.COURSE) {
				option.setText(R.string.course);
			}else if (s.getOption() == Search.INSTITUTION) {
				option.setText(R.string.institution);
			}
		}

		if(year != null){
			year.setText(Integer.toString(s.getYear()));
		}

		if(indicator != null){
			indicator.setText(s.getIndicator().getName());
		}

		if( firstValue != null ) {
			firstValue.setText(Integer.toString(s.getMinValue()));
		}

		if( secondValue != null ) {
			int max = s.getMaxValue();

			if( max == -1 ) {
				secondValue.setText(R.string.maximum);
			} else {
				secondValue.setText(Integer.toString(max));	
			}
		}
		
		if( searchDate != null ) {
			searchDate.setText(SimpleDateFormat.getDateTimeInstance().format(s.getDate()));
		}
	}

	return v;
	}

	
}
