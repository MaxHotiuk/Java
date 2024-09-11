package com.classes;

public class Student extends Human {
    private final String faculty;
    private final int progress;

    public Student(String namein, int agein, String genderin, String facultyin, int progressin)
    {
        super(namein, agein, genderin);
        this.faculty = facultyin;
        this.progress = progressin;
    }

    public String getFaculty()
    {
        return faculty;
    }

    public int getProgress()
    {
        return progress;
    }

}
