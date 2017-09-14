package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    /**
     * Finds and returns all the file in the specified path, excluding directories.
     * @param path where to search
     * @return all files, excluding directories
     */
    public File[] findAllFilesAtPath(String path) {
        File[] episodes = (new File(path).listFiles());
        if (episodes != null) {
            return (Stream.of(episodes)
                    .filter(File::isFile) // filter whether it is a directory or a file
                    .collect(Collectors.toList()).toArray(new File[0]));
        }
        return null;
    }

    /**
     * Finds how many times the words appeared in each episode
     * @param words words to search for
     * @return list which holds pairs of the episode name and the number of occurrences
     */
    public List<Pair<String, Integer>> searchForWordsInSeason(String words, String season) {
        File[] episodes = findAllFilesAtPath(WebCrawler.APPLICATION_PATH + File.separator + season);

        List<Pair<String, Integer>> occurrences = new LinkedList<>();
        for (File file : episodes) {
            if (file.getName().contains(".txt")) {
                String fileName = file.getName();
                occurrences.add(new Pair<>(fileName.replace(".txt", ""), searchForWordsInEpisode(words.toLowerCase(), fileName, season)));
            }
        }
        return occurrences;
    }

    /**
     * Counts how many times the given words appear in the episode of the selected season
     * @param words the sequence of words that are searched
     * @param episodeTitle the title of the episode
     * @param season the season number
     * @return the number of occurrences of the words
     */
    public int searchForWordsInEpisode(String words, String episodeTitle, String season) {
        try {
            File file = new File(WebCrawler.APPLICATION_PATH + File.separator + season + File.separator + episodeTitle);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            Iterator<String> it = lines.iterator();
            if (it.hasNext()) {
                // Get the title from the file
                // title can also be episodeTitle, but this might change if the saving to file changes
                it.next();
            }
            int totalOccurrences = 0;
            while (it.hasNext()) {
                totalOccurrences += countWordsInString(words.toLowerCase(), it.next().toLowerCase());
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
