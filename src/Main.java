import java.util.Map;

/**
 * Created by Chris on 02-Sep-17.
 */
public class Main {

    public static void main(String[] args) {


    }

    public static void test1() {
        WebCrawler webCrawler = new WebCrawler();
        String url = "https://genius.com/albums/Game-of-thrones/Season-1-scripts";
//        webCrawler.saveLyricsForSeason(url);

        TextSearcher textSearcher = new TextSearcher();
        // TODO: 05-Sep-17 Ask if capitalization matters
//        System.out.println(textSearcher.searchForWordsInSeason("at"));

        LinesCounter linesCounter = new LinesCounter();
        Map<String, Statistics> statisticsMap = linesCounter.countLinesForSeason("Season1");
        for (String character : statisticsMap.keySet()) {
            System.out.println(character + " has: ");
            System.out.println(statisticsMap.get(character));
            System.out.println("----------------------------------");
        }

    }

}
