package org.komalsuryan;

import java.time.LocalDate;

public class Person {
    private String ssNumber;
    private String name;
    private int communityId;
    private LocalDate dateOfBirth;
    private String sex;
    private float height;
    private float weight;

    public Person(String ssNumber, String name, int communityId, LocalDate dateOfBirth, String sex, float height, float weight) {
        this.ssNumber = ssNumber;
        this.name = name;
        this.communityId = communityId;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }

    public Person(Person person) {
        this.ssNumber = person.getSsNumber();
        this.name = person.getName();
        this.communityId = person.getCommunityId();
        this.dateOfBirth = person.getDateOfBirth();
        this.sex = person.getSex();
        this.height = person.getHeight();
        this.weight = person.getWeight();
    }

    public String getSsNumber() {
        return ssNumber;
    }

    public void setSsNumber(String ssNumber) {
        this.ssNumber = ssNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    private boolean checkCommunityId(int communityId) {
        return new Database().getCommunity(communityId) != null;
    }

    private boolean checkSsNumber(String ssNumber) {
        // match for SSN format: 9 numbers only
        return ssNumber.matches("\\d{9}");
    }

    private boolean checkName(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
