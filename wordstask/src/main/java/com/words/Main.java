package com.words;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (
                Scanner in = new Scanner(System.in)) {
                    System.out.println("Enter the string: ");
                    String str = in.nextLine();
                    String[] words = str.split(",");
                    StringBuilder sb = new StringBuilder();
                    for (String word : words) {
                        String reversed = new StringBuilder(word).reverse().toString() + ",";
                        sb.append(reversed);
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    System.out.println(sb.toString());
                }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}