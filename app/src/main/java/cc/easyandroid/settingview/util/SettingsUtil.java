package cc.easyandroid.settingview.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.reflect.Field;
import java.util.Set;

import cc.easyandroid.settingview.setting.annomotion.IsPara;


@SuppressWarnings("unchecked")
public final class SettingsUtil {
	private SharedPreferences mPreferences;

	private SettingsUtil(Context context) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public <T> void initParaFromPreferences(Para<T> para) {
		if (!mPreferences.contains(para.key)) {
			return;
		}
		Class<T> clazz = (Class<T>) para.value.getClass();
		if (clazz == String.class) {
			para.value = (T) mPreferences.getString(para.key, (String) para.value);
		} else if (clazz == int.class || clazz == Integer.class) {
			Integer i = mPreferences.getInt(para.key, (Integer) para.value);
			para.value = (T) i;
		} else if (clazz == boolean.class || clazz == Boolean.class) {
			Boolean b = mPreferences.getBoolean(para.key, (Boolean) para.value);
			para.value = (T) b;
		} else if (clazz == float.class || clazz == Float.class) {
			Float f = mPreferences.getFloat(para.key, (Float) para.value);
			para.value = (T) f;
		} else if (clazz == long.class || clazz == Long.class) {
			Long f = mPreferences.getLong(para.key, (Long) para.value);
			para.value = (T) f;
		} else if (clazz == Set.class) {
			Set<String> s = mPreferences.getStringSet(para.key, (Set<String>) para.value);
			para.value = (T) s;
		}
	}

	public static void initPrar(Context context, Class<?> object) {
		try {
			Field[] fields = object.getFields();
			SettingsUtil settingsUtil = new SettingsUtil(context);
			for (Field field : fields) {
				IsPara kPara = field.getAnnotation(IsPara.class);
				if (kPara != null && field.getType() == Para.class) {
					Para<Object> para = (Para<Object>) field.get(object);
					settingsUtil.initParaFromPreferences(para);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
