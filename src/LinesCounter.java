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

    private boolean fileWereModified = false; // Use this to make a persistent list of files, so that that don't get read every time
    // Use the observable pattern to modify this value?

    /**
     * Returns statistics for each character
     *
     * @param season
     * @return a map, where the keys are characters' names and values the statistics
     * for that character
     */
    public Map<String, Statistics> countLinesForSeason(String season) {
        Map<String, Statistics> seasonsStats = new HashMap<>();
        TextSearcher textSearcher = new TextSearcher();
//        File[] files = textSearcher.findAllFilesAtPath(WebCrawler.APPLICATION_PATH + File.separator + season);
//        for (File file : files) {
//            Map<String, Integer> results = countLinesForEpisode(generateScriptsFromFile(file));
//            for (String charName : results.keySet()) {
//                Statistics stats = new Statistics(charName);
//
//                stats = seasonsStats.getOrDefault(charName, stats);
//                stats.registerLines(file.getName(), results.get(charName));
//            }
//        }
        List<String> characterNames = WebCrawler.getCharacterNames();
        if (characterNames != null) {
            for (String name : characterNames) {
                // what if the name varies from file to file
                // do different combinations of the words from the name
                List<Pair<String, Integer>> list = textSearcher.searchForWordsInSeason(name + ":", season);

//                Statistics stats = new Statistics(name);
//                This approach unnecessarily creates an object
//                stats = seasonsStats.getOrDefault(name, stats);

                Statistics stats = seasonsStats.get(name);
                if (stats == null) {
                    stats = new Statistics(name);
                    seasonsStats.put(name, stats);
                }

                for (Pair<String, Integer> stringIntegerPair : list) {
                    stats.registerLines(stringIntegerPair.getLeft(), stringIntegerPair.getRight());
                }
            }
        }
        return seasonsStats;
    }

    /**
     * Calculates and returns the number of lines for each character
     *
     * @param scriptLines
     * @return a map, where the keys are characters' names and the values represent the number of lines the character
     * has in the episode
     */
    private Map<String, Integer> countLinesForEpisode(List<ScriptLine> scriptLines) {
        Map<String, Integer> counter = new HashMap<>();
        for (ScriptLine line : scriptLines) {
            counter.put(line.getCharacterName(), counter.getOrDefault(line.getCharacterName(), 0) + 1);
        }
        return counter;
    }

    private List<ScriptLine> generateScriptsFromFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<ScriptLine> lines = new LinkedList<>();
            Iterator<String> it = reader.lines().collect(Collectors.toList()).iterator();
            while (it.hasNext()) {
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
