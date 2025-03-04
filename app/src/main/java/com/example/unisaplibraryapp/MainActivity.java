package com.example.unisaplibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    static MysqlConnection connectionClass;
    static Connection con;
    ResultSet rs;
    String name,str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
        finish(); // Finish MainActivity so that the user can't return to it
        connectionClass = new MysqlConnection();
        connect();
    }
    public void connect(){
        Log.d("ConnectionTest", "Connected: " + (con != null));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            try{
                con = connectionClass.CONN();
                if(con == null){
                    str = "Error in connection with MYSQL server";
                }
                else{
                    str = "Connected with MYSQL server";
                }
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            });
        });
    }
    public static String fetchFromMySQL(String usn) {
        String fetchedEmail = null;
        try {
            con = connectionClass.CONN();
            String query = "SELECT email FROM students WHERE usn = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, usn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                fetchedEmail = rs.getString("email");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return fetchedEmail;
    }
}
