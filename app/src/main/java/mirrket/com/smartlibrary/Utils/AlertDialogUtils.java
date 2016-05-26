package mirrket.com.smartlibrary.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;


public class AlertDialogUtils {

	public static class AlertDialogItem implements CharSequence {
		public String name;
		public Runnable handler;
		public AlertDialogItem(String name, Runnable handler ) {
			this.name = name;
			this.handler = handler;
		}
		@Override
		public String toString() {
			return name;
		}
		@Override
		public char charAt(int index) {
			return name.charAt(index);
		}
		@Override
		public int length() {
			return name.length();
		}
		@Override
		public CharSequence subSequence(int start, int end) {
			return name.subSequence(start, end);
		}
	}


	public static void showContextDialogue(Context context, String title, ArrayList<AlertDialogItem> items) {
		if (items.size() > 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(title);

			final AlertDialogItem[] itemArray = new AlertDialogItem[items.size()];
			items.toArray(itemArray);
	
			builder.setItems(itemArray, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	itemArray[item].handler.run();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}		
	}

}
