package org.awi.ui.server.viewbinders;

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

public class ListingUIBinder extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private ViewHolder holder;
	private List<Buddy> buddies;

	public ListingUIBinder(Activity activity, List<Buddy> buddies) {
		this.buddies = buddies;
		layoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return buddies.size();
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
			vi = layoutInflater.inflate(R.layout.listingui_row_tpl, null);
			holder = new ViewHolder();

			holder.name = (TextView) vi.findViewById(R.id.listing_name);
			holder.tagline = (TextView) vi.findViewById(R.id.listing_tagline);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		holder.name.setText(buddies.get(position).getName());
		holder.tagline.setText(buddies.get(position).getTagLine());

		return vi;
	}

	private static class ViewHolder {
		TextView name;
		TextView tagline;
	}

}
