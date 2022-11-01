package org.amishakalhan;

public enum UserTypes {
    DOCTOR("Doctor"),
    PATIENT("Patient"),
    SYSTEM_ADMIN("System Admin"),
    COMMUNITY_ADMIN("Community Admin");

    private final String userType;

    UserTypes(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

}
