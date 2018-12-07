package com.example.changjameslee.worldmovie;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {
    TextInputEditText username,password,fullName,confirmPassword;
    Button signUpBttn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ref();
        mAuth = FirebaseAuth.getInstance();

        signUpBttn.setOnClickListener(registerNow);

    }
    private View.OnClickListener registerNow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean checkifFieldsEmpty= false;

            if(username.getText().toString().isEmpty())
            {
                username.setError("Username is Required");
                checkifFieldsEmpty=true;
            }

            if(password.getText().toString().isEmpty())
            {
                password.setError("Password is Required");
                checkifFieldsEmpty=true;
            }

            if(fullName.getText().toString().isEmpty())
            {
                fullName.setError("Name is Required");
                checkifFieldsEmpty=true;
            }

            if(confirmPassword.getText().toString().isEmpty())
            {
                confirmPassword.setError("Please confirm your password!");
                checkifFieldsEmpty=true;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches())
            {
                username.setError("Please enter valid email");
                checkifFieldsEmpty=true;
            }

            if(password.length()<6)
            {
                Toast.makeText(getApplication(), "PASSWORD MUST BE AT LEAST 6 CHARACTERS!", Toast.LENGTH_SHORT).show();
                checkifFieldsEmpty=true;
            }

            if(!password.getText().toString().equals(confirmPassword.getText().toString()))
            {
                Toast.makeText(getApplication(), "PASSWORDS DO NOT MATCH!", Toast.LENGTH_SHORT).show();
                checkifFieldsEmpty=true;
            }
            if(checkifFieldsEmpty==false) {

                mAuth.createUserWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(getApplication(), "Successfuly Registered!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterUser.this, HomeActivity.class);
                            startActivity(intent);

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterUser.this, "Invalid Credentials, Please Enter a different email",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                    }
                });

            }


        }
    };
    public void ref()
    {
        username = findViewById(R.id.RegisterActivityEditText_Username);
        fullName = findViewById(R.id.RegisterActivityEditText_SignUpFullName);
        password = findViewById(R.id.RegisterActivityEditText_Password);
        confirmPassword = findViewById(R.id.RegisterActivityEditText_Password);
        signUpBttn = findViewById(R.id.RegisterActivityButtonCreateAccout);
    }
}
