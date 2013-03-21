package org.awi.ui.server.databinder;

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

public class DashboardDataBinder extends BaseAdapter {

	private LayoutInflater layoutInFlater;
	private ViewHolder holder;
	private List<DashBoard> dashBoards;

	public DashboardDataBinder(Activity _activity, List<DashBoard> dashBoards) {
		this.dashBoards = dashBoards;
		layoutInFlater = (LayoutInflater) _activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return dashBoards.size();
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
			vi = layoutInFlater.inflate(R.layout.main_buddy_list_row_template, null);
			holder = new ViewHolder();

			holder.name = (TextView) vi.findViewById(R.id.name);
			holder.tagline = (TextView) vi.findViewById(R.id.tagline);

			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		holder.name.setText(dashBoards.get(position).getName());
		holder.tagline.setText(dashBoards.get(position).getTagLine());

		return vi;
	}

	private static class ViewHolder {
		TextView name;
		TextView tagline;
	}
}