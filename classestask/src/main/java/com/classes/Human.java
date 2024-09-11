package com.classes;

public abstract class Human {
    private final String name;
    private final int age;
    private final String gender;

    protected Human(String namein, int agein, String genderin)
    {
        this.name = namein;
        this.age = agein;
        this.gender = genderin;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getGender()
    {
        return gender;
    }
}
