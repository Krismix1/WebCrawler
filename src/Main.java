import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/sample.fxml"));
        primaryStage.setTitle("Web Crawler");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void test1() {
//        logic.WebCrawler webCrawler = new logic.WebCrawler();
//        String url = "https://genius.com/albums/Game-of-thrones/Season-1-scripts";
//        webCrawler.saveLyricsForSeason(url);
//
//        logic.TextSearcher textSearcher = new logic.TextSearcher();
//        // TODO: 05-Sep-17 Ask if capitalization matters
//        System.out.println(textSearcher.searchForWordsInSeason("at"));
//
//        logic.LinesCounter linesCounter = new logic.LinesCounter();
//        Map<String, logic.Statistics> statisticsMap = linesCounter.countLinesForSeason("Season1");
//        for (String character : statisticsMap.keySet()) {
//            System.out.println(character + " has: ");
//            System.out.println(statisticsMap.get(character));
//            System.out.println("----------------------------------");
//        }
    }

    public static void dat16j(String[] args) {

        try {
//            URL myUrl = new URL("https://genius.com/albums/Game-of-thrones/Season-1-scripts/");
            URL myUrl = new URL("https://genius.com/Game-of-thrones-winter-is-coming-annotated/");
            URLConnection conn = myUrl.openConnection();
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
            conn.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
