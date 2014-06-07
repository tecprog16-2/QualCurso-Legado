package unb.mdsgpp.qualcurso;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListHistoryAdapter extends ArrayAdapter<HashMap<String, String>>{
	public ListHistoryAdapter(Context context, int textViewResourceId){
		super(context, textViewResourceId);
	}
	
	public ListHistoryAdapter(Context context, int resource, List<HashMap<String, String>> items){
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
			
	HashMap<String, String> h = getItem(position);
	
	if(h != null){
		TextView search_by = (TextView) v.findViewById(R.id.searchBy);
	}
	return v;
	}
	
}
