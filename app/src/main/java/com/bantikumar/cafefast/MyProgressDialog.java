package com.bantikumar.cafefast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyProgressDialog {
    ProgressDialog progressDialog;

    public MyProgressDialog(Context context, String title,String message,boolean isCancellable,boolean cancelButton){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(isCancellable);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        if(cancelButton)
        {
            progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void stop(){
        progressDialog.dismiss();
    }
    public void start(){
        progressDialog.show();
    }

}
