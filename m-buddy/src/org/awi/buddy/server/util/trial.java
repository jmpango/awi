package org.awi.buddy.server.util;

public class trial {
	private String[]data;
	public trial() {
		data = new String[]{};
		data[0] = "";
	}
}


/*button.setOnClickListener(new OnClickListener() {
	 
	@Override
	public void onClick(View arg0) {

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:0377778888"));
		startActivity(callIntent);

	}

});*/

/*@Override
public void onItemClick(AdapterView<?> a,
        View v, int position, long id) {
    City city = (City) a.getItemAtPosition(position);
    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
    intent.putExtra("com.example.cities.City", city);
    startActivity(intent);
}
});

In your details activity, get the city from the extras on the intent:

Bundle bundle = getIntent().getExtras();
City city = bundle.getParcelable("com.example.cities.City");*/