package com.example.changjameslee.worldmovie;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    //used in Google Authentication
    Button login;
    TextView register;
    SignInButton signInWithGoogle;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    private static String TAG="ERROR";
    private final static int RC_SIGN_IN=2;

    TextInputEditText EdtTxttlogin_username,EdtTxtlogin_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        refs();
        mAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInWithGoogle = findViewById(R.id.forGoogle);
        signInWithGoogle.setOnClickListener(new View.OnClickListener() {@Override
           public void onClick(View v) {
                signIn();
           }
      });
        register.setOnClickListener(registerTxt);
        login.setOnClickListener(loginBttn);

   }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void refs()
    {
        login = findViewById(R.id.LoginButton);
        register = findViewById(R.id.textView_mainSignUp);
        EdtTxttlogin_username = findViewById(R.id.userNameLogIn);
        EdtTxtlogin_password = findViewById(R.id.passwordLogIn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("ERROR:", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplication(), "FAILED TO LOGIN", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }



    //Login Button
   private View.OnClickListener loginBttn = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           boolean isEmpty=false;

           if(EdtTxttlogin_username.getText().toString().isEmpty())
           {
               EdtTxttlogin_username.setError("Please Enter Username");
               isEmpty=true;

           }

           if(EdtTxtlogin_password.getText().toString().isEmpty())
           {
               EdtTxtlogin_password.setError("Please Enter Password");
               isEmpty=true;
           }

           if(isEmpty==false) {
               mAuth.signInWithEmailAndPassword(EdtTxttlogin_username.getText().toString(), EdtTxtlogin_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                           startActivity(intent);
                         Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                       } else {

                            View view = findViewById(R.id.cons);
                           Snackbar.make(view,"Wrong Credentials!\n Please Check", Snackbar.LENGTH_LONG ).show();
                       }
                   }
               });
           }

        }
   };

    //register text
   private View.OnClickListener registerTxt = new View.OnClickListener()
    {@Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,register.class);
            startActivity(intent);
        }
    };

}

