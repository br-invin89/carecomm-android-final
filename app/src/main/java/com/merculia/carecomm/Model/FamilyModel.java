package com.merculia.carecomm.Model;

public class FamilyModel {

    private int profilePic;
    private String name;
    private String relationship;

    public FamilyModel(int profilePic, String name, String relationship) {
        this.profilePic = profilePic;
        this.name = name;
        this.relationship = relationship;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getName() {
        return name;
    }

    public String getRelationship() {
        return relationship;
    }
}
