package com.crazy4web.chatapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText phone, code;
    private Button btn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
    String mverificationID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);


        // Checking if user is actually logged in.
        userIsLoggedIn();

        phone = findViewById(R.id.phone);
        code = findViewById(R.id.code);
        btn = findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mverificationID!=null){
                    VerifyPhoneNumberWithCode();
                }
                else {

                    StartVerification();
                }


            }
        });


        callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                SignInWithPhoneCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                btn.setText("Verification Failed");

            }

            @Override
            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationID, forceResendingToken);

                mverificationID = verificationID;
                btn.setText("Verify Code");

            }
        };
    }

    private void VerifyPhoneNumberWithCode(){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mverificationID,code.getText().toString());
        SignInWithPhoneCredential(credential);

    }

    private void SignInWithPhoneCredential(PhoneAuthCredential phoneAuthCredential) {

        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){

               // Getting the current user once task is successful
              final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

               if(user != null){

                   // Referencing a child User in the database. and pointing to the UserID of the user

                   final DatabaseReference muserDB = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

                   // getting value of the user by using a listener

                   muserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           if(!dataSnapshot.exists()){

                               Map<String, Object> userMap = new HashMap<>();
                               userMap.put("phone",user.getPhoneNumber());

                               userMap.put("name",user.getPhoneNumber());

                               muserDB.updateChildren(userMap);
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                           


                       }
                   });


               }





               userIsLoggedIn();
           }

            }
        });


    }

    public void userIsLoggedIn() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user!=null){

            Log.d("user",""+user.getUid());



            Intent i = new Intent(getApplicationContext(),Main2Activity.class);

            startActivity(i);


        }
    }

    public void StartVerification() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone.getText().toString(),
                100,
                TimeUnit.SECONDS,
                this,
                callback
        );

    }
}
