package com.wannoo.rit;

import android.content.Context;
import android.widget.Toast;

/**
 * 可马上替换消失的Toast
 */
public final class ToolsToast {
	private ToolsToast() {}
	private static Toast toast;

	public static void showToast(Context context ,String text) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}

}
