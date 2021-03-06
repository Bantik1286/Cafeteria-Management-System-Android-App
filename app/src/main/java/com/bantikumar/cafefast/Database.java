package com.bantikumar.cafefast;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
    String email;
    public static List<OrderClass> orders;
    public static List<SelectedItem> cartItems;
    public  static  String error="";

    public Database() {
        this.url = String.format(this.url, this.host, this.port, this.database);
    }

    public Database(String email){
        this.url = String.format(this.url, this.host, this.port, this.database);
        this.email = email;
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
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
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



    public Student login(String email){
        Student student = null;
        if(isInternetAvailable()){
            if(connect()) {
                String query = "select * from STUDENTS where email = '"+email+"';";
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
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
                String query = "select I.item_id, iname, available_qty, price, email from item  I left join favourite_item  F on I.item_id = F.item_id order by iname;";
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    ResultSet resultSet = st.executeQuery(query);
                    items = new ArrayList<>();
                    while (resultSet.next())
                    {
                        if(resultSet.getString("email")!=null)
                        items.add(new Item(resultSet.getInt("item_id"),resultSet.getString("iname"),"Default Decription",(double)resultSet.getInt("price"),resultSet.getInt("available_qty"),null,resultSet.getString("email").equals(Dashboard.email.toString())));
                        else
                            items.add(new Item(resultSet.getInt("item_id"),resultSet.getString("iname"),"Default Decription",(double)resultSet.getInt("price"),resultSet.getInt("available_qty"),null,false));
                    }
                    resultSet.close();
                    for(Item item : items){
                        List<Category> categories = new ArrayList<>();
                        String catQuery = "select item_category.cat_id as id, cat_name from item_category inner join category on item_category.cat_id = category.cat_id where item_id = "+item.getItemId()+";";
                        resultSet = st.executeQuery(catQuery);
                        while(resultSet.next()){
                            categories.add(new Category(resultSet.getInt("id"),resultSet.getString("cat_name")));
                        }
                        item.setCategoryList(categories);
                    }
                    resultSet.close();
                    connection.close();
                    st.close();
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
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
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
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    int n = st.executeUpdate(query);
                    st.close();
                    connection.close();

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


    /*
       // TODO : Check Availablity of items
    public boolean placeOrder(OrderClass orderClass){
        String insertOrderTupple = "insert into orders (description,status," +       // TODO : Insert Date
                "total_price,placed_by) values ('" +
                orderClass.getRequirement() +"','"+orderClass.getStatus()+"',"+String.valueOf(orderClass.getTotalAmount())+",'"+orderClass.getPlaceBy()+"');";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    st.executeUpdate(insertOrderTupple);
                    String queryOrderId = "select max(order_id) as id from orders where placed_by = '"+orderClass.getPlaceBy()+"';";
                    ResultSet s = st.executeQuery(queryOrderId);
                    if(s.next()) {
                        int id = s.getInt("id");
                        s.close();
                        for (SelectedItem item : orderClass.getItems()) {
                            String item_tupple = "insert into order_items (order_id,item_id,qty,price) VALUES (" + String.valueOf(id) + "," + String.valueOf(item.getItem().getItemId()) + "," + String.valueOf(item.getQuantity()) + "," + String.valueOf(item.getItem().getPrice()) + ");";
                            st.close();
                            st = connection.createStatement();
                            st.executeUpdate(item_tupple);
                        }
                        s.close();
                        st.close();
                        connection.close();
                        return true;
                    }
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
        return false;
    }
     */

    public boolean addToCart(String email, SelectedItem item) {
        String query = "insert into cart (email, item_id, qty) values ('" + email + "'," + String.valueOf(item.getItem().getItemId()) + "," + String.valueOf(item.getQuantity()) + ");";
        if (isInternetAvailable()) {
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    int n = st.executeUpdate(query);
                     return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }else {
                return false;
            }
        } else {
            error = "Internet connection not found";
            return false;
        }
    }

    public List<SelectedItem> placeOrderTransaction(OrderClass orderClass){
        java.util.Date dateJ = new java.util.Date();
        java.sql.Timestamp sqlTime=new java.sql.Timestamp(dateJ.getTime());
        List<SelectedItem> unavaileAbleItems = null;
        String insertOrderTupple = "insert into orders (start_date,description,status," +       // TODO : Insert Date
                "total_price,placed_by) values ('"+sqlTime+"','" +
                orderClass.getRequirement() +"','"+orderClass.getStatus()+"',"+String.valueOf(orderClass.getTotalAmount())+",'"+orderClass.getPlaceBy()+"');";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(false);
                    st = connection.createStatement();
                    st.executeUpdate(insertOrderTupple);
                    String queryOrderId = "select max(order_id) as id from orders where placed_by = '"+orderClass.getPlaceBy()+"';";
                    ResultSet s = st.executeQuery(queryOrderId);
                    if(s.next()) {
                        int id = s.getInt("id");
                        s.close();
                        unavaileAbleItems = new ArrayList<>();
                        for (SelectedItem item : orderClass.getItems()) {
                            String updateQtyItem = "update item set available_qty = available_qty - "+String.valueOf(item.getQuantity())+" where item_id = "+String.valueOf(item.getItem().getItemId())+";";
                            st.executeUpdate(updateQtyItem);
                        }
                        unavaileAbleItems = null;

                        for (SelectedItem item : orderClass.getItems()) {
                            String insertItems = "insert into order_items (order_id,item_id,qty,price) VALUES (" + String.valueOf(id) + "," + String.valueOf(item.getItem().getItemId()) + "," + String.valueOf(item.getQuantity()) + "," + String.valueOf(item.getItem().getPrice()) + ");";
                            st.executeUpdate(insertItems);
                        }

                        if(CartFragement.cartOrder){
                            String q = "delete from cart where email = '"+Dashboard.email+"';";
                            st.executeUpdate(q);
                        }

                        connection.commit();
                        st.close();
                        connection.close();
                        return null;
                    }
                }
                catch (Exception e){
                    if(unavaileAbleItems == null) {
                        error = e.getMessage();
                        return new ArrayList<>();
                    }
                    else{
                        if(connect()){
                            try{
                                ResultSet s;
                                for (SelectedItem item : orderClass.getItems()) {
                                    String q = "select item_id from item where available_qty < "+String.valueOf(item.getQuantity())+" and item_id = "+String.valueOf(item.getItem().getItemId())+";";
                                    s = st.executeQuery(q);
                                    if(s.next()){
                                        unavaileAbleItems.add(item);
                                    }
                                    s.close();
                                }
                                return unavaileAbleItems;
                            }
                            catch (Exception w){
                                return new ArrayList<>();
                            }
                        }
                    }
                }
            }
            else
                return new ArrayList<>();
        }
        else{
            error = "Internet connection not found";
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }























    public boolean getAllOrders(){
        orders  = null;
        String query = "select *  from orders where placed_by = '"+email.toString().trim()+"';";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    orders = new ArrayList<OrderClass>();
                    ResultSet rs = st.executeQuery(query);
                    while(rs.next()){
                        Timestamp timestamp =rs.getTimestamp("start_date");
                        char status = rs.getString("status").charAt(0);
                        if(status=='R')
                            orders.add(0,new OrderClass(rs.getInt("order_id"),rs.getString("placed_by"),rs.getString("description"),status,rs.getTimestamp("start_date")));
                        else
                        orders.add(new OrderClass(rs.getInt("order_id"),rs.getString("placed_by"),rs.getString("description"),status,rs.getTimestamp("start_date")));
                    }

                    for(OrderClass order : orders){
                        String query1 = "select order_items.item_id, iname ,item.price, qty from order_items inner join item on item.item_id = order_items.item_id  where order_id = "+String.valueOf(order.getOrderId())+";";
                        rs = st.executeQuery(query1);
                        List<SelectedItem> items = new ArrayList<>();
                        while(rs.next()){
                            items.add(new SelectedItem(new Item(rs.getInt("item_id"),(double)rs.getInt("price"),rs.getString("iname")),rs.getInt("qty")));
                        }
                        order.setItems(items);
                        rs.close();
                    }
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }

    boolean getCartItems(){
        String query = "select item.item_id, qty, iname,price from item inner join cart on item.item_id = cart.item_id where email = '"+email+"';";
        if(isInternetAvailable()){
            cartItems = null;
            if(connect()){

                cartItems = new ArrayList<>();
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while(rs.next()){
                        cartItems.add(new SelectedItem(new Item(rs.getInt("item_id"),(double)rs.getInt("price"),rs.getString("iname")),rs.getInt("qty")));
                    }
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }


    public boolean updateCartItem(int item_id,int qty){
        String query = "update  cart set qty = "+String.valueOf(qty)+" where item_id = "+String.valueOf(item_id)+" and email = '"+Dashboard.email+"';";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }

    public boolean deleteItemFromCart(int item_id){
        String query = "delete from cart where email = '"+Dashboard.email+"' and item_id = "+String.valueOf(item_id)+";";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }

    public boolean deleteAllCartItem(){
        String query = "delete from cart where email = '"+Dashboard.email+"';";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }

    public boolean updateFirstName(String newFirstName){
        String query = "update students set first_name = '"+newFirstName+"' where email = '"+Dashboard.email+"';";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }
    public boolean updateLastName(String newLastName){
        String query = "update students set last_name = '"+newLastName+"' where email = '"+Dashboard.email+"';";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }

    public boolean updatePassword(String newPass){
        String query = "update students set password = '"+newPass+"' where email = '"+Dashboard.email+"';";
        if(isInternetAvailable()){
            if(connect()){
                try {
                    connection.setAutoCommit(true);
                    st = connection.createStatement();
                    st.executeUpdate(query);
                    return true;
                }
                catch (Exception e){
                    error = e.getMessage();
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            error = "Internet connection not found";
            return false;
        }
    }



}