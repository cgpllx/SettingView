package cc.easyandroid.settingview.util;

import android.util.SparseArray;

public class SparseArrayUtil {
	public static <E> boolean isEmpty(SparseArray<E> array){
		if(array==null||array.size()==0){
			return true;
		}
		return false;
	}
}
