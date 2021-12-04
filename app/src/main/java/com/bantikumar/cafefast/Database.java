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


    // TODO : Close all connections

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
    int rowEffected=0;
    boolean check;


    public  static  String error="default";
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
                    error=e.getMessage();
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            error=e.getMessage();
            e.printStackTrace();
            status = false;
        }
        return status;
    }



    public boolean addUser(Student student){
        if(isInternetAvailable()){
            if(connect()) {
                String query = "insert into STUDENTS (first_name,last_name,email,password) VALUES  ('" + student.getFirstname() + "','" + student.getLastname() + "','" + student.getEmail() + "','" + student.getPassword() + "');";
                String queryCheckAvailablity = "select * from STUDENTS where email = '"+student.getEmail().toString()+"';";
                try {
                    ResultSet resultSet = st.executeQuery(queryCheckAvailablity);
                    if(resultSet.next()){
                        error = "User already exists";
                        return false;
                    }
                    rowEffected = st.executeUpdate(query);
                    if(rowEffected>0)
                        return true;
                }
                catch (Exception e){
                    error = e.getMessage().toString();
                    return false;
                }
            }
            else
                return false;
        }
        else{
            error = "Internet connection not found";
            return false;
        }
        return true;
    }



    public Student login(String email, String password){
        Student student = null;
        if(isInternetAvailable()){
            if(connect()) {
                String query = "select * from STUDENTS where email = '"+email+"' and password = '"+password+"';";
                try {
                    ResultSet resultSet = st.executeQuery(query);
                    if(resultSet.next()){
                        student = new Student(resultSet.getString("first_name"),resultSet.getString("last_name"),resultSet.getString("email"),resultSet.getString("password"));
                    }
                    else
                        error = "Please check your email or password";
                }
                catch (Exception e){
                    error = e.getMessage().toString();
                    return null;
                }
            }
            else
                return null;
        }
        else{
            error = "Internet connection not found";
            return null;
        }
        return student;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            error = e.getMessage();
        }
        return false;
    }
    public List<Item> getAllItems(String email){
        List<Item> items=null;
        List<Integer> favouriteItems=null;
        if(isInternetAvailable()){
            if(connect()){
                String query = "select * from item order by iname;";
                String favQuery = "select item_id from favourite_item where email='"+email.toString()+"';";
                try {
                    ResultSet resultSet = st.executeQuery(query);
                    items = new ArrayList<>();
                    while (resultSet.next())
                    {
                        int id = resultSet.getInt("item_id");
                        items.add(new Item(id,resultSet.getString("iname"),"Default Decription",(double)resultSet.getInt("price"),resultSet.getInt("available_qty"),12,false));
                    }

                    favouriteItems = new ArrayList<>();
                    ResultSet fav = st.executeQuery(favQuery);
                    while (fav.next())
                        favouriteItems.add(fav.getInt("item_id"));

                    for(int i=0;i<items.size();i++){
                        for(int j=0;j<favouriteItems.size();j++){
                            if(items.get(i).getItemId() == favouriteItems.get(j)){
                                items.get(i).setFavourite(true);
                            }
                        }
                    }


                }
                catch (Exception e){
                    error = e.getMessage();
                    return null;
                }
            }
        }
        else
        {
            error = "Internet connection not found";
            return null;
        }
        return items;
    }

    public boolean addFavouriteItem(String email,int item_id){
        if(isInternetAvailable()){
            if(connect()){
                String query = "insert into favourite_item (email,item_id) values ('"+email+"',"+item_id+");";
                try {
                    int n = st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else
                return false;
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }

    public  boolean deleteFavourite(String email,int item_id){
        if(isInternetAvailable()){
            if(connect()){
                String query = "delete from favourite_item where email = '"+email+"' and item_id = "+item_id+";";
                try {
                    int n = st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else
                return false;
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }


}