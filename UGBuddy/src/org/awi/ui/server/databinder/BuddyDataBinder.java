package org.awi.ui.server.databinder;

import java.util.List;

import org.awi.ui.R;
import org.awi.ui.model.Buddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BuddyDataBinder extends BaseAdapter {

	private LayoutInflater layoutInFlater;
	private ViewHolder holder;
	private List<Buddy> dataz;

	public BuddyDataBinder(Activity activity, List<Buddy> dataz) {
		this.dataz = dataz;
		layoutInFlater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return dataz.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = layoutInFlater.inflate(R.layout.listing_row_template, null);
			holder = new ViewHolder();

			holder.name = (TextView) vi.findViewById(R.id.listing_name);
			holder.tagline = (TextView) vi.findViewById(R.id.listing_tagline);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		holder.name.setText(dataz.get(position).getName());
		holder.tagline.setText(dataz.get(position).getTagLine());

		return vi;
	}

	private static class ViewHolder {
		TextView name;
		TextView tagline;
	}
}
