package com.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Human[] people = new Human[38];
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/maxhotiuk/Desktop/5sem/Java/classestask/src/main/resources/people.txt"))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null && index < people.length) {
                String[] parts = line.split(",");
                String type = parts[0];
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String gender = parts[3];
                String facultyOrDepartment = parts[4];
                int gradeOrExperience = Integer.parseInt(parts[5]);

                if (type.equals("Student")) {
                    people[index] = new Student(name, age, gender, facultyOrDepartment, gradeOrExperience);
                } else if (type.equals("Lecturer")) {
                    people[index] = new Lecturer(name, age, gender, facultyOrDepartment, gradeOrExperience);
                }
                index++;
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        for (int i = 0; i < people.length; i++) {
            for (int j = i + 1; j < people.length; j++) {
                if (people[i].getName().compareTo(people[j].getName()) > 0) {
                    Human temp = people[i];
                    people[i] = people[j];
                    people[j] = temp;
                }
            }
        }

        List<Lecturer> lecturers = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        for (Human person : people) {
            System.out.println(person.getName() + " " + person.getAge() + " " + person.getGender());
            switch (person) {
                case Lecturer lecturer -> lecturers.add(lecturer);
                case Student student -> students.add(student);
                default -> System.out.println("Unknown type of person");
            }
        }

        lecturers.sort((l1, l2) -> l1.getExperience() - l2.getExperience());
        int minExperience = lecturers.get(0).getExperience();
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getExperience() == minExperience) {
                System.out.println("Lecturer with minimal experience: " + lecturer.getName());
            }
        }
        
        students.sort((s1, s2) -> s2.getProgress() - s1.getProgress());
        int maxProgress = students.get(0).getProgress();
        for (Student student : students) {
            if (student.getProgress() == maxProgress) {
                System.out.println("Student with maximum progress: " + student.getName());
            }
        }
    }
}