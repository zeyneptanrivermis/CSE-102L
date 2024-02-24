//  ZEYNEP TANRIVERMİŞ
//  20220808038
//  23.02.2024
public class HW01_20220808038 {
	public static void main(String[] args) {
        //  testing the Stock class
        Stock stock = new Stock("GoogL", "AlPhabet inc.");
        stock.setPreviousClosingPrice(100);
        stock.setCurrentPrice(105);
        
        System.out.println("Stock Information:");
        System.out.println("Symbol: " + stock.getSymbol());
        System.out.println("Name: " + stock.getName());
        System.out.println("Previous Closing Price: $" + stock.getPreviousClosingPrice());
        System.out.println("Current Price: $" + stock.getCurrentPrice());
        System.out.println("Price Change: " + stock.getChangePercent() + "%");
        System.out.println(stock.toString()); 

        
        System.out.println("\n--------------------------------\n");

        //  testing the Fan class
        Fan fan1 = new Fan();
        fan1.setOn(true);
        fan1.setSpeed(Fan.getFast());
        fan1.setColor("Yellow");
        fan1.setRadius(10);

        Fan fan2 = new Fan(5, "Blue");
        fan2.setOn(false);

        System.out.println("Fan 1 Status:");
        System.out.println(fan1.toString(fan1.isOn())); 
        System.out.println("\nFan 2 Status:");
        System.out.println(fan2.toString(fan2.isOn())); 
    }
}


//  class of the question 1
class Stock{
	
	//  data fields
	private String symbol;
	private String name;
	private double previousClosingPrice;
	private double currentPrice;
	
	//  constructors
	public Stock(String symbol, String name) {
		this.symbol = symbol.toUpperCase();
		this.name = toTitleCase(name);
	}
	
	//  getter methods (accessor)
	public String getSymbol() {
		return symbol;
	}
	public String getName() {
		return name;
	}
	public double getPreviousClosingPrice() {
		return previousClosingPrice;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	
	//  mutators for double data fields
	public void setPreviousClosingPrice(double previousClosingPrice) {
		if(previousClosingPrice<0) {
			System.out.println("ERROR: Invalid Value");
		}
		else {
			this.previousClosingPrice = previousClosingPrice;
		}
	}
	public void setCurrentPrice(double currentPrice) {
		if(currentPrice<0) {
			System.out.println("ERROR: Invalid Value");
		}
		else {
			this.currentPrice = currentPrice;
		}
	}
	
	//  methods 
	public double getChangePercent() {
		if(previousClosingPrice == 0) {
			return 0.0;
		}
		return((currentPrice-previousClosingPrice)/previousClosingPrice)*100;
	}
	
	public String toString() {
        return "[" + symbol + "] - " + name + ": " + currentPrice;
    }
	
	public static String toTitleCase(String name) {
		String[] words = name.split(" ");
        StringBuilder titleCase = new StringBuilder();
        for (String word : words) {
        	if (!word.isEmpty()) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                          .append(word.substring(1).toLowerCase())
                          .append(" ");
            }
        }
        return titleCase.toString().trim();
	}	
}


//  class of the question 2
class Fan{
	
	//  data fields
	private static final int SLOW = 1;
	private static  final int MEDIUM = 2;
	private static final int FAST = 3;
	private int speed;
	private boolean on;
	private double radius;
	private String color;
	
    //  constructors
	public Fan() {
		this.speed = SLOW;
		this.on = false;
		this.radius = 5.0;
		this.color = "Blue";
	}
	
	public Fan(double radius, String color) {
		//  invoke no-arg
		this();
		//  sets radius and color to data fields
		this.radius = radius;
		this.color = color;
	}

	//  getter and setter methods (accessor and mutator)
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if(on == true) {
			this.speed = speed;
		}
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getSLOW() {
		return SLOW;
	}

	public static int getMedium() {
		return MEDIUM;
	}

	public static int getFast() {
		return FAST;
	}
	
	//  methods 
	public boolean isOn() {
		return on;
	}
	
	public void change() {
		if(on == true) {
			on = false;
		}
		else {
			on = true;
		}
	}
	
	public String toString(boolean on) {
		if (this.on) {
	        return "Fan is on - Speed: " + speed + ", Color: " + color + ", Radius: " + radius;
	    } 
		else {
	        return "Fan is off - Color: " + color + ", Radius: " + radius;
	    }
	}	
}
