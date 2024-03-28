//  ZEYNEP TANRIVERMİŞ
//  20220808038

import java.util.Calendar;
import java.util.Date;

public class H02_20220808038 {
    public static void main(String[] args) {
        // Create instances of City
        City city1 = new City("12345", "City A");
        City city2 = new City("67890", "City B");

        // Create an instance of Ticket
        Ticket ticket1 = new Ticket(city1, city2, new Date(), 1);
        Ticket ticket2 = new Ticket(city1, city2, 2);

        // Create an instance of Person
        Person person = new Person("John", "Doe", "123-456-7890");

        // Test methods of Ticket
        System.out.println("From: " + ticket1.getFrom().getName());
        System.out.println("To: " + ticket1.getTo().getName());
        System.out.println("Date: " + ticket1.getDate());
        System.out.println("Seat: " + ticket1.getSeat());

        System.out.println("From: " + ticket2.getFrom().getName());
        System.out.println("To: " + ticket2.getTo().getName());
        System.out.println("Date: " + ticket2.getDate());
        System.out.println("Seat: " + ticket2.getSeat());

        // Test methods of Person
        System.out.println("Name: " + person.getName());
        System.out.println("Surname: " + person.getSurname());
        System.out.println("Phone Number: " + person.getPhoneNumber());

        // Test setting phone number
        person.setPhoneNumber("987-654-3210");
        System.out.println("New Phone Number: " + person.getPhoneNumber());
    }
    
    
}

class Ticket {
    private City from;
    private City to;
    private Date date;
    private int seat;

    public Ticket(City from, City to, Date date, int seat) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.seat = seat;
    }

    public Ticket(City from, City to, int seat) {
        this.from = from;
        this.to = to;
        this.seat = seat;
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        System.out.println(date);
    }

    public City getFrom() {
        return from;
    }
    public City getTo() {
        return to;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
}

class City {
    private String postalCode;
    private String name;

    public City(String postalCode, String name) {
        this.postalCode = postalCode;
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public String getName() {
        return name;
    }
}

class Person {
    private String name;
    private String surname;
    private String phoneNumber;

    public Person(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }  
}