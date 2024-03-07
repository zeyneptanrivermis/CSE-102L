import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

public class Lab03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vehicle car = new Car("BMW", "M4", 4, false, 8, true);
		Truck truck = new Truck("Mercedes", "x4", 2004, false, 8, true);
		Motorcycle motorcycle = new Motorcycle("Kawasaki", "z4", 2008, true, 36, true);
		
		Vehicle[] parkinglot = new Vehicle[] { car, truck, motorcycle};
		for(Vehicle vehicle : parkinglot) {
			vehicle.run();
		}
    }
}

abstract class Vehicle {
	
	protected String brand; // sadece vehicle sınıfınfan ve alt sınıflarından erişilebilir (protected)
	protected String model;
	protected int year;
	protected boolean isRented;
	
	public Vehicle(String brand, String model, int year, boolean isRented) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.isRented = isRented;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public boolean isRented() {
		return isRented;
	}

	public void setRented(boolean isRented) {
		this.isRented = isRented;
	}
	
	public abstract void run();
		
}


class Car extends Vehicle{

	public Car(String brand, String model, int year, boolean isRented,
			int passengerCapacity, boolean automaticTransmission) {
		super(brand, model, year, isRented);
		// TODO Auto-generated constructor stub
	}
	
	private int passengerCapacity;
	private boolean automaticTransmision;
	
	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	
	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}

	public boolean isAutomaticTransmision() {
		return automaticTransmision;
	}

	public void setAutomaticTransmision(boolean automaticTransmision) {
		this.automaticTransmision = automaticTransmision;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Car is running");
	}
}


class Truck extends Vehicle{

	public Truck(String brand, String model, int year, boolean isRented,
			int loadCapacitiy, boolean fourWheelDrive) {
		super(brand, model, year, isRented);
		// TODO Auto-generated constructor stub
	}
	
	private int loadCapacity;
	private boolean fourWheelDrive;
	
	public int getLoadCapacity() {
		return loadCapacity;
	}
	
	public void setLoadCapacity(int loadCapacity) {
		this.loadCapacity = loadCapacity;
	}
	
	public boolean isFourWheelDrive() {
		return fourWheelDrive;
	}
	
	public void setFourWheelDrive(boolean fourWheelDrive) {
		this.fourWheelDrive = fourWheelDrive;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Truck is running");
		
	}
}


class Motorcycle extends Vehicle{

	public Motorcycle(String brand, String model, int year, boolean isRented,
			int EngineVolume, boolean isHasABS) {
		super(brand, model, year, isRented);
		// TODO Auto-generated constructor stub
		
	}

	private int engineVolumes;
	private boolean hasABS;
	
	public int getEngineVolumes() {
		return engineVolumes;
	}
	
	public void setEngineVolumes(int engineVolumes) {
		this.engineVolumes = engineVolumes;
	}
	
	public boolean isHasABS() {
		return hasABS;
	}
	
	public void setHasABS(boolean hasABS) {
		this.hasABS = hasABS;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Motorcycle is running");
	}
}


class Customer{
	private String firstName;
	private String lastName;
	private int id;

    ArrayList<Vehicle> rentedVehicle = new ArrayList<>();
    ArrayList<RentalContract> rentalContracts;

    public Customer(String firstName, String lastName, int capacity){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = genereateId();

        ArrayList<Vehicle> rentedVehicles = new ArrayList<Vehicle>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    private int genereateId(){
        Random rnd = new Random();
        return rnd.nextInt(1000, 9999);
    }

    public RentalContract rent (Vehicle v, Date startDate, Date endDate){
        RentalContract contract = new RentalContract(this, v, startDate, endDate);
        rentalContracts.add(contract);
        rentedVehicle.add(v);
        return contract;
    }
}


class RentalContract{
	private Customer customer;
	private Vehicle rentedVehicle;
    private Date startDate;
    private Date endDate;

    RentalContract(Customer customer, Vehicle vehicle, Date starDate, Date endDate){
        this.customer = customer;
        this.rentedVehicle = rentedVehicle;
        this.startDate = starDate;
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date period(){
        return new Date(endDate.getTime() - startDate.getTime());
    }    
}

