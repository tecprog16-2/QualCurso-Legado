package unb.mdsgpp.qualcurso;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<HashMap<String, String>> {

public ListAdapter(Context context, int textViewResourceId) {
    super(context, textViewResourceId);
}

public ListAdapter(Context context, int resource, List<HashMap<String, String>> items) {
    super(context, resource, items);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

    View v = convertView;

    if (v == null) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.list_item, null);

    }

    HashMap<String, String> h = getItem(position);

    if (h != null) {

        TextView rank = (TextView) v.findViewById(R.id.position);
        TextView institutionName = (TextView) v.findViewById(R.id.university);
        TextView value = (TextView) v.findViewById(R.id.data);

        if (rank != null) {
            rank.setText(Integer.toString(position+1));
        }
        if (institutionName != null) {

        	institutionName.setText(h.get("acronym"));
        }
        if (value != null) {
            value.setText(h.get(h.get("order_field")));
        }
    }

    return v;

}
}