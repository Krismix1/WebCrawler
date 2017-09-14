package logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chris on 03-Sep-17.
 */
public class WebCrawler {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * The path of this application
     */
    public static final String APPLICATION_PATH = System.getProperty("user.dir");


    public String getScriptForEpisode(String url) {
        try {
            // TODO: 04-Sep-17 Try not to connect to the document to many times
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("p");
            return elements.first().text();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getTitleForEpisode(String url) {
        try {
            // TODO: 04-Sep-17 Try not to connect to the document to many times
            Document doc = Jsoup.connect(url).get();
            Elements title = doc.select(".header_with_cover_art-primary_info-title");
            return title.text();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveEpisodeToTextFile(String title, String lyrics, String pathToSave) {
        saveEpisodeToFileOfFormat(title, lyrics, pathToSave, ".txt");
    }

    /**
     * Saves the episode to a file of desired format.
     *
     * @param title      the title of the episode, which is written on the first line in the file and also used as the name of the file
     * @param lyrics     the lyrics of the episode
     * @param pathToSave the directory in which the episode will be saved. This directory should already exists.
     * @param fileFormat the format of the file
     */
    public void saveEpisodeToFileOfFormat(String title, String lyrics, String pathToSave, String fileFormat) {
        try {
            File file = new File(pathToSave + File.separator + title + fileFormat);
            BufferedWriter writer = new BufferedWriter(new PrintWriter(file));
            writer.write(title);
            writer.newLine();
            writer.write(lyrics);
            writer.close();
            System.out.println(ANSI_GREEN + "Successfully saved episode \"" + title + "\"" + ANSI_RESET);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ANSI_RED + "Failed to save episode \"" + title + "\"" + ANSI_RESET);
        }
    }

    /**
     * Gets the title and episode script from the given URL and saves it to the path + title + .txt
     *
     * @param url        the URL of the episode
     * @param pathToSave the directory in which to save. This directory should already exists
     */
    public void saveEpisodeFromURL(String url, String pathToSave) {
        String title = getTitleForEpisode(url);
        System.out.println(ANSI_YELLOW + "Trying to save episode \"" + title + "\"" + ANSI_RESET);
        String lyrics = getScriptForEpisode(url);
        saveEpisodeToTextFile(title, lyrics, pathToSave);
    }

    /**
     * Saves the lyrics of all episodes in the season at the URL.
     * It will create a directory under the application folder, if it doesn't exist.
     *
     * @param seasonURL the URL of the season
     */
    // FIXME: 14-Sep-17 Maybe put the 'path' as a method parameter and let the client choose where to save?
    public void saveLyricsForSeason(String seasonURL) {
        final String SEASON = "Season";
        char seasonNumber = seasonURL.charAt(seasonURL.indexOf(SEASON) + SEASON.length() + 1);
        String path = APPLICATION_PATH + File.separator + SEASON + seasonNumber;
        (new File(path)).mkdir(); // FIXME: 14-Sep-17 Maybe put this line in saveEpisodeToFileOfFormat method?

        List<String> episodesURLS = getEpisodesURLS(seasonURL);
        episodesURLS.forEach(url -> saveEpisodeFromURL(url, path));
    }

    public List<String> getEpisodesURLS(String seasonURL) {
        try {
            Document doc = Jsoup.connect(seasonURL).get();
            Elements episodesURLS = doc.select("a[href].u-display_block");
            return episodesURLS
                    .stream()
                    .map(element -> element.attr("href"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the names of the characters from <a href="https://en.wikipedia.org/wiki/List_of_Game_of_Thrones_characters">wikipedia</a>.
     *
     * @return list of the characters
     */
    public static List<String> getCharactersName() {
        try {
            List<String> characterNames = new LinkedList<>();
            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_Game_of_Thrones_characters").get();
            for (Element table : doc.select("table.wikitable")) { //select the first table.
                Elements rows = table.select("tr");

                for (int i = 2; i < rows.size(); i++) { //first row is the col names so skip it, second row is the number of the season
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    characterNames.add(cols.get(1).text());
                }
            }
            return characterNames;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
