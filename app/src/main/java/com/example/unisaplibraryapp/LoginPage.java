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
import java.util.Random;
import javax.mail.MessagingException;

public class LoginPage extends AppCompatActivity {

    private EditText otpInput;
    private Button sendOtpButton;
    private Button verifyOtpButton; // New button for verifying OTP
    private final String appEmail = "abhisheksaini5656000@gmail.com"; // Your Gmail address
    private final String appPassword = "suib vadt aehn wtmh"; // Your App Password

    private final int randomNumber = 100000 + new Random().nextInt(900000);

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
        verifyOtpButton = findViewById(R.id.verify_otp_button); // Initialize the new button

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send the OTP via email
                sendEmail();
                Toast.makeText(LoginPage.this, "OTP sent to your email!", Toast.LENGTH_SHORT).show();
                verifyOtpButton.setVisibility(View.VISIBLE); // Show Verify OTP button
            }
        });

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpValue = otpInput.getText().toString();

                // Check if the OTP input field is not empty
                if (!otpValue.isEmpty()) {
                    if (otpValue.equals(String.valueOf(randomNumber))) { // Check against the generated random number
                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginPage.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
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
                    sender.sendMail("abhisheksaini642794@gmail.com", "Welcome To The Unisap Library App", "Your One Time Password is: " + randomNumber);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
