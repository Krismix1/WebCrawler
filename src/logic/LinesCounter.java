package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 05-Sep-17.
 */
public class LinesCounter {

    private boolean fileWereModified = false; // Use this to make a persistent list of files, so that that don't get read every time
    // Use the observable pattern to modify this value?

    /**
     * Returns statistics for each character
     *
     * @param season in which season the search will be done
     * @return a map, where the keys are characters' names and values the statistics
     * for that character
     */
    public Map<String, Statistics> countLinesForSeason(String season) {
        Map<String, Statistics> seasonsStats = new HashMap<>();
        TextSearcher textSearcher = new TextSearcher();
        List<String> characterNames = WebCrawler.getCharactersName();
        if (characterNames != null) {
            for (String name : characterNames) {
                // what if the name varies from file to file
                // do different combinations of the words from the name
                // Take each word from the character name and add them to the list
                // This way, less variations will be
                // E.g. Ned Eddark Stark will result in Ned, Eddard, Stark
                // Hmm, last name will confuse things up
                List<Pair<String, Integer>> list = textSearcher.searchForWordsInSeason(name + ":", season);

//                logic.Statistics stats = new logic.Statistics(name);
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
}
