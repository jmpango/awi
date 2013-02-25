package org.m.awi.data.binder;

import java.util.HashMap;
import java.util.List;

import org.m.awi.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardDataBinder extends BaseAdapter {
	private final String KEY_NAME = "name";
	private final String KEY_TAGLINE = "tagline";
	/*private final String KEY_ICON = "icon";*/
	
	private LayoutInflater _inflater;
	private List<HashMap<String, String>> _dashboardData;
	private ViewHolder holder;
	
	public DashboardDataBinder(Activity _activity, List<HashMap<String,String>> map){
		this._dashboardData = map;
		
		_inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public int getCount() {
		return _dashboardData.size();
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
		if(convertView == null) {
			vi = _inflater.inflate(R.layout.dashboard_list_row, null);
			holder = new ViewHolder();
			
			holder.name = (TextView) vi.findViewById(R.id.name);
			holder.tagline = (TextView) vi.findViewById(R.id.tagline);
			holder.itemImage = (ImageView) vi.findViewById(R.id.list_image);
			
			vi.setTag(holder);
		}else{
			holder = (ViewHolder) vi.getTag();
		}
		
		holder.name.setText(_dashboardData.get(position).get(KEY_NAME));
		holder.tagline.setText(_dashboardData.get(position).get(KEY_TAGLINE));
		
		String uri = "drawable/" + /*_dashboardData.get(position).get(KEY_ICON)*/"img_placeholder";
		int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	    Drawable image = vi.getContext().getResources().getDrawable(imageResource);
		
	    holder.itemImage.setImageDrawable(image);
	    
		return vi;
	}

	static class ViewHolder{
		TextView name;
		TextView tagline;
		ImageView itemImage;
	}
}
