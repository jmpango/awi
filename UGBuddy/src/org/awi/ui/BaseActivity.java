package org.awi.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity{

	public TextView msgLabel;
	public EditText dashBoardSearchBox, searchBox;
	public ImageButton homeBtn, advancedSearchBtn;
	
	public BaseActivity() {
	}

	public void hideMsgBox() {
		msgLabel = (TextView) findViewById(R.id.msg_label);
		msgLabel.setVisibility(View.GONE);
	}

	public void showMsgBox(String msg) {
		msgLabel.setVisibility(View.VISIBLE);
		msgLabel.setText(msg);
	}
	
	public void showLog(String tag, String message){
		Log.d(tag, message);
	}
	
	public void showToast(Activity activity, String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

}
