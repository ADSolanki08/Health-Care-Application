package com.example.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity {
    EditText mName,mEmail,mPwd,mPhone,sEmail,sPwd,fPass;
    Animation frombottom,log;
    ImageButton pro,set,assess,call;
    LinearLayout bg_view,menus,login,title,signin;
    Button lBtn,sBtn,NEW,OLD;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FOR ANIMATION
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        log=AnimationUtils.loadAnimation(this,R.anim.loganim);
        bg_view=(LinearLayout)findViewById(R.id.backgroundView);

        //LOGIN BUTTON
        lBtn=(Button)findViewById(R.id.login_btn);

        //SIGN BUTTON
        sBtn=(Button)findViewById(R.id.SignIn_btn);

        //FOR NEW USER BACK TO LOGIN PAGE
        NEW=(Button)findViewById(R.id.NotRegistered);

        //FOR OLD USER GO TO SIGN UP PAGE
        OLD=(Button)findViewById(R.id.Registered);

        //HOMEPAGE ICONS
        pro=(ImageButton)findViewById(R.id.profile_imgbtn);
        set=(ImageButton)findViewById(R.id.set_imgbtn);
        assess=(ImageButton)findViewById(R.id.asses_imgbtn);
        call=(ImageButton)findViewById(R.id.call_imgbtn);

        //LINEAR LAYOUTS
        //WELCOME TEXT
        title=(LinearLayout)findViewById(R.id.title);
        //ICON MENUS
        menus=(LinearLayout) findViewById(R.id.menus);
        //NEW USER LOGIN
        login=(LinearLayout) findViewById(R.id.login);
        //OLD USER SIGN UP
        signin=(LinearLayout)findViewById(R.id.SignIn);

        //ON NEW USER LOGIN LINEAR LAYOUT
        mName=(EditText)findViewById(R.id.editText_name);
        mEmail=(EditText)findViewById(R.id.editText_Email);
        mPwd=(EditText)findViewById(R.id.editText_Password);
        mPhone=(EditText)findViewById(R.id.editText_Phone);

        //ON SIGN UP LINEAR LAYOUT
        sEmail=(EditText)findViewById(R.id.signInEmail);
        sPwd=(EditText)findViewById(R.id.signInPassword);

        //progressBar
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        //FORGET PASSWORD
        fPass=(EditText)findViewById(R.id.ForgetPass);

        //CREATE INSTANCE OF FIREBASE
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        //IF USER ALREADY SIGNED IN
        if(fAuth.getCurrentUser() != null) {
            title.animate().translationX(-1200);
            login.animate().translationY(-1500);
            signin.animate().translationY(-1500);
            bg_view.animate().translationY(-3500);
          //  menus.startAnimation(frombottom);
        }
        else
        {
            //ANIMATION BETWEEN WELCOME TEXT LAYOUT  AND LOGIN LAYOUT
            title.animate().translationX(-1200).setDuration(800).setStartDelay(2000);
            login.animate().translationX(0).setDuration(800).setStartDelay(2000);

        }

        //FOR NEW USER
            //LOGIN BUTTON ON CLICK EVENT
        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String pass = mPwd.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    mName.setError("Name is required");
                    mName.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(pass)) {
                    mPwd.setError("Password is required");
                    mPwd.requestFocus();
                    return;
                } else if (pass.length() <= 6) {
                    mPwd.setError("Pasword must be >= 6 characters");
                    mPwd.requestFocus();
                    return;

                }else if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number is required");
                    mPhone.requestFocus();
                    return;
                } else if (phone.length() != 10) {
                    mPhone.setError("Enter valid mobile number");
                    mPhone.requestFocus();
                    return;}
                else {


                    progressBar.setVisibility(View.VISIBLE);
                    //USER REGISTRATION TO THE FIREBASE
                    fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "logged successfully", Toast.LENGTH_SHORT).show();
                                userId=fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fStore.collection("users").document(userId);
                                Map<String,Object> user= new HashMap<>();
                                user.put("Name",name);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG","onSuccess user Profile is create for " + userId);
                                    }
                                });

                                //VIEW ANIMATION
                                login.animate().translationY(-1500).setDuration(800).setStartDelay(1500);
                                signin.animate().translationY(-1500).setDuration(800).setStartDelay(1500);
                                bg_view.animate().translationY(-3500).setDuration(800).setStartDelay(1500);
                                menus.startAnimation(frombottom);


                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }

                    });
                }
            }
        });

        //FOR OLD USER
            //SIGN IN BUTTON ON CLICK EVENT
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sEmail.getText().toString().trim();
                String pass = sPwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(pass)) {
                    mPwd.setError("Password is required");
                    mPwd.requestFocus();
                    return;
                } else if (pass.length() <= 6) {
                    mPwd.setError("Pasword must be >= 6 characters");
                    mPwd.requestFocus();
                    return;
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    //USER SING IN TO THE FIREBASE
                    fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "logged successfully", Toast.LENGTH_SHORT).show();
                                //VIEW ANIMATION
                                //VIEW ANIMATION
                                login.animate().translationY(-1500).setDuration(800).setStartDelay(1500);
                                signin.animate().translationY(-1500).setDuration(800).setStartDelay(1500);
                                bg_view.animate().translationY(-3500).setDuration(800).setStartDelay(1500);
                                menus.startAnimation(frombottom);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        //ALREADY REGISTERED USER
            //ON CLICK EVENT
        OLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.animate().translationX(-1500).setDuration(800).setStartDelay(1500);
                signin.animate().translationX(0).setDuration(800).setStartDelay(2000);

            }
        });

        //CREATE NEW ACCOUNT
            //ON CLICK EVENT
        NEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin.animate().translationX(1500).setDuration(800).setStartDelay(1500);
                login.animate().translationX(0).setDuration(800).setStartDelay(2000);


            }
        });

        //TO RESET PASSWORD
            //FORGET PASSWORD ON CLICK EVENT
        fPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final EditText resetMail= new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //EXTRACT THE EMAIL AND SEND RESET LINK
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset Link sent To your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error:Reset Link Is Not Sent"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //CLOSE THE DIALOG
                    }
                });
                passwordResetDialog.create().show();


            }
        });

        //PROFILE ICON ON CLICK EVENT
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        //SELF ASSESSMENT ICON ON CLICK EVENT
        assess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AssessmentActivity.class);
                startActivity(intent);
            }
        });


    }
}