package com.example.unisaplibraryapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

import javax.mail.MessagingException;

public class LoginPage extends AppCompatActivity {

    private EditText otpInput;
    private Button sendOtpButton;
    private final String appEmail = "abhisheksaini5656000@gmail.com"; // Your Gmail address
    private final String appPassword = "suib vadt aehn wtmh"; // Your App Password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        otpInput = findViewById(R.id.otp_input);
        sendOtpButton = findViewById(R.id.send_otp_button);

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpValue = otpInput.getText().toString();

                if (otpValue.equals("1234")) { // Example OTP
                    sendEmail(); // Send email upon successful OTP
                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginPage.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Function to send the email
    private void sendEmail() {
        // Using AsyncTask to send the email in the background
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender(appEmail, appPassword);
                    sender.sendMail("abhisheksaini642794@gmail.com", "Hello Abhishek", "hello abhishek");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
