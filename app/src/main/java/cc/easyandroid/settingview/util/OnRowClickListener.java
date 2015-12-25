package cc.easyandroid.settingview.util;


import cc.easyandroid.settingview.setting.RowView;

public interface OnRowClickListener<T extends RowView> {
	void onRowClick(T rowView, RowViewActionEnum action);
}
