package com.manali.travelbees;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/*
        **************************************************************************
        *   This class saves the group trip information in the Firebase Database *                            *
        *    which is used to populate the Recycler View                         *
        *                                                                        *
        *   Last Edited On : 11/06/17                                            *
        *   Last Edited By : Ayush Manish Agrawal                                *
        *   What Changed   :                                                     *
        *        			            		                                 *
        **************************************************************************
*/

public class ActivityCreateNewTrip extends AppCompatActivity {

    private TextInputLayout newTripCity;
    private TextInputLayout newTripMembers;
    private Button newTripCreate;


    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        newTripCity = (TextInputLayout) findViewById(R.id.trip_new_location);
        newTripMembers = (TextInputLayout) findViewById(R.id.trip_new_members);
        newTripCreate = (Button) findViewById(R.id.trip_new_create_button);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        //Catching the input from the Text Input Layout on button click
        newTripCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trip_city = newTripCity.getEditText().getText().toString();
                String trimmedTrip_city = trip_city.replace(" ", "_");
                //Todo implement trip members
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String groupId = user.getUid() + trimmedTrip_city;
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                //adding fields to the groups table
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupId);

                HashMap<String, String> groupsMap = new HashMap<>();
                groupsMap.put("groupName", trip_city);
                groupsMap.put("groupOwner", user.getEmail());
                groupsMap.put("groupDateCreated", date);
                //Todo Add Members

                mDatabase.setValue(groupsMap);

                //adding fields to the members column in the group table

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupId).child("groupMembers");

                HashMap<String, String> membersMap = new HashMap<>();
                membersMap.put("uid", user.getUid());

                mDatabase.setValue(membersMap);

                //adding userGroups column in the user table
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("userGroups");

                HashMap<String, String> usersGroupsMap = new HashMap<>();
                usersGroupsMap.put("groupId", groupId);

                mDatabase.setValue(usersGroupsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //going back to main activity on successful push of data
                        if (task.isSuccessful()) {

                            Intent mainActivityIntent = new Intent(ActivityCreateNewTrip.this, ActivityMain.class);
                            startActivity(mainActivityIntent);
                            finish();
                            Toast.makeText(ActivityCreateNewTrip.this, "The trip has been created successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
