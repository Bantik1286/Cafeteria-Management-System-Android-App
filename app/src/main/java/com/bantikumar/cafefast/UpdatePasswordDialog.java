package com.bantikumar.cafefast;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;


public class UpdatePasswordDialog extends DialogFragment {

    Button update,cancel;
    TextInputLayout oldPass,newPass;
    Dialog progressDialog;
    Database db;
    boolean flag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_password_dialog,container,false);
        update = v.findViewById(R.id.update_password_dialog_update_btn);
        cancel = v.findViewById(R.id.update_password_dialog_cancel_btn);
        oldPass = v.findViewById(R.id.update_password_dialog_input_layout1);
        newPass = v.findViewById(R.id.update_password_dialog_input_layout2);
        db = new Database();
        setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean v = true;
                if(oldPass.getEditText().getText().toString().isEmpty()){
                    oldPass.setError("You can't leave this field empty");
                    oldPass.setErrorEnabled(true);
                    v = false;
                }
                else{
                    if (!oldPass.getEditText().getText().toString().equals(Dashboard.student.getPassword().toString())){
                        oldPass.setErrorEnabled(true);
                        oldPass.setError("Please enter correct password");
                        v = false;
                    }
                    else
                        oldPass.setErrorEnabled(false);
                }if(newPass.getEditText().getText().toString().isEmpty()){
                    newPass.setError("You can't leave this field empty");
                    newPass.setErrorEnabled(true);
                    v = false;
                }
                else
                    newPass.setErrorEnabled(false);
                if(v){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                            progressDialog = new Dialog(getActivity());
                            progressDialog.setContentView(R.layout.loading_dialog);
                            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            flag = db.updatePassword(newPass.getEditText().getText().toString());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            progressDialog.dismiss();
                            if(flag){
                                Toast.makeText(getActivity(), "Password updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getActivity(), db.error, Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }.execute();
                }
            }
        });

        return v;
    }
    @Override
    public void onResume() {
        // Sets the height and the width of the DialogFragment
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }
}
