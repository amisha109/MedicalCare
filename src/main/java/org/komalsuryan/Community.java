package org.komalsuryan;

import java.util.concurrent.atomic.AtomicInteger;

public class Community {
    private final int id;
    private String name;
    private String city;
    private String state;
    private String zipCode;
    private static final AtomicInteger count = new AtomicInteger(new Database().getMaxCommunityId());

    public Community(String name, String city, String state, String zipCode) {
        this.id = count.incrementAndGet();
        if (isNameValid(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("The name is not valid");
        }
        if (isCityValid(city)) {
            this.city = city;
        } else {
            throw new IllegalArgumentException("The city is not valid");
        }
        this.state = state;
        if (isZipCodeValid(zipCode)) {
            this.zipCode = zipCode;
        } else {
            throw new IllegalArgumentException("Zip code is not valid");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isNameValid(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("The name is not valid");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (isCityValid(city)) {
            this.city = city;
        } else {
            throw new IllegalArgumentException("The city is not valid");
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        if (isZipCodeValid(zipCode)) {
            this.zipCode = zipCode;
        } else {
            throw new IllegalArgumentException("Invalid zip code");
        }
    }

    private boolean isNameValid(String name) {
        // check if name is not null and not empty
        if (name == null || name.isEmpty()) {
            return false;
        }
        // check if name contains only alphabets, numbers, spaces and hyphens
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != ' ' && c != '-') {
                return false;
            }
        }
        return true;
    }

    private boolean isCityValid(String city) {
        // check if city is not null and not empty
        if (city == null || city.isEmpty()) {
            return false;
        }
        // return true if city contains only alphabets and spaces using regex
        return city.matches("[a-zA-Z ]+");
    }

    private boolean isZipCodeValid(String zipCode) {
        // match with regex pattern for exactly 5 numbers
        return zipCode.matches("\\d{5}");
    }
}


