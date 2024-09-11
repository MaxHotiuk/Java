package com.classes;

public class Lecturer extends Human{
    private final String position;
    private final int experience;

    public Lecturer(String namein, int agein, String genderin, String positionin, int experiencein)
    {
        super(namein, agein, genderin);
        this.position = positionin;
        this.experience = experiencein;
    }

    public String getPosition()
    {
        return position;
    }

    public int getExperience()
    {
        return experience;
    }
}
