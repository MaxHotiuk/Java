package com.classes;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Human[] people = new Human[10];

        people[0] = new Student("Ivan", 20, "male", "FPMI", 90);
        people[1] = new Student("Anna", 22, "female", "FPMI", 85);
        people[2] = new Student("John", 21, "male", "CS", 88);
        people[3] = new Student("Maria", 23, "female", "Math", 92);
        people[4] = new Lecturer("Dr. Smith", 45, "male", "Physics", 5);
        people[5] = new Lecturer("Dr. Johnson", 50, "female", "Chemistry", 5);
        people[6] = new Lecturer("Dr. Brown", 40, "male", "Biology", 20);
        people[7] = new Lecturer("Dr. Davis", 55, "female", "Engineering", 40);
        people[8] = new Student("Daniel", 20, "male", "Economics", 86);
        people[9] = new Student("Olivia", 22, "female", "Literature", 90);

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