package unb.mdsgpp.qualcurso;

import android.app.Application;
import android.content.Context;

public class QualCurso extends Application {
	
	private static Context context;
	private static QualCurso instance;
	
	public QualCurso(){
		//context = getApplicationContext();
		instance = this;
	}
	
	public static Context getContext(){
		return context;
	}
	public static QualCurso getInstance(){
		return instance;
	}
}
