package org.awi.buddy.server;

import java.util.List;

import org.awi.buddy.model.DashBoard;
import org.awi.buddy.ui.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainBuddyDataBinder extends BaseAdapter {

	private LayoutInflater layoutInFlater;
	private ViewHolder holder;
	private List<DashBoard> dashBoards;

	public MainBuddyDataBinder(Activity _activity, List<DashBoard> dashBoards) {
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
			vi = layoutInFlater.inflate(R.layout.dashboard_list_row, null);
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

	static class ViewHolder {
		TextView name;
		TextView tagline;
	}
}
