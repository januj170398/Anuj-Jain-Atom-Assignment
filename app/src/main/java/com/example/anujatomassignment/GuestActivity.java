package com.example.anujatomassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GuestActivity extends AppCompatActivity {

    private ImageView backButton;
    private EditText nameEditText;
    private Button guestButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String guestName;


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        backButton = findViewById(R.id.back_btn);
        nameEditText = findViewById(R.id.guest_name_edittext);
        guestButton = findViewById(R.id.guest_continue_btn);
        progressBar = findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestName = nameEditText.getText().toString();
                if(guestName.trim().equals("")){
                    Toast.makeText(GuestActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    AnonymousSignIn();
                }
            }
        });

    }


    private void AnonymousSignIn() {
        if(currentUser == null){

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(GuestActivity.this, "Anonymous Sign In Success", Toast.LENGTH_SHORT).show();
                        Intent guestIntent = new Intent(getApplicationContext(),HomePageActivity.class);
                        guestIntent.putExtra("guest_name",nameEditText.getText().toString());
                        startActivity(guestIntent);
                        finish();

                    }else{
                        Toast.makeText(GuestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}