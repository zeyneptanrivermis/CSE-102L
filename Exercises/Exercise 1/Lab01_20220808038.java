import java.util.ArrayList;
import java.util.Random;

public class Lab01_20220808038 {
    public static void main(String[] args) {
        Circle circle1 = new Circle(5, "Pink");
        System.out.println(circle1.toString());

        Employee employee1 = new Employee("Zeynep", 10000, 2);
        employee1.promote(employee1);
        employee1.promote(employee1);
        employee1.demote(employee1);
        System.out.println(employee1.toString());
        employee1.doWork(employee1);

        Student student = new Student("Zeynep", 4);
        student.addCourse("CSE");
        student.addCourse("CSE");
        student.addCourse("MATH");
        student.removeCourses("CSE");
        student.printCourses();
    }
}

class Circle {
    private int radius;
    private String color;

    public Circle(int radius, String color) {
        this.radius = radius;
        this.color = color;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public String toString() {
        return "Circle's color " + color + " with area " + getArea();
    }
}

class Employee {
    private Employee[] employees = new Employee[8];
    private static int count;
    private int id = -1;
    private String name = "Default Employee";
    private double salary = 23217.53;
    private int level = 0;

    public Employee(String name, double salary, int level) {
        this.name = name.substring(0, 1).toUpperCase().concat(name.substring(1).toLowerCase());
        this.salary = salary;
        this.level = level;
        generateId(level);
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }
    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public static void promote(Employee e) {
        e.level++;
    }

    public static void demote(Employee e) {
        e.level--;
    }

    private static int generateId(int level) {
        Random rnd = new Random();
        return level * 1000 + rnd.nextInt(1000);
    }

    public void doWork(Employee e) {
        System.out.println(e.getName() + " done work");
    }

    
    public String toString() {
        return "Name: " + name + "\n" + "Id: " + id + "\n" + "Salary: " + salary + "\n" + "Level: " + level + "\n";
    }
}

class Student {
    private String name;
    private int grade;
    private ArrayList<String> courses;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
        this.courses = new ArrayList<>();
    }

    public void addCourse(String courseName) {
        courses.add(courseName);
    }

    public void removeCourses(String courseName) {
        courses.remove(courseName);
    }

    public void printCourses() {
        for(String Courses: courses) {
            System.out.print(courses + ", ");
        }
    }
}