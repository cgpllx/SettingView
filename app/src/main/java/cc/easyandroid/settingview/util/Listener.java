package cc.easyandroid.settingview.util;

import cc.easyandroid.settingview.setting.GroupView;
import cc.easyandroid.settingview.setting.RowView;

public class Listener {
	public interface OnGroupViewItemClickListener {

		void onItemClick(GroupView groupView, RowView rowView);
	}

	public interface OnRowViewClickListener {

		void onRowViewClick(RowView rowView);
	}
}
