package com.manali.travelbees;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ayush on 7/4/2017.
 */

public class TravelBees {

    public void onCreate(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
