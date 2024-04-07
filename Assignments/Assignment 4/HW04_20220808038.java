import java.util.Random;

public class HW04_20220808038 {

    public static void main(String[] args) {
        CPU cpu = new CPU("Intel", 2.5);
        RAM ram = new RAM("DDR4", 8);

        // Test Computer class
    Computer computer = new Computer(cpu, ram);
    try {
        computer.run();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println(computer);

    // Test Laptop class
    Laptop laptop = new Laptop(cpu, ram, 5000, 0);
    System.out.println("Initial Battery Percentage: " + laptop.batteryPercentage());
    try {
        laptop.run();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Battery Percentage after running: " + laptop.batteryPercentage());
    laptop.charge();
    System.out.println("Battery Percentage after charging: " + laptop.batteryPercentage());
    System.out.println(laptop);

    // Test Desktop class
    Desktop desktop = new Desktop(cpu, ram, "Monitor", "Keyboard", "Mouse");
    try {
        desktop.run();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println(desktop);
    desktop.plugIn("Printer");
    System.out.println(desktop);
    desktop.plugOut();
    System.out.println(desktop);
    desktop.plugOut(1);
    System.out.println(desktop);
    }
}

class Computer {
    protected CPU cpu;
    protected RAM ram;

    public Computer(CPU cpu, RAM ram) {
        this.cpu = cpu;
        this.ram = ram;
    }

    public void run() throws InterruptedException {
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            sum += ram.getValue(i, i);
        }
        try {
            ram.setValue(0, 0, sum);
        } catch (MemoryException e) {
            e.printStackTrace();
        } catch (ComputationException e) {
            try {
                sum = cpu.fixComputation(sum, sum);
                ram.setValue(0, 0, sum);
            } catch (ComputationException ex) {
                ex.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); 
        }
    }

    public String toString() {
        return  "Computer: " + cpu + " " + ram;
    }
}

class Laptop extends Computer {
    private int milliAmp;
    private int battery;

    public Laptop(CPU cpu, RAM ram, int milliAmp, int battery) {
        super(cpu, ram);
        this.milliAmp = milliAmp;
        this.battery = battery;
    }

    public int batteryPercentage() {
        return battery * 100 / milliAmp;
    }

    public void charge() {
        while (battery < milliAmp * 90 / 100) {
            battery += milliAmp * 2 / 100;
        }
    }

    public void run() throws InterruptedException {
        if (battery > milliAmp * 5 / 100) {
            super.run();
            battery -= milliAmp * 3 / 100;
        } else {
            charge();
        }
    }

    public String toString() {
       return super.toString() + " " + battery;
    }
}

class Desktop extends Computer {
    private ArrayList<String> peripherals;

    public Desktop(CPU cpu, RAM ram, String... peripherals) {
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }

    public void run() throws InterruptedException {
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            for (int j = 0; j < ram.getCapacity(); j++) {
                try {
                    sum = cpu.compute(sum, ram.getValue(i, j));
                } catch (ComputationException e) {
                    throw e;
                }
            }
        }
        try {
            ram.setValue(0, 0, sum);
        } catch (MemoryException e) {
            e.printStackTrace();
        }
    }

    public void plugIn(String peripheral) {
        peripherals.add(peripheral);
    }

    public String plugOut() {
        if (peripherals.isEmpty()) {
            return null;
        }
        return peripherals.remove(peripherals.size() - 1);
    }

    public String plugOut(int index) {
        if (index < 0 || index >= peripherals.size()) {
            return null;
        }
        return peripherals.remove(index);
    }

    public String toString() {
        return super.toString() + " " + String.join(" ", peripherals);
    }
}

class CPU {
    private String name;
    private double clock;

    public CPU(String name, double clock) {
        this.name = name;
        this.clock = clock;
    }

    public String getName() {
        return name;
    }
    public double getClock() {
        return clock;
    }

    public int compute(int a, int b) throws ComputationException, InterruptedException{
        try {
            Thread.sleep((int)((4/clock)*1000));
        } catch (InterruptedException e) {
            throw new ComputationException("Exception occurred due to internal clock speed: " + clock, e);
        }

        int result = a+b;
        if(result<0){
            throw new ComputationException(this, a, b);        
        } 
        System.out.println("Result: " + result);
        return result;
    }

    public String toString() {
        return "CPU: " + name + " " + clock + "Ghz";
    }
}

class RAM {
    public String type;
    public int capacity;
    public int[][] memory;

    public RAM(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        initMemory();
    }

    public String getType() {
        return type;
    }
    public int getCapacity() {
        return capacity;
    }

    private void initMemory() {
        Random random = new Random();
    memory = new int[capacity][capacity];

    for (int i = 0; i < memory.length; i++) {
        for (int k = 0; k < memory.length; k++) {
            memory[i][k] = random.nextInt(11);
        }
    }

    }

    private boolean check(int i, int j) throws MemoryException {
        if(i < capacity && i >= 0 && j < capacity && j >= 0) {
            return true;
        }
        else {
            throw new MemoryException(this, i, j);
        }
    }

    public int getValue(int i, int j) throws MemoryException {
        if (!check(i, j)) {
            return -1;
        }
        return memory[i][j];
    }

    public void setValue(int i, int j, int value) {
        if (check(i, j)) {
            memory[i][j] = value;
        }
    }

    public String toString() {
        return "RAM: " + type + " " + capacity + "GB";
    }  
}

class MemoryException extends Exception {
    private RAM ram;
    private int address1;
    private int address2;

    public MemoryException(RAM ram, int address1, int address2) {
        super("Memory out of range! With addresses #" + address1 + ", " + address2);
        this.ram = ram;
        this.address1 = address1;
        this.address2 = address2;
    }
}

class ComputationException extends Exception {
    private CPU cpu;
    private int value1;
    private int value2;

    public ComputationException(CPU cpu, int value1, int value2) {
        super("Computation exception occurred on " + cpu + " with values: (" + value1 + ", " + value2 + ")");
        this.cpu = cpu;
        this.value1 = value1;
        this.value2 = value2;
    }

    public ComputationException(ComputationException e) {
        super("Unhandled exception occurred at " + e.cpu + " with values " + e.value1 + " and " + e.value2 + ":\n\t" + e.getMessage());
        this.cpu = e.cpu;
        this.value1 = e.value1;
        this.value2 = e.value2;
    }

    public int fixComputation(int val1, int val2) throws ComputationException {
        try {
            val1 = Math.abs(val1);
            val2 = Math.abs(val2);
            return cpu.compute(val1, val2);
        } catch (ComputationException e) {
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    } 
}

