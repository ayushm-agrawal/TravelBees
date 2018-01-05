package com.manali.travelbees;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGroups extends Fragment {

    private RecyclerView mGroupsList;

    private DatabaseReference mGroupsDatabase;



    public FragmentGroups() {
        // Required empty public constructor otherwise app will crash
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_groups, container, false);

        mGroupsDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");

        mGroupsList = (RecyclerView) layout.findViewById(R.id.groups_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        mGroupsList.setHasFixedSize(true);
        mGroupsList.setLayoutManager(linearLayoutManager);
        mGroupsList.addItemDecoration(itemDecoration);
        return layout;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<GroupList, GroupsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GroupList, GroupsViewHolder>(

                GroupList.class,
                R.layout.group_single_item,
                GroupsViewHolder.class,
                mGroupsDatabase
        ) {

            //Popoulating the Recyler View by fetching the data from Firebase
            @Override
            protected void populateViewHolder(final GroupsViewHolder groupsViewHolder, GroupList groups, int position) {

                    groupsViewHolder.setName(groups.getGroupName());
                    groupsViewHolder.setOwner(groups.getGroupOwner());

                    final String tripId = getRef(position).getKey();

                //going to the Trip Activity on click of any recycler view item
                    groupsViewHolder.groupsView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            groupsViewHolder.groupsView.setBackgroundColor(Color.LTGRAY);
                            Intent tripActivityIntent = new Intent(getContext(), ActivityTrip.class);
                            tripActivityIntent.putExtra("groupId", tripId);
                            startActivity(tripActivityIntent);

                        }
                    });

            }
        };

        mGroupsList.setAdapter(firebaseRecyclerAdapter);


    }


    //Fetching the Data in the View holder
    public static class GroupsViewHolder extends RecyclerView.ViewHolder{

        View groupsView;

        public GroupsViewHolder(View itemView){
            super(itemView);

            groupsView = itemView;
        }

        public void setName(String groupName) {

            TextView groupNameView = (TextView) groupsView.findViewById(R.id.group_name);
            groupNameView.setText(groupName);
        }

        public void setOwner(String groupOwner) {
            TextView groupOwnerView = (TextView) groupsView.findViewById(R.id.group_owner);
            groupOwnerView.setText(groupOwner);
        }
    }
}
