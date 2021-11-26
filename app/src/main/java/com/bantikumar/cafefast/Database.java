package com.bantikumar.cafefast;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {


    private  final String host = "db.bskteeklvsixrtwmuqbp.supabase.co";
    private  final String database = "postgres";
    private  final int port = 5432;
    private  final String user = "postgres";
    private  final String pass = "03446766677";
    private  String url = "jdbc:postgresql://%s:%d/%s";
    private  boolean status = false;
    private Connection connection=null;
     private Statement st=null;
     public static String msg= "default";
    MyProgressDialog p;
    int rowEffected=0;
    boolean check;

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
    }


    public boolean connect() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    st = connection.createStatement();
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    msg=e.getMessage();
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            msg=e.getMessage();
            e.printStackTrace();
            status = false;
        }
        return status;
    }


    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            msg = e.getMessage();
        }
        return false;
    }
    public boolean addUser(Student student){
        check = false;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String query = "insert into STUDENTS (first_name,last_name,email,password) VALUES  ('" + student.getFirstname() + "','" + student.getLastname() + "','" + student.getEmail() + "','" + student.getPassword() + "');";
                    rowEffected = 0;
                    if (isInternetAvailable()) {
                        if (status && st != null) {
                            try {
                                rowEffected = st.executeUpdate(query);
                                check = true;
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                                msg = throwables.getMessage();
                                check = false;
                            }
                        } else {
                            if (connect())
                                addUser(student);
                            else
                                check = false;
                        }
                    } else {
                        msg = "Please check your internet connection.";
                        check = false;
                    }
                    check = rowEffected != 0;
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        if(!check)
            msg = "User Already exisits";
        return check;
    }

    public boolean Login(String email,String password){
        check = false;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String query = "select * from STUDENTS where email='"+email+"' and password='"+password+"';";
                rowEffected = 0;
                if (isInternetAvailable()) {
                    if (status && st != null) {
                        try {
                            ResultSet r = st.executeQuery(query);
                            if(r.next())
                            check = true;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            msg = throwables.getMessage();
                            check = false;
                        }
                    } else {
                        if (connect())
                            Login(email,password);
                        else
                            check = false;
                    }
                } else {
                    msg = "Please check your internet connection.";
                    check = false;
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!check)
            msg = "No such user exists";
        return check;
    }



}