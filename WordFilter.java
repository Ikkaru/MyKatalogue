import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class WordFilter {
    private final Set<String> forbiddenWords;
    private Pattern compiledPattern;

    // Constructor
    public WordFilter() {
        this.forbiddenWords = new HashSet<>();
        // Default forbidden words
        addForbiddenWord("palsu");
        addForbiddenWord("kw");
        addForbiddenWord("tiruan");
        addForbiddenWord("scam");
        addForbiddenWord("bohong");
        addForbiddenWord("illegal");
    }

    // Add ForbiddenWord Method
    public synchronized void addForbiddenWord(String word) {
        // Cek Apakah word tersebut ada isinya
        if (word != null && !word.trim().isEmpty()) {
            forbiddenWords.add(word.trim().toLowerCase());
            updatePattern();
        }
    }

    // Remove Forbidden Word Method
    public synchronized void removeForbiddenWord(String word) {
        // Cek Apakah word tersebut ada isinya
        if (word != null) {
            forbiddenWords.remove(word.trim().toLowerCase());
            updatePattern();
        }
    }

    // Method untuk mengambil semua forbiddenWords
    public synchronized Set<String> getForbiddenWords() {
        return new HashSet<>(forbiddenWords);
    }

    // Compile dan update pattern Regex setiap ada perubahan frobiddenWord
    private void updatePattern() {
        if (forbiddenWords.isEmpty()) {
            compiledPattern = null;
            return;
        }

        // Escape regex special characters in each forbidden word and join with |
        StringBuilder sb = new StringBuilder();
        sb.append("\\b(");
        int i = 0;
        for (String word : forbiddenWords) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(Pattern.quote(word));
            i++;
        }
        sb.append(")\\b");

        // Compile String tersebut menjadi Pattern dengan Flag Case Insensitive
        compiledPattern = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    }

    // Method for checking is the word Contains a forbidden Word
    public synchronized boolean containsForbiddenWord(String text) {
        if (text == null || text.isEmpty() || compiledPattern == null) {
            return false;
        }
        // Jika Ada kata yang match akan mereturn 1 dan jika tidak return 0
        return compiledPattern.matcher(text).find();
    }
}