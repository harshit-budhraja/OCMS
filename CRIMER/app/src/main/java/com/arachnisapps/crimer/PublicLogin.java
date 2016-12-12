package com.arachnisapps.crimer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PublicLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private String email,password,user_email,user_dispname,imgurl;
    Button gbtn,lgnbtn,sgupbutton;
    private static final int RC_SIGN_IN = 9001;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_login);
        progressDialog = new ProgressDialog(this);

        if (!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        lgnbtn = (Button) findViewById(R.id.loginButton);
        gbtn = (Button) findViewById(R.id.gbutton);
        sgupbutton = (Button) findViewById(R.id.sgupbutton);
        sharedPreferences = this.getSharedPreferences("mypref",0);
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and
        // basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        sgupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),(CharSequence) "Please check your details",Toast.LENGTH_SHORT).show();

                }
                else{
                    progressDialog.setMessage("Registering Please Wait...");
                    progressDialog.show();
                    registerUser(email,password);
                }
            }
        });

        lgnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),(CharSequence) "Please check your details",Toast.LENGTH_SHORT).show();

                }
                else{
                    progressDialog.setMessage("Signing in...");
                    progressDialog.show();
                    signinUser(email,password);
                }
            }
        });

        gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        if(sharedPreferences.getBoolean("loginstatus",false) && mGoogleApiClient.isConnected()){
            Intent intent = new Intent(PublicLogin.this, MainActivity.class);
            intent.putExtra("email",user_email);
            intent.putExtra("password","");
            intent.putExtra("name",user_dispname);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                try {
                    editor = sharedPreferences.edit();
                    user_email = result.getSignInAccount().getEmail();
                    user_dispname = result.getSignInAccount().getDisplayName();
                    imgurl = result.getSignInAccount().getPhotoUrl().toString();
                    Log.v("LOGIN DETAILS",user_dispname + " " + user_email);
                }
                catch(NullPointerException np){
                    np.printStackTrace();
                }
                if(user_email == null){
                    user_email = "user@gmail.com";
                }
                if(imgurl == null){
                    imgurl = "http://gurucul.com/wp-content/uploads/2015/01/default-user-icon-profile.png";
                }
                if(user_dispname == null){
                    user_dispname = "Anonymous User";
                }
                editor.putString("emailid",user_email);
                editor.putString("name",user_dispname);
                editor.putString("imageurl",imgurl);
                editor.putBoolean("loginstatus",true);
                editor.apply();
                Intent intent = new Intent(PublicLogin.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void registerUser(String user,String pass){

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //checking if success
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),(CharSequence)"SUCCESS",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PublicLogin.this,MainActivity.class);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            Log.v("LOGIN DETAILS",email + " " + password);
                            editor = sharedPreferences.edit();
                            editor.putString("emailid",email);
                            editor.putBoolean("loginstatus",true);
                            editor.apply();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),(CharSequence)"FAILURE",Toast.LENGTH_SHORT).show();
                            //display some message here
                            AlertDialog.Builder builder = new AlertDialog.Builder(PublicLogin.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle("FAILURE")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });

    }

    private void signinUser(String user,String pass){

        firebaseAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //checking if success
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),(CharSequence)"SUCCESS",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PublicLogin.this,MainActivity.class);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            Log.v("LOGIN DETAILS",email + " " + password);
                            editor = sharedPreferences.edit();
                            editor.putString("emailid",email);
                            editor.putBoolean("loginstatus",true);
                            editor.apply();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),(CharSequence)"FAILURE",Toast.LENGTH_SHORT).show();
                            //display some message here
                            AlertDialog.Builder builder = new AlertDialog.Builder(PublicLogin.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle("FAILURE")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });

    }
}