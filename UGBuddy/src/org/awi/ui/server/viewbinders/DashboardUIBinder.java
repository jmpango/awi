package org.awi.ui.server.viewbinders;

import java.util.List;

import org.awi.ui.R;
import org.awi.ui.model.DashBoard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Responsible for binding dashboard data on the UI.
 * 
 * @author Jonathan
 * 
 */

public class DashboardUIBinder extends BaseAdapter {
	private LayoutInflater layoutInFlater;
	private ViewHolder viewHolder;
	private List<DashBoard> dashBoardList;

	public DashboardUIBinder(Activity activity, List<DashBoard> dashboardLists) {
		this.dashBoardList = dashboardLists;
		layoutInFlater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return dashBoardList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = layoutInFlater.inflate(R.layout.dashboardui_row_tpl, null);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) vi.findViewById(R.id.name);
			viewHolder.tagline = (TextView) vi.findViewById(R.id.tagline);

			vi.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) vi.getTag();
		}

		viewHolder.name.setText(dashBoardList.get(position).getName());
		viewHolder.tagline.setText(dashBoardList.get(position).getTagLine());

		return vi;
	}

	private static class ViewHolder {
		TextView name;
		TextView tagline;
	}
}
