package org.awi.ui.server.util;

import org.awi.ui.server.service.AlertPositiveListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class BuddyDialogRadio extends DialogFragment {
	private String[] data;
	private String title;
	private AlertPositiveListener alertPositiveListener;
	private String oKButtonName;

	public BuddyDialogRadio() {
	}

	public BuddyDialogRadio(String[] data, String title, String oKButtonName) {
		this.data = data;
		this.title = title;
		this.oKButtonName = oKButtonName;
	}

	public void onAttach(android.app.Activity activity) {
		super.onAttach(activity);
		try {
			alertPositiveListener = (AlertPositiveListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement AlertPositiveListener");
		}
	}

	OnClickListener positiveListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			AlertDialog alert = (AlertDialog) dialog;
			if (alert.getListView() != null) {
				int position = alert.getListView().getCheckedItemPosition();
				alertPositiveListener.onPositiveClick(position);
			}
		}
	};

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		int position = bundle.getInt("position");
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		b.setTitle(title);
		b.setSingleChoiceItems(data, position, null);
		b.setPositiveButton(oKButtonName, positiveListener);
		b.setNegativeButton("Cancel", null);
		AlertDialog d = b.create();
		data = new String[] {};
		return d;
	}
}
