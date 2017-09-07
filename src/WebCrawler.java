import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

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

    public static final String PATH_TO_SAVE = System.getProperty("user.dir");
    public static final String SEASON_ONE_PATH = PATH_TO_SAVE + File.separator + "Season1";


    public String getScriptForEpisode(String url) {
        try {
//            long start = System.nanoTime();
            // TODO: 04-Sep-17 Try not to connect to the document to many times
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("p");
//            System.out.println((System.nanoTime() - start) / 1000000.0);
            return elements.first().text();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getTitleForEpisode(String url) {
        try {
//            long start = System.nanoTime();
            // TODO: 04-Sep-17 Try not to connect to the document to many times
            Document doc = Jsoup.connect(url).get();
            Elements title = doc.select(".header_with_cover_art-primary_info-title");
//            System.out.println((System.nanoTime() - start) / 1000000.0);
            return title.text();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveEpisodeToTextFile(String title, String lyrics) {
        saveEpisodeToFileOfFormat(title, lyrics, ".txt");
    }

    public void saveEpisodeToFileOfFormat(String title, String lyrics, String fileFormat) {
        try {
            // FIXME: 04-Sep-17 Don't hardcode this!!!
            (new File(SEASON_ONE_PATH)).mkdir();
            File file = new File(SEASON_ONE_PATH + File.separator + title + fileFormat);
            // !
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

    public void saveEpisodeFromURL(String url) {
        String title = getTitleForEpisode(url);
        System.out.println( ANSI_YELLOW + "Trying to save episode \"" + title + "\"" + ANSI_RESET);
        String lyrics = getScriptForEpisode(url);
        saveEpisodeToTextFile(title, lyrics);
    }

    public void saveLyricsForSeason(String seasonURL) {
        try {
            Document doc = Jsoup.connect(seasonURL).get();
            Elements episodesURLS = doc.select("a[href].u-display_block");
            episodesURLS.forEach(url -> saveEpisodeFromURL(url.attr("href")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
