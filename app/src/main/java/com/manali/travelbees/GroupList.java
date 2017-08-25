package com.manali.travelbees;

/**
 * Created by Ayush on 8/9/2017.
 */

public class GroupList {

    public String groupName;
    public String groupOwner;

    public GroupList(String groupName, String groupOwner) {
        this.groupName = groupName;
        this.groupOwner = groupOwner;
    }

    public GroupList() {

    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupOwner() {
        return groupOwner;
    }
}
