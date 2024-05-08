public class H06_20220808038 {
    public static void main(String[] args) {
        
    }
}

interface Sellable{
    public String getName();
    public double getPrice();
}

interface Package <T>  {
    public T extract();
    public boolean pack(T item);
    public boolean isEmpty();
}

interface Wrappable extends Sellable {
    public boolean isWrappable();
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
        return this.getClass().getSimpleName() + " (" + name + ", " + price + ")";
    }
}

class Mirror extends Product {
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

class Paper extends Product implements Wrappable {
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

    @Override
    public boolean isWrappable() {
        return true;
    }
}

class Matroschka <T extends  Wrappable> extends Product implements Wrappable, Package<T> {
    private T item;

    public Matroschka(T item) {
        super("Doll", 5 + (item != null ? item.getPrice() : 0));
        this.item = item;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + item + "]";
    }


    @Override
    public T extract() {
        T extractedItem = item;
        item = null;
        return extractedItem;
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
    public boolean isEmpty() {
        return item == null;
    }

    @Override
    public boolean isWrappable() {
        return true;
    }
}

class Box  <T extends Sellable> implements Package<T> {
    private T item;
    private boolean seal;

    public Box() {
        this.item = null;
        this.seal = false;
    }

    public Box(T item) {
        this.item = item;
        this.seal = true;
    }

    @Override
    public T extract() {
        T extractedItem = item;
        item = null;
        seal = false;
        return extractedItem;
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

    @Override
    public boolean isEmpty() {
        return item == null;
    }

    public boolean isSealBroken() {
        return !seal;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " {" + item +"} Seal: " + seal;
    }


}