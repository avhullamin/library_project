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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import javax.mail.MessagingException;

public class LoginPage extends AppCompatActivity {

    private EditText otpInput, usnInput;
    private Button sendOtpButton, verifyOtpButton;
    private final String appEmail = "unisap.library.app@gmail.com";
    private final String appPassword = "lndw uocy nshz vqfn";

    private final int randomNumber = 100000 + new Random().nextInt(900000);
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        usnInput = findViewById(R.id.username_input);
        otpInput = findViewById(R.id.otp_input);
        sendOtpButton = findViewById(R.id.send_otp_button);
        verifyOtpButton = findViewById(R.id.verify_otp_button);

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = usnInput.getText().toString();
                if (!usn.isEmpty()) {
                    fetchEmailAndSendOtp(usn);
                } else {
                    Toast.makeText(LoginPage.this, "Please enter USN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpValue = otpInput.getText().toString();

                if (!otpValue.isEmpty()) {
                    if (otpValue.equals(String.valueOf(randomNumber))) {
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

    private void fetchEmailAndSendOtp(String usn) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String email = readEmailFromCSV(usn);
                if (email != null) {
                    userEmail = email;
                    sendEmail(userEmail);
                    runOnUiThread(() -> {
                        Toast.makeText(LoginPage.this, "OTP successfully sent to " + userEmail, Toast.LENGTH_LONG).show();
                        sendOtpButton.setVisibility(View.GONE);
                        verifyOtpButton.setVisibility(View.VISIBLE);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginPage.this, "USN not found in CSV", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private String readEmailFromCSV(String usn) {
        String line;
        String[] columns;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.student_info);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                columns = line.split(","); // Split by comma
                if (columns.length > 2 && columns[0].equalsIgnoreCase(usn)) { // Ensure the line has enough columns
                    return columns[2]; // Return the email
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // USN not found
    }

    private void sendEmail(String recipientEmail) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender(appEmail, appPassword);
                    sender.sendMail(recipientEmail, "Welcome To The UNISAP Library App", "Your One Time Password is: " + randomNumber);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
