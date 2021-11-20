package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    TextView signuplink;
    EditText varmail, varpass;
    Button varlogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        varmail = findViewById(R.id.enter_mail);
        varpass = findViewById(R.id.enter_pass);
        varlogin = findViewById(R.id.loginbtn);
        fAuth = FirebaseAuth.getInstance();

        //Login

        varlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user already logged in
                if(fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    finish();
                }

                String email = varmail.getText().toString().trim();
                String password = varpass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    varmail.setError("Email required");
                    Toast.makeText(LoginPage.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    varpass.setError("Password reqiured");
                    Toast.makeText(LoginPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginPage.this, "Logged in Sucessfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        } else{
                            Toast.makeText(LoginPage.this, "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        //Colors part of text
        SpannableString ss = new SpannableString("New User? Create Account");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 10, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView = (TextView) findViewById(R.id.sign_up_pg_link);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);

        //SIGN UP PAGE LINK
//        signuplink = findViewById(R.id.sign_up_pg_link);
//        signuplink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), RegisterPage.class));
//            }
//        });
    }
}