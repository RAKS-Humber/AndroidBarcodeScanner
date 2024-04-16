package com.humber.barcodepos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText emailText,passwordText,confirmPasswordText;

    TextView loginNow;

    Button registerBtn;

    FirebaseAuth mAuth;


    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();

        emailText=findViewById(R.id.email);
        passwordText=findViewById(R.id.password);
        registerBtn=findViewById(R.id.registerButton);
        progressBar=findViewById(R.id.progress_bar);
        loginNow=findViewById(R.id.loginNow);
        confirmPasswordText=findViewById(R.id.verify_password);

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                String email=emailText.getText().toString();
                String pass=passwordText.getText().toString();
                String confirmPass=confirmPasswordText.getText().toString();


                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    emailText.setText("");
                    emailText.requestFocus();

                    return;
                }
                if(!email.contains("@")|| !email.contains("."))
                {
                    Toast.makeText(Register.this,"Enter Valid Email",Toast.LENGTH_SHORT).show();
                    emailText.setText("");
                    emailText.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                    passwordText.requestFocus();
                    return;
                }
                if(!pass.equals(confirmPass))
                {

                    Toast.makeText(Register.this,"Password Should Match",Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                    confirmPasswordText.setText("");
                    passwordText.requestFocus();
                    return;
                }



                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //progressBar.setVisibility(View.GONE);

                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(Register.this, "Account Created.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Exception e=task.getException();
                                            if(e!=null)
                                            {
                                                if(e instanceof com.google.firebase.auth.FirebaseAuthUserCollisionException) {
                                                    Toast.makeText(Register.this, "Email is already in use. Please use a different email address.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(Register.this, "Authentication failed." + e.getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                            }
                                            }




                                        }





                            });
                }



        });

    }


}