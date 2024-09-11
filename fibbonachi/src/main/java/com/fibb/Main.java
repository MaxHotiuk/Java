package com.fibb;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (
                Scanner in = new Scanner(System.in)) {
                    System.out.println("Enter the number of rows: ");
                    String m = in.nextLine();
                    System.out.println("Enter the number of columns: ");
                    String n = in.nextLine();

                    int rows = Integer.parseInt(m);
                    int columns = Integer.parseInt(n);
                    int[][] matrix = new int[rows][columns];

                    System.out.println("Input matrix elements or randomize them? (i/r)");
                    String input = in.nextLine();

                    switch (input) {
                        case "i" -> {
                            System.out.println("Enter matrix elements:");
                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < columns; j++) {
                                    matrix[i][j] = in.nextInt();
                                }
                            }
                        }
                        case "r" -> {
                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < columns; j++) {
                                    matrix[i][j] = new java.util.Random().nextInt(10) + 1;
                                }
                            }
                        }
                        default -> throw new Exception("Invalid input");
                    }
                    
                    System.out.println("Matrix:");
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            System.out.printf("%3d", matrix[i][j]);
                        }
                        System.out.println();
                    }
                    
                    int[] vector = new int[rows];

                    for (int i = 0; i < rows; i++) {
                        vector[i] = Integer.MAX_VALUE;
                        for (int j = 0; j < columns; j++) {
                            if (isFibonacci(matrix[i][j])) {
                                vector[i] = Math.min(vector[i], matrix[i][j]);
                            }
                        }
                        if (vector[i] == Integer.MAX_VALUE) {
                            vector[i] = -1;
                        }
                    }

                    
                    System.out.println("Vector:");
                    for (int i = 0; i < rows; i++) {
                        String s = String.format("%3d", vector[i]);
                        System.out.printf(s);
                    }
                    System.out.println();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static boolean isFibonacci(int n) {
        return isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4);
    }

    private static boolean isPerfectSquare(int x) {
        int s = (int) Math.sqrt(x);
        return s * s == x;
    }
}