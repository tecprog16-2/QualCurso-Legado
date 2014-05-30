package unb.mdsgpp.qualcurso;

import android.os.Parcelable;
import android.support.v4.app.Fragment;

public abstract interface BeanListCallbacks {
	void onBeanListItemSelected(Fragment fragment);
	void onBeanListItemSelected(Fragment fragment, int container);
	void onSearchBeanSelected(Parcelable bean, String field, int year, int rangeA, int rangeB);
}
