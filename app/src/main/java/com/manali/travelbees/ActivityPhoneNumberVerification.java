package com.manali.travelbees;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityPhoneNumberVerification extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String mVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("fullName");
        final TextView userDisplayName = findViewById(R.id.userDisplayNamePhoneVerification);
        final EditText phoneNumber = findViewById(R.id.enterPhoneNumber);
        final TextView headerText = findViewById(R.id.phoneVerificationHeader);
        final EditText verificationCode = findViewById(R.id.enterVerificationCode);
        CircleImageView userImage = findViewById(R.id.userImagePhoneVerification);
        final Button btnSendVerificationCode = findViewById(R.id.btn_sendVerificationCode);
        final Button btnVerifyCode = findViewById(R.id.btn_verifyCode);


        String filename = "https://firebasestorage.googleapis.com/v0/b/travelbees-3a1bc.appspot.com/o/default_profile_images%2Fbee2.jpg?alt=media&token=9244169c-7141-40cd-bcca-d96c312d9157";

        Picasso.with(ActivityPhoneNumberVerification.this).load(filename).into(userImage);
        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userFullName = dataSnapshot.getValue(String.class);
                userDisplayName.setText(userFullName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(final PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                user.linkWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("phoneNumber", phoneNumber.getText().toString());

                                    mDatabase.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                               Intent verifyCodeIntent = new Intent(ActivityPhoneNumberVerification.this, ActivityMain.class);
                                               startActivity(verifyCodeIntent);
                                               finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    phoneNumber.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                mVer = verificationId;
                headerText.setText("Enter Verification Code");
                phoneNumber.setVisibility(View.INVISIBLE);
                verificationCode.setVisibility(View.VISIBLE);
                btnSendVerificationCode.setVisibility(View.INVISIBLE);
                btnVerifyCode.setVisibility(View.VISIBLE);

                // Save verification ID and resending token so we can use them later
            }
        };
        // [END phone_auth_callbacks]

        btnSendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        ActivityPhoneNumberVerification.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallback
            }
        });

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String verS = verificationCode.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVer, verS);

                user.linkWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("phoneNumber", phoneNumber.getText().toString());

                                    mDatabase.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent verifyCodeIntent = new Intent(ActivityPhoneNumberVerification.this, ActivityMain.class);
                                                startActivity(verifyCodeIntent);
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }
}
