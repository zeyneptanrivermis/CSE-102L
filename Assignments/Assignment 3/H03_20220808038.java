//  ZEYNEP TANRIVERMİŞ
//  20220808038
public class H03_20220808038 {
    public static void main(String[] args) {
        Author author1 = new Author("John", "Doe", "john.doe@example.com");
        Author author2 = new Author("Jane", "Smith", "jane.smith@example.com");

        Book book1 = new Book("123456789", "Book Title 1", author1, 25.99);
        Book book2 = new Book("987654321", "Book Title 2", author2);

        EBook ebook1 = new EBook("111222333", "EBook Title 1", author1, 19.99, "http://example.com/ebook1", 10.5);
        EBook ebook2 = new EBook("333222111", "EBook Title 2", author2, "http://example.com/ebook2", 15.75);

        PaperBook paperBook1 = new PaperBook("444555666", "PaperBook Title 1", author1, 29.99, 10, true);
        PaperBook paperBook2 = new PaperBook("666555444", "PaperBook Title 2", author2, true);

        // Displaying information about the books
        System.out.println("Book 1:");
        System.out.println("ISBN: " + book1.getIsbn());
        System.out.println("Title: " + book1.getTitle());
        System.out.println("Author: " + book1.getAuthor().getName() + " " + book1.getAuthor().getSurname());
        System.out.println("Price: $" + book1.getPrice());

        System.out.println("\nEBook 1:");
        System.out.println("ISBN: " + ebook1.getIsbn());
        System.out.println("Title: " + ebook1.getTitle());
        System.out.println("Author: " + ebook1.getAuthor().getName() + " " + ebook1.getAuthor().getSurname());
        System.out.println("Price: $" + ebook1.getPrice());
        System.out.println("Download URL: " + ebook1.getDownloadUrl());
        System.out.println("Size (MB): " + ebook1.getSizeMb());

        System.out.println("\nPaperBook 1:");
        System.out.println("ISBN: " + paperBook1.getIsbn());
        System.out.println("Title: " + paperBook1.getTitle());
        System.out.println("Author: " + paperBook1.getAuthor().getName() + " " + paperBook1.getAuthor().getSurname());
        System.out.println("Price: $" + paperBook1.getPrice());
        System.out.println("Shipping Weight: " + paperBook1.getShippingWeight() + " oz");
        System.out.println("In Stock: " + (paperBook1.isInStock() ? "Yes" : "No"));
    }
}

class Author {
    private String name;
    private String surname;
    private String mail;

    public Author(String name, String surname, String mail) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
}

class Book {
    private String isbn;
    private String title;
    private Author author;
    private double price;
    
    public Book(String isbn, String title, Author author, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        setPrice(15.25);
    }

    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    public Author getAuthor() {
        return author;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}

class EBook extends Book {
    private String downloadUrl;
    private double sizeMb;

    public EBook(String isbn, String title, Author author, double price, String downloadUrl, double sizeMb) {
        super(isbn, title, author, price);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }

    public EBook(String isbn, String title, Author author, String downloadUrl, double sizeMb) {
        super(isbn, title, author);
        this.downloadUrl = downloadUrl;
        this.sizeMb = sizeMb;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
    public double getSizeMb() {
        return sizeMb;
    }
}

class PaperBook extends Book {
    private int shippingWeight;
    private boolean inStock;

    public PaperBook(String isbn, String title, Author author, double price, int shippingWeigh, boolean inStock) {
        super(isbn, title, author, price);
        this.shippingWeight = shippingWeigh;
        this.inStock = inStock;
    }

    public PaperBook(String isbn, String title, Author author,  boolean inStock) {
        super(isbn, title, author);
        this.inStock = inStock;
        setShippingWeight((int) (Math.random() * 11) + 5);
    }

    public int getShippingWeight() {
        return shippingWeight;
    }
    public void setShippingWeight(int shippingWeigh) {
        this.shippingWeight = shippingWeigh;
    }
    public boolean isInStock() {
        return inStock;
    }
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    } 
}