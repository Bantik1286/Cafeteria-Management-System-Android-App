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

public class updateNameDialog extends DialogFragment {


    TextInputLayout editor;
    Button update,cancel;
    String oldName;
    char updateName;
    Dialog progressDialog;
    boolean flag;
    Database db;

    updateNameDialog(char updateName,String oldName){
        this.updateName = updateName;
        this.oldName = oldName;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_name_dialog,container,false);

        setCancelable(false);

        update = v.findViewById(R.id.update_name_dilog_update_btn);
        cancel = v.findViewById(R.id.update_name_dilog_cancel_btn);
        editor = v.findViewById(R.id.update_name_dilog_input_layout);
        editor.getEditText().setText(oldName);
        if(updateName=='l')
            editor.setHint("Last name");
        if(updateName=='f')
            editor.setHint("First name");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        db = new Database();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateName =='l'){
                    if(editor.getEditText().getText().toString().isEmpty()){
                        editor.setError("You can leave this filed empty");
                        editor.setErrorEnabled(true);
                    }
                    else
                        editor.setErrorEnabled(false);
                    if(!editor.isErrorEnabled()) {
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
                                flag = db.updateLastName(editor.getEditText().getText().toString());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                super.onPostExecute(o);
                                progressDialog.dismiss();
                                if(flag){
                                    Toast.makeText(getActivity(), "last name updated Successfully", Toast.LENGTH_SHORT).show();
                                    Dashboard.student.setLastname(editor.getEditText().getText().toString());
                                    Dashboard.fullname.setText(Dashboard.student.getFirstname() + " " + Dashboard.student.getLastname());
                                    ProfileActivity.lastname.setText(Dashboard.student.getLastname());
                                }
                                else
                                    Toast.makeText(getActivity(), db.error, Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }.execute();
                    }
                }
                else if(updateName == 'f'){
                    if(editor.getEditText().getText().toString().isEmpty()){
                        editor.setError("You can leave this filed empty");
                        editor.setErrorEnabled(true);
                    }
                    else
                        editor.setErrorEnabled(false);
                    if(!editor.isErrorEnabled()) {

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
                                flag = db.updateFirstName(editor.getEditText().getText().toString());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                super.onPostExecute(o);
                                progressDialog.dismiss();
                                if(flag){
                                    Dashboard.student.setFirstname(editor.getEditText().getText().toString());
                                    Dashboard.fullname.setText(Dashboard.student.getFirstname() + " " + Dashboard.student.getLastname());
                                    ProfileActivity.firstname.setText(Dashboard.student.getFirstname());
                                    Toast.makeText(getActivity(), "first name updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                               else
                                Toast.makeText(getActivity(), db.error, Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }.execute();
                    }
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
