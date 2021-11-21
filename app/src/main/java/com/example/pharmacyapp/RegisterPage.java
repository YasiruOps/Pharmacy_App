package com.example.pharmacyapp;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText varname, varemail, varcontact, varpass, varconfpass;
    Button varreg_btn;
    TextView loginpglink;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        varname = findViewById(R.id.reg_name);
        varemail =findViewById(R.id.reg_email);
        varcontact = findViewById(R.id.reg_contact);
        varpass = findViewById(R.id.reg_pass);
        varconfpass = findViewById(R.id.reg_conf_pass);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

                //signing happens here

                if (field_chck == true) {

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterPage.this, "User Registerd", Toast.LENGTH_SHORT).show();

                                //Adding user data to firebase
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Name",name);
                                user.put("Contact",contact);
                                user.put("Email", email);
                                user.put("Password",password);
                                documentReference.set(user).addOnSuccessListener((OnSuccessListener) (aVoid) ->{
                                    Log.d(TAG, "onSuccess: user Profile was created for "+ userID);
                                }).addOnFailureListener (new OnFailureListener(){
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure "+ e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            } else{
                                Toast.makeText(RegisterPage.this, "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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