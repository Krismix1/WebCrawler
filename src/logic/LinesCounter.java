package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Chris on 05-Sep-17.
 */
public class LinesCounter {

    private boolean fileWereModified = false; // Use this to make a persistent list of files, so that that don't get read every time
    private TextSearcher textSearcher = new TextSearcher();
    // Use the observable pattern to modify this value?

    /**
     * Returns statistics for each character
     *
     * @param season in which season the search will be done
     * @return a map, where the keys are characters' names and values the statistics
     * for that character
     */
    public Map<String, Statistics> countLinesForSeason(String season) {
        // what if the name varies from file to file
        // do different combinations of the words from the name
        // Take each word from the character name and add them to the list
        // This way, less variations will be
        // E.g. Ned Eddark Stark will result in Ned, Eddard, Stark
        // Hmm, last name will confuse things up
        List<String> characterNames = WebCrawler.getCharactersName();
        if (characterNames != null) {

            return characterNames
                    .stream()
                    .map(name -> countLinesForSeason(name, season))
                    .collect(Collectors.toMap(Statistics::getCharacterName, Function.identity()));
        }
        return null;
    }

    /**
     * Gathers statistics for the specified character in the given season.
     * Keep in mind that the characterName variable value will be used for searching, with ignorance of capitalization.
     * @param characterName the character for which searching is done. For this words the method will look in the text files
     * @param season the season in which to search
     * @return statistics about the character
     */
    public Statistics countLinesForSeason(String characterName, String season) {
        List<Pair<String, Integer>> list = textSearcher.searchForWordsInSeason(characterName + ":", season, false);

//                logic.Statistics stats = new logic.Statistics(name);
//                This approach unnecessarily creates an object
//                stats = seasonsStats.getOrDefault(name, stats);

        Statistics stats = new Statistics(characterName);

        for (Pair<String, Integer> stringIntegerPair : list) {
            stats.registerLines(stringIntegerPair.getLeft(), stringIntegerPair.getRight());
        }
        return stats;
    }
}
