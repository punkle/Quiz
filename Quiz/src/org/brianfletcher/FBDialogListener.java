package org.brianfletcher;

import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FBDialogListener implements DialogListener {

	public void onComplete(Bundle values) {
		System.out.println("onComplete");
	}

	public void onFacebookError(FacebookError e) {
		System.out.println("onFacebookError");
	}

	public void onError(DialogError e) {
		System.out.println("onError");
	}

	public void onCancel() {
		System.out.println("onCancel");
	}

}
