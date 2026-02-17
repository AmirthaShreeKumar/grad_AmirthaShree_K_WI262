
/*
* Find the highest salary paid employee
* Find how many male & female employees working in company (numbers)
* Total expense for the company department wise
* Who is the top 5 senior employees in the company
* Find only the names who all are managers
* Hike the salary by 20% for everyone except manager
* Find the total number of employees


*/


import java.util.*;
import java.util.stream.Collectors;

public class Employee {
    public static void main(String[] args) {

        List<Emp> list = new ArrayList<>();

        list.add(new Emp("Sam", 21, "Male", 200000, "HR", "Consultant"));
        list.add(new Emp("Tam", 22, "Male", 300000, "HM", "Consultant"));
        list.add(new Emp("Riya", 24, "Female", 450000, "IT", "Developer"));
        list.add(new Emp("Amit", 28, "Male", 600000, "Finance", "Analyst"));
        list.add(new Emp("Neha", 26, "Female", 500000, "Marketing", "Executive"));
        list.add(new Emp("Rahul", 30, "Male", 800000, "IT", "Senior Developer"));
        list.add(new Emp("Sneha", 23, "Female", 350000, "HR", "Recruiter"));
        list.add(new Emp("Karan", 29, "Male", 750000, "Operations", "Manager"));
        list.add(new Emp("Pooja", 27, "Female", 550000, "Finance", "Accountant"));
        list.add(new Emp("Vikram", 35, "Male", 1200000, "Management", "Director"));
        list.add(new Emp("Anjali", 25, "Female", 400000, "Sales", "Sales Executive"));
        list.add(new Emp("Rohit", 32, "Male", 900000, "Sales", "Sales Manager"));
        list.add(new Emp("Meena", 34, "Female", 950000, "IT", "Project Manager"));
        list.add(new Emp("Suresh", 41, "Male", 1500000, "Operations", "General Manager"));
        list.add(new Emp("Kavya", 28, "Female", 650000, "Design", "UI/UX Designer"));
        list.add(new Emp("Arjun", 26, "Male", 480000, "Support", "Support Engineer"));
        list.add(new Emp("Nisha", 31, "Female", 820000, "Marketing", "Marketing Manager"));
        list.add(new Emp("Manoj", 38, "Male", 1100000, "Finance", "Finance Manager"));
        list.add(new Emp("Isha", 24, "Female", 320000, "Content", "Content Writer"));
        list.add(new Emp("Dev", 29, "Male", 700000, "R&D", "Research Engineer"));

        System.out.println(" 1. Highest salary employee ");
        list.stream()
            .max(Comparator.comparingDouble(Emp::getSalary))
            .ifPresent(e -> System.out.println("Highest Salary: " + e.getName()));

        System.out.println(" 2. Male & Female count ");
      Map<Boolean, Long> genderCount =
            list.stream()
                .collect(Collectors.partitioningBy(
                    e -> "Male".equals(e.getGender()),
                    Collectors.counting()
                ));

        System.out.println("Male count: " + genderCount.get(true));
        System.out.println("Female count: " + genderCount.get(false));


        System.out.println("3. Department-wise total expense ");
        Map<String, Double> deptExpense =
                list.stream()
                    .collect(Collectors.groupingBy(
                        Emp::getDept, Collectors.summingDouble(Emp::getSalary)
                    ));
        System.out.println("Department Expense: " + deptExpense);

        System.out.println("Top 5 Senior Employees:");
        list.stream()
            .sorted(Comparator.comparingInt(Emp::getAge).reversed())
            .limit(5)
            .forEach(System.out::println);

        System.out.println("5. Names of all managers ");
        List<String> managers =
                list.stream()
                    .filter(e -> e.getRole().contains("Manager"))
                    .map(Emp::getName)
                    .collect(Collectors.toList());
        System.out.println("Managers: " + managers);

        System.out.println(" 6. Hike salary by 20% except managers" );
        list.stream()
            .filter(e -> !e.getRole().contains("Manager"))
            .forEach(e -> e.setSalary(e.getSalary() * 1.20));

        
        

        System.out.println(" 7. Total employees ");
        long totalEmployees = list.size();
        System.out.println("Total Employees: " + totalEmployees);
    }
}


class Emp {

    private String name;
    private int age;
    private String gender;
    private double salary;
    private String role;
    private String dept;

    Emp(String name, int age, String gender, double salary, String dept, String role) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.dept = dept;    
        this.role = role;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getSalary() { return salary; }
    public String getDept() { return dept; }
    public String getRole() { return role; }

    public void setSalary(double salary) {
        this.salary = salary;   
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", dept='" + dept + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
