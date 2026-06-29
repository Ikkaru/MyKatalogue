import java.util.*;

public class ProductManager {
    private final List<Product> products; // List yang berisi seluruh produk
    private final Map<String, List<Product>> categoryIndex; // Map untuk pencarian index berdasarkan kategori
    private final Map<String, List<Product>> keywordIndex; // Map untuk pencarian index berdasarkan Keyword dari Summary
    private final WordFilter wordFilter;
    private final String filePath;

    // Contructor jika menspesifikasikan argumen nama file dari json untuk penyimpanan data presistent
    public ProductManager(WordFilter wordFilter, String filePath) {
        this.products = new ArrayList<>();
        this.categoryIndex = new HashMap<>();
        this.keywordIndex = new HashMap<>();
        this.wordFilter = wordFilter;
        this.filePath = filePath;
    }

    // Consturctor jika tidak menspesifikasikan argumen nama file json penyimpanan, secara default akan memasukkan products.json
    public ProductManager(WordFilter wordFilter) {
        this(wordFilter, "products.json");
    }

    // Method untuk menambahkan Produk
    public boolean addProduct(Product product) {
        // Cek apakah produk ada
        if (product == null) {
            return false;
        }
        // Panggil method untuk menambahkan producrt ke memory
        boolean isAdded = addProductInMemory(product);
        
        // Jika berhasil maka Save ke presistent memory
        if (isAdded) {
            saveToDisk();
        }
        return isAdded;
    }

    // Method untuk menambahkan product ke memory
    private boolean addProductInMemory(Product product) {
        // Cek apakah ada kata terlarang di nama, summary, atau deskripsi
        if (wordFilter.containsForbiddenWord(product.getName()) ||
            wordFilter.containsForbiddenWord(product.getSummary()) ||
            wordFilter.containsForbiddenWord(product.getDescription())) {
            return false;
        } 
        
        products.add(product);

        // Index by Category (Case-insensitive)
        String catKey = product.getCategory().trim().toLowerCase();
        // Jika Map untuk key tersebut kosong maka buat arrayList Baru, dan jika tidak kosong maka kembalikan ArrayList tersebut dan tambahkan Produknya
        categoryIndex.computeIfAbsent(catKey, k -> new ArrayList<>()).add(product);

        // Index by Keywords in Product Summary
        indexProductKeywords(product);
        
        return true;
    }

    /**
     * Splits the product's summary into cleaned, lowercase tokens
     * and indexes them.
     */
    private void indexProductKeywords(Product product) {
        String summary = product.getSummary();
        if (summary == null || summary.trim().isEmpty()) {
            return;
        }


        // Memfilter dan membuang tanbda baca serta mengubah kalimat menjadi lowecase
        String cleanSummary = summary.replaceAll("[^a-zA-Z0-9\\s]", " ").toLowerCase();
        // Mentokenisasi kalimat menjadi kata
        String[] tokens = cleanSummary.split("\\s+");

        // Menggunakan set untuk mengambil kata kunci unik agar tidak ada kata yang double
        Set<String> uniqueWords = new HashSet<>();
        for (String token : tokens) {
            String word = token.trim();
            if (word.length() >= 2) {
                uniqueWords.add(word);  
            }
        }

        // Menambahkan ke Keyword Index
        for (String word : uniqueWords) {
            // Jika Map untuk key tersebut kosong maka buat arrayList Baru, dan jika tidak kosong maka kembalikan ArrayList tersebut dan tambahkan Produknya
            keywordIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(product);
        }
    }


    // Method untuk menyimpan list produk ke file json
    public void saveToDisk() {
        JsonStorage.saveProducts(filePath, products);
    }

    // Method untuk me load data dari file json. akan menghapus buffer memory saat ini dan akan menimpa nya dengan data dari json
    public void loadFromDisk() {
        // Clear List Produk di Memory saat ini
        products.clear();
        categoryIndex.clear();
        keywordIndex.clear();


        // Load data dari json ke memory
        List<Product> loaded = JsonStorage.loadProducts(filePath);
        for (Product p : loaded) {
            addProductInMemory(p);
        }
    }


    // method untuk mereturn list dari seluruh produk
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }


    // Method untuk mencari produk berdasarkan Katergori yang diberikan
    public List<Product> getProductsByCategory(String category) {
        if (category == null) {
            return new ArrayList<>();
        }
        String catKey = category.trim().toLowerCase();
        return new ArrayList<>(categoryIndex.getOrDefault(catKey, Collections.emptyList()));
    }

    // Method untuk mencari produk berdasarkan keyword summary yang diberikan
    public List<Product> searchByKeyword(String query) {
        // Cek apakah Querynya Kosong
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Memfilter dan membuang tanda baca. dan mengubah seluruh kata menjadi lowercase
        String cleanQuery = query.replaceAll("[^a-zA-Z0-9\\s]", " ").toLowerCase();
        // Tokenisasi. Memecah Kalimat menjadi kata
        String[] queryWords = cleanQuery.split("\\s+");

        Set<Product> matchedProducts = new LinkedHashSet<>();
        for (String word : queryWords) {
            word = word.trim();
            if (word.length() >= 2) {
                List<Product> matches = keywordIndex.get(word);
                if (matches != null) {
                    matchedProducts.addAll(matches);
                }
            }
        }

        return new ArrayList<>(matchedProducts);
    }


    // Clear Semua list Produk Termasuk yang berada di dalam file json
    public void clearAll() {
        products.clear();
        categoryIndex.clear();
        keywordIndex.clear();
        saveToDisk();
    }
}
