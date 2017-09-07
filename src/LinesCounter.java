import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Chris on 05-Sep-17.
 */
public class LinesCounter {

    public Map<String, Integer> countLinesForEpisode(List<ScriptLine> scriptLines) {
        Map<String, Integer> counter = new HashMap<>();
        for (ScriptLine line : scriptLines) {
            counter.put(line.getCharacterName(), counter.getOrDefault(line.getCharacterName(), 0) + 1);
        }
        return counter;
    }

    public Map<String, Statistics> countLinesForSeason(String season) {
        File[] files = new TextSearcher().findAllFilesAtPath(WebCrawler.SEASON_ONE_PATH);
        for (File file : files) {
            Map<String, Integer> results = countLinesForEpisode(generateScriptsFromFile(file));
        }
        throw new UnsupportedOperationException();
    }

    public List<ScriptLine> generateScriptsFromFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<ScriptLine> lines = new LinkedList<>();
            Iterator<String> it = reader.lines().collect(Collectors.toList()).iterator();
            while (it.hasNext()){
                String text = it.next();
                System.out.println();
                System.out.println(text);
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
