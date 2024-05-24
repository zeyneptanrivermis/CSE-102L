import java.util.Arrays;

public class H07_20220808038 {
    public static void main(String[] args) {

    }
}

interface Common<T> {
    boolean enqueue(Container item);

    boolean isEmpty();
    T peek();
    int size();
}

interface PriorityQueue<T>  extends Common<T> {
    static final int FLEET_CAPACITY = 3;
    @Override
    boolean isEmpty();
    @Override
    T peek();
    @Override
    int size();
    boolean enqueue(T item);
    boolean dequeue();
}

interface Stack<T> extends Common<T> {
    @Override
    boolean isEmpty();
    @Override
    T peek();
    @Override
    int size();
    boolean push(T item);
    T pop();
}

interface Node<T> {
    static final int DEFAULT_CAPACITY =2;
    T getNext();
    double getPriority();
    void setNext(T item);
}

interface Package <T>  {
    T extract();
    double getPriority();
    boolean isEmpty();
    boolean pack(T item);
}

interface Sellable{
    String getName();
    double getPrice();
}

interface Wrappable extends Sellable {
    @Override
    String getName();
    @Override
    double getPrice();
}

class Box  <T extends Sellable> extends Object implements Package<T> {
    private int distanceToAddress;
    private T item;
    private boolean seal;

    public Box() {
        this.item = null;
        this.seal = false;
    }

    public Box(T item, int distanceToAddress) {
        this.item = item;
        this.distanceToAddress = distanceToAddress;
        seal = true;
    }

    @Override
    public T extract() {
        T extractedItem = item;
        item = null;
        seal = false;
        return extractedItem;
    }

    @Override
    public boolean isEmpty() {
        if(item!=null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            seal = true;
            return true;
        }
        return false;
    }

    public boolean isSealBroken() {
        if(seal==true) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public double getPriority() {
        if (item != null) {
            double price = item.getPrice();
            double priceFactor = 0.5;
            double distanceFactor = 0.5;
            return priceFactor * price - distanceFactor * distanceToAddress;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " {" + item +"} Seal: " + seal;
    }
}

class CargoCompany {
    private CargoFleet queue;
    private Container stack;

    public CargoCompany() {
        this.queue = null;
        this.stack = null;
    }

    public <T extends Box<?>> void add(T box) {
        if(stack == null) {
            stack = new Container();
        }
        if()
    }

    private void ship(CargoFleet fleet) {

    }

    private void empty(Container container) {

    }

    private <T extends Box<?>> Sellable deliver(T box) {

    }
}

abstract class CargoFleet implements Common, PriorityQueue {
    private Container head;
    private int size;

    public CargoFleet() {
        this.head = null;
    }

    @Override
    public boolean enqueue(Container item) {
        if (item == null) {
            return false;
        }
        if (head == null) {
            head = (Container) item; // If the fleet is empty, add the first container.
        }
        else {
            Container current = head;
            Container previous = null;
            while (current != null && item.compareTo(current) < 0) {
                previous = current;
                current = current.next;
            }
            if (previous == null) {
                item.next = head;
                head = item;
            } else {
                item.next = current;
                previous.next = item;
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean dequeue() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Object peek() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}

class Container implements Common<T>, Stack, Node {
    private Box<?>[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    public Container() {
        this.size = 0; //
    }

    public int compareTo(Container o) {

    }

    @Override
    public boolean push(Object item) {
        return false;
    }

    @Override
    public Object pop() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        if(this.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Container getNext() {
        return next;
    }

    @Override
    public double getPriority() {
        return 0;
    }

    public void setNext(Container item) {
        item = next;
    }

    @Override
    public String toString() {
        return "Container{" +
                "boxes=" + Arrays.toString(boxes) +
                ", top=" + top +
                ", size=" + size +
                ", priority=" + priority +
                ", next=" + next +
                '}';
    }
}

abstract class Product implements Sellable {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " {Name:" + name + ", Price:" + price + "}";
    }
}

class Matroschka <T extends  Wrappable> extends Product implements Wrappable, Package<T> {
    private T item;

    public Matroschka(T item) {
        super("Doll", 5 + (item != null ? item.getPrice() : 0));
        this.item = item;
    }

    @Override
    public T extract() {
        T extractedItem = item;
        item = null;
        return extractedItem;
    }

    @Override
    public boolean isEmpty() {
        if (item==null) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }
        return false;
    }

    @Override
    public double getPriority() {
        if (item != null && item instanceof Sellable) {
            double basePriority = 10;
            double pricePriorityMultiplier = 0.1;
            double priceComponent = ((Sellable) item).getPrice() * pricePriorityMultiplier;
            return basePriority + priceComponent;
        }
        else {
            return item.getPrice();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " {Item: " + item + "}";
    }
}

class Mirror extends Product implements Sellable {
    private int width;
    private int height;

    public Mirror(int width, int height) {
        super("mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    @Override
    public double getPrice() {
        return super.getPrice() * getArea();
    }

    public <T> T reflect(T item) {
        System.out.println("Reflecting item: " + item);
        return item;
    }
}

class Paper extends Product implements Sellable, Wrappable {
    private String note;

    public Paper() {
        super("Paper", 0.5);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

class  UnsupportedOperationException extends Throwable {

}