import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Chris on 05-Sep-17.
 */
public class TextSearcher {

    public File[] findAllFilesAtPath(String path) {
        File[] episodes = (new File(path).listFiles());
        if (episodes != null) {
            return (Stream.of(episodes)
                    .filter(File::isFile)
                    .collect(Collectors.toList()).toArray(new File[0]));
        }
        return null;
    }

    public List<Pair<String, Integer>> searchForWordsInAllEpisodes(String words) {
        File[] episodes = findAllFilesAtPath(WebCrawler.APPLICATION_PATH + File.separator + "Season1");

        List<Pair<String, Integer>> occurrences = new LinkedList<>();
        for (File file : episodes) {
            if (file.getName().contains(".txt")) {
                String fileName = file.getName();
                occurrences.add(new Pair<>(fileName.replace(".txt", ""), searchForWordsInFile(words.toLowerCase(), fileName)));
            }
        }
        return occurrences;
    }

    public int searchForWordsInFile(String words, String episodeTitle) {
        try {
            File file = new File(WebCrawler.APPLICATION_PATH + File.separator + "Season1" + File.separator + episodeTitle);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            Iterator<String> it = lines.iterator();
            String title = "";
            if (it.hasNext()) {
                // Get the title from the file
                // title can also be episodeTitle, but this might change if the saving to file changes
                title = it.next();
            }
            int totalOccurrences = 0;
            while (it.hasNext()) {
                totalOccurrences += countWordsInString(words, it.next().toLowerCase());
            }
            return totalOccurrences;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int countWordsInString(String searchFor, String text) {
        int count = 0;
        Pattern p = Pattern.compile(searchFor);
        Matcher m = p.matcher(text);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
