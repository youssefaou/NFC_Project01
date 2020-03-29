package com.example.nfcproject01.reader;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //
        //Log.d("DEBUG", "showing dialog!");

        Dialog dialog = new Dialog(this);
         dialog.setTitle("Your Widget Name");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
        //
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                finish();
            }

        });
    }

}
