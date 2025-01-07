package com.example.myapplication;

public class Module {
    private int id;
    private String name;
    private double coefficient;
    private double test;
    private double practical;
    private double project;
    private double testPercentage;
    private double practicalPercentage;
    private double projectPercentage;
    private double average;

    public Module(int id, String name, double coefficient, double test, double practical, double project,
                  double testPercentage, double practicalPercentage, double projectPercentage, double average) {
        this.id = id;
        this.name = name;
        this.coefficient = coefficient;
        this.test = test;
        this.practical = practical;
        this.project = project;
        this.testPercentage = testPercentage;
        this.practicalPercentage = practicalPercentage;
        this.projectPercentage = projectPercentage;
        this.average = average;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public double getCoefficient() { return coefficient; }
    public double getTest() { return test; }
    public double getPractical() { return practical; }
    public double getProject() { return project; }
    public double getTestPercentage() { return testPercentage; }
    public double getPracticalPercentage() { return practicalPercentage; }
    public double getProjectPercentage() { return projectPercentage; }
    public double getAverage() { return average; }
}