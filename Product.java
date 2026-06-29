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

    // Method untuk set nama
    public void setName(String name) {
        this.name = name;
    }

    // Menthod untuk mengambil kategori
    public String getCategory() {
        return category;
    }

    // Method untuk set kategori
    public void setCategory(String category) {
        this.category = category;
    }

    // Method untuk Mengambil harga
    public double getPrice() {
        return price;
    }

    // Method untuk set harga
    public void setPrice(double price) {
        this.price = price;
    }

    // Method untuk mengambil rating 
    public double getRating() {
        return rating;
    }

    // Method untuk set rating
    public void setRating(double rating) {
        this.rating = rating;
    }

    // Method untuk mengambil summary
    public String getSummary() {
        return summary;
    }

    // Method untuk set summary
    public void setSummary(String summary) {
        this.summary = summary;
    }


    // Method untuk mengambil deskripsi
    public String getDescription() {
        return description;
    }

    // Method untuk set deskripsi
    public void setDescription(String description) {
        this.description = description;
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
