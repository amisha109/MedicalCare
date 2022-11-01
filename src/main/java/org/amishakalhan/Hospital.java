package org.amishakalhan;

import java.util.concurrent.atomic.AtomicInteger;

public class Hospital {
    private final int id;
    private String name;
    private String location;
    private int communityId;
    private boolean isClinic;
    private static final AtomicInteger count = new AtomicInteger(new Database().getMaxHospitalId());


    public Hospital(String name, String location, int communityId, boolean isClinic) {
        this.id = count.incrementAndGet();
        if (checkName(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name is not valid. Name can only contain letters and spaces");
        }
        if (checkLocation(location)) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("Location is not valid. Location can only contain letters, numbers and spaces");
        }
        if (checkCommunityId(communityId)) {
            this.communityId = communityId;
        } else {
            throw new IllegalArgumentException("Community ID is not valid or does not exist");
        }
        this.isClinic = isClinic;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        if (checkCommunityId(communityId)) {
            this.communityId = communityId;
        } else {
            throw new IllegalArgumentException("Community ID is not valid or does not exist");
        }
    }

    public boolean isClinic() {
        return isClinic;
    }

    public void setClinicStatus(boolean isClinic) {
        this.isClinic = isClinic;
    }

    private boolean checkName(String name) {
        // regex pattern to check if string is not empty, and contains only alphabets and spaces
        return name.matches("^[a-zA-Z\\s]*$");
    }

    private boolean checkLocation(String location) {
        // regex pattern to check if the string is not empty, and contains alphabets, numbers and spaces
        return location.matches("^[a-zA-Z0-9\\s]*$");
    }

    private boolean checkCommunityId(int communityId) {
        return new Database().getCommunity(communityId) != null;
    }
}
