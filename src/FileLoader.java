import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    String filename;
    Path path;

    public FileLoader(String filename) {
        this.filename = filename;
        path = Paths.get(filename);
    }

    List<String> getLines() {
        try {
            return Files.readAllLines(this.path);
        } catch (IOException e) {
            return List.of();
        }
    }

    String getAllRawCharacters() {
        List<String> fileLines = this.getLines();
        StringBuilder outputString = new StringBuilder(1000000);

        for (String line: fileLines) {

            // Ignore newlines or empty lines
            if (line.isEmpty() || line.equals('\n')) { continue ; }
            for (String word: line.split(" ")) {
                // remove all punctuations from words
                word = word.replaceAll("[^\\sa-zA-Z0-9]", "");

                if (word.isEmpty()) {
                    continue;
                };

                outputString.append(" ");
                outputString.append(word);
            }
        }

        return outputString.toString();
    }
}
