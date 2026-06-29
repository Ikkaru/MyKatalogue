public class Product {
    private String name;
    private String category;
    private double price;
    private double rating;
    private String summary;
    private String description;

    public Product(String name, String category, double price, double rating, String summary, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.summary = summary;
        this.description = description;
    }

    // Method untuk ngambil nama
    public String getName() {
        return name;
    }

    // Menthod untuk mengambil kategori
    public String getCategory() {
        return category;
    }

    // Method untuk Mengambil harga
    public double getPrice() {
        return price;
    }

    // Method untuk mengambil rating 
    public double getRating() {
        return rating;
    }

    // Method untuk mengambil summary
    public String getSummary() {
        return summary;
    }

    // Method untuk mengambil deskripsi
    public String getDescription() {
        return description;
    }

    // Method untuk format string 
    @Override
    public String toString() {
        return String.format(
            "=========================================\n" +
            "Nama Produk  : %s\n" +
            "Kategori     : %s\n" +
            "Harga        : Rp %,.2f\n" +
            "Rating       : %.1f / 5.0\n" +
            "Ringkasan    : %s\n" +
            "Deskripsi    : %s\n" +
            "=========================================",
            name, category, price, rating, summary, description
        );
    }
}
