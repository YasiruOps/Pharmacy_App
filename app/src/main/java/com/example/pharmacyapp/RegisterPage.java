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

public class RegisterPage extends AppCompatActivity {

    EditText varname, varemail, varcontact, varpass, varconfpass;
    Button varreg_btn;
    TextView loginpglink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        varname = findViewById(R.id.reg_name);
        varemail =findViewById(R.id.reg_email);
        varcontact = findViewById(R.id.reg_contact);
        varpass = findViewById(R.id.reg_pass);
        varconfpass = findViewById(R.id.reg_conf_pass);


        varreg_btn = findViewById(R.id.register_Btn);
        varreg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, email, contact, password, confpass;
                boolean field_chck = true;

                name = varname.getText().toString().trim();
                email = varemail.getText().toString().trim();
                contact = varcontact.getText().toString().trim();
                password = varpass.getText().toString().trim();
                confpass = varconfpass.getText().toString().trim();

                if(TextUtils.isEmpty(name)  || TextUtils.isEmpty(password)) {
                    varname.setError("Please fill in you all details");
                    field_chck = false;
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    varemail.setError("Please enter email");
                    field_chck = false;
                    return;
                }
                if (TextUtils.isEmpty(contact)){
                    varcontact.setError("Please enter contact");
                    field_chck = false;
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    varcontact.setError("Please enter contact");
                    field_chck = false;
                    return;
                }
                if (TextUtils.isEmpty(confpass)){
                    varconfpass.setError("Please enter password");
                    field_chck = false;
                    return;
                }

                if (!password.equals(confpass)){
                    Toast toast = Toast.makeText(RegisterPage.this, "password mismatch", Toast.LENGTH_SHORT);
                    TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
                    view.setTextColor(Color.RED);
                    toast.show();
                    field_chck = false;
                    return;
                }

                if (field_chck == true) {

                }
            }
        });

        //Colors part of text
        SpannableString ss = new SpannableString("Already have an account? Login");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(RegisterPage.this, LoginPage.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 26, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView = (TextView) findViewById(R.id.login_page_link);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);

        loginpglink = findViewById(R.id.login_page_link);
        loginpglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
            }
        });
    }
}