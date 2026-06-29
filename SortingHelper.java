import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingHelper {

    // Atur perbandingan untuk Rating Tertinggi (Besar ke Kecil) -> O(1)
    public static final Comparator<Product> BY_RATING_DESC = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return Double.compare(p2.getRating(), p1.getRating()); 
        }
    };

    // Atur perbandingan untuk Harga Termurah (Kecil ke Besar) -> O(1)
    public static final Comparator<Product> BY_PRICE_ASC = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return Double.compare(p1.getPrice(), p2.getPrice()); 
        }
    };

    // Atur perbandingan untuk Harga Termahal (Besar ke Kecil) -> O(1)
    public static final Comparator<Product> BY_PRICE_DESC = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return Double.compare(p2.getPrice(), p1.getPrice()); 
        }
    };

    // Atur perbandingan untuk Urutan Abjad Nama (A ke Z) -> O(1)
    public static final Comparator<Product> BY_NAME_ASC = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getName().compareToIgnoreCase(p2.getName()); 
        }
    };

    /**
     * Fungsi utama untuk memulai pengurutan manual Merge Sort.
     * Kompleksitas Waktu: O(N log N) -> Sangat cepat dan kecepatannya stabil di segala kondisi.
     * Kompleksitas Ruang: O(N) -> Memerlukan satu list 'temp' bantuan untuk tempat menyusun data.
     */
    public static void mergeSort(List<Product> list, Comparator<Product> comparator) {
        // Jika list kosong atau cuma berisi 1 data, tidak perlu diurutkan
        if (list == null || list.size() <= 1) {
            return;
        }
        // Buat list temporary seukuran list asli untuk tempat nampung data sementara
        List<Product> temp = new ArrayList<>(list);
        // Jalankan fungsi pembelah data
        mergeSortHelper(list, temp, 0, list.size() - 1, comparator);
    }

    /**
     * Fungsi Rekursif untuk membelah (Divide) list menjadi dua bagian terus-menerus.
     * Kompleksitas Waktu: O(N log N) karena pembelahan terjadi secara logaritmik.
     */
    private static void mergeSortHelper(List<Product> list, List<Product> temp, int left, int right, Comparator<Product> comparator) {
        if (left < right) {
            // Cari nilai tengah untuk memotong list jadi dua pecahan
            int mid = left + (right - left) / 2; 
            
            mergeSortHelper(list, temp, left, mid, comparator);      // Belah bagian kiri terus sampai habis
            mergeSortHelper(list, temp, mid + 1, right, comparator);  // Belah bagian kanan terus sampai habis
            merge(list, temp, left, mid, right, comparator);          // Gabungkan & urutkan pecahan tersebut (Conquer)
        }
    }

    /**
     * Fungsi untuk menyatukan (Merge) kembali pecahan data sambil diurutkan posisinya.
     * Kompleksitas Waktu: O(N) karena mencocokkan isi elemen satu demi satu secara urut.
     */
    private static void merge(List<Product> list, List<Product> temp, int left, int mid, int right, Comparator<Product> comparator) {
        // Copy isi data asli yang mau diurutkan ke list temporary 'temp'
        for (int i = left; i <= right; i++) {
            temp.set(i, list.get(i));
        }

        int i = left;       // Penunjuk (pointer) untuk pecahan list sebelah kiri
        int j = mid + 1;    // Penunjuk (pointer) untuk pecahan list sebelah kanan
        int k = left;       // Penunjuk posisi untuk memasukkan data yang sudah rapi ke list utama

        // Bandingkan isi list kiri dan list kanan, mana yang sesuai aturan komparator dimasukkan duluan
        while (i <= mid && j <= right) {
            if (comparator.compare(temp.get(i), temp.get(j)) <= 0) {
                list.set(k, temp.get(i));
                i++;
            } else {
                list.set(k, temp.get(j));
                j++;
            }
            k++;
        }

        // Jika sisa data di pecahan kiri masih ada, masukkan semuanya ke list utama
        while (i <= mid) {
            list.set(k, temp.get(i));
            i++;
            k++;
        }
        // Catatan: Sisa data di pecahan kanan tidak perlu di-copy karena posisinya otomatis sudah pas.
    }
}
