package unb.mdsgpp.qualcurso;

import java.util.List;

import models.Search;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListHistoryAdapter extends ArrayAdapter<Search>{
	public ListHistoryAdapter(Context context, int textViewResourceId){
		super(context, textViewResourceId);
	}

	public ListHistoryAdapter(Context context, int resource, List<Search> items){
		super(context,resource, items);
	}

	@Override
	public View getView(int position, View contextView, ViewGroup parent){
		View v = contextView;

		if(v == null){
			LayoutInflater li;
			li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.history_list_item, null);
		}

	Search s = getItem(position);

	if(s != null){
		TextView option = (TextView) v.findViewById(R.id.option);
		TextView year = (TextView) v.findViewById(R.id.year);
		TextView indicator = (TextView) v.findViewById(R.id.indicator);
		TextView firstValue = (TextView) v.findViewById(R.id.firstValue);
		TextView secondValue = (TextView) v.findViewById(R.id.secondValue);
		TextView searchDate = (TextView) v.findViewById(R.id.searchDate);

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
			searchDate.setText(s.getDate().toString());
		}
	}

	return v;
	}
	
}
