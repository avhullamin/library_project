package com.example.unisaplibraryapp;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MysqlConnection {
    protected static String host= "sql12.freesqldatabase.com";
    protected static String dbName= "sql12765871";
    protected static String userName= "sql12765871";
    protected static String password= "puIGfeuGI6";
    protected static String port= "3306";
    protected static String email;
    public Connection CONN() {
        Connection conn = null;
        try{
            String connectionString = "jdbc:mysql://"+host+":"+port+"/"+dbName;
            conn = DriverManager.getConnection(connectionString,userName,password);
        }
        catch (Exception e){
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }

}
