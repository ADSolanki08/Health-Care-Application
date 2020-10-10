package com.example.health;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class ProfileActivity extends AppCompatActivity {
    Button logout;
    TextView fname,email,phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //TO CHANGE ACTION BAR TITLE
        getSupportActionBar().setTitle("PROFILE");
        //TO REACH PARENT ACTIVITY
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        fname=(TextView)findViewById(R.id.profileName);
        email=(TextView)findViewById(R.id.profileEmail);
        phone=(TextView)findViewById(R.id.profilePhone);

        //
        logout=(Button)findViewById(R.id.logOut);

        //
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        //
        userId=fAuth.getCurrentUser().getUid();

        //
        final DocumentReference documentReference= fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("Phone"));
                fname.setText(documentSnapshot.getString("Name"));
               email.setText(documentSnapshot.getString("Email"));
            }
        });

        //
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}