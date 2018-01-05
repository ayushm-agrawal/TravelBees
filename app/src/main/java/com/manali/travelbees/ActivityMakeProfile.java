package com.manali.travelbees;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class ActivityMakeProfile extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    Animation downtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_profile);

        final EditText fullName = findViewById(R.id.fullNameMakeProfile);
        final EditText userName = findViewById(R.id.userNameMakeProfile);
        final Button btnUploadPhoto = findViewById(R.id.btn_upload_photo);
        final Button btnNextActivity = findViewById(R.id.btn_nextMakeProfile);
        final TextView duplicateUsername = findViewById(R.id.txt_duplicateUsername);
        btnUploadPhoto.setVisibility(View.INVISIBLE);
        btnNextActivity.setVisibility(View.INVISIBLE);

        fullName.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        //the button shows after the username is entered
        userName.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //do nothing
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //do nothing
               duplicateUsername.setVisibility(View.INVISIBLE);
           }

           @Override
           public void afterTextChanged(Editable editable) {
           }
        });

        userName.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    if(btnUploadPhoto.getVisibility() == View.INVISIBLE) {
                        downtoup = AnimationUtils.loadAnimation(ActivityMakeProfile.this, R.anim.downtoup);
                        btnUploadPhoto.setVisibility(View.VISIBLE);
                        btnUploadPhoto.setAnimation(downtoup);
                        btnNextActivity.setVisibility(View.VISIBLE);
                        btnNextActivity.setAnimation(downtoup);
                    }
                }
                return false;
            }
        });


        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final String fullNameText = fullName.getText().toString();
                final String userNameText = userName.getText().toString();
                //if username is empty then throw error
                if(userNameText.isEmpty()){
                    duplicateUsername.setText("You cannot leave the username empty");
                    duplicateUsername.setVisibility(View.VISIBLE);
                }
                else{
                    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert currentUser != null;
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());

                    //checking if the username exists START
                    Query usernameQuery = mDatabaseReference.orderByValue().equalTo(userNameText);
                    usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                userName.setText("");
                                userName.hasFocus();
                                duplicateUsername.setText("The username is already taken");
                                duplicateUsername.setVisibility(View.VISIBLE);
                            }
                            else{
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("fullName", fullNameText);
                                userMap.put("userName", userNameText);

                                mDatabaseReference.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ActivityMakeProfile.this, "User details were added successfully", Toast.LENGTH_SHORT).show();
                                            Intent phoneVerificationIntent = new Intent(ActivityMakeProfile.this, ActivityPhoneNumberVerification.class);
                                            ActivityOptions options = ActivityOptions.makeCustomAnimation(ActivityMakeProfile.this, R.anim.fade_in, R.anim.fade_out);
                                            ActivityMakeProfile.this.startActivity(phoneVerificationIntent, options.toBundle());
                                            finish();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //checking if the username exists END
                }
            }
        });
    }
}
