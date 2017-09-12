import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 02-Sep-17.
 */
public class Main {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        WebCrawler webCrawler = new WebCrawler();
        String url = "https://genius.com/albums/Game-of-thrones/Season-1-scripts";
//        webCrawler.saveLyricsForSeason(url);

        TextSearcher textSearcher = new TextSearcher();
        // TODO: 05-Sep-17 Ask if capitalization matters
//        System.out.println(textSearcher.searchForWordsInAllEpisodes("at"));

        /*
        Jaime Lannister: "Summoned to court to answer for the crimes of your bannerman Gregor Clegane, the Mountain" Uh, "arrive within the fortnight or be branded an enemy of the Crown." Poor Ned Stark. Brave man, terrible judgment.
        Tywin Lannister: Attacking him was stupid.
        Tywin Lannister: Lannisters don't act like fools. Are you gonna say something clever? Go on, say something clever.
        Jaime Lannister: Catelyn Stark took my brother.
        Tywin Lannister: Why is he still alive?
        Jaime Lannister: Tyrion?
        Tywin Lannister: Ned Stark.
        Jaime Lannister: One of our men interfered, speared him through the leg before I could finish him. Tywin Lannister: Why is he still alive?
        Jaime Lannister: It wouldn't have been clean.
        Tywin Lannister: Clean...
        */

        List<ScriptLine> scriptLines = new LinkedList<>();
        scriptLines.add(new ScriptLine("Jaime Lannister", "Summoned"));
        scriptLines.add(new ScriptLine("Tywin Lannister", "Attacking"));
        scriptLines.add(new ScriptLine("Tywin Lannister", "Lannisters"));
        scriptLines.add(new ScriptLine("Jaime Lannister", "1"));
        scriptLines.add(new ScriptLine("Tywin Lannister", "2"));
        scriptLines.add(new ScriptLine("Jaime Lannister", "3"));
        scriptLines.add(new ScriptLine("Tywin Lannister", "4"));
        scriptLines.add(new ScriptLine("Jaime Lannister", "5"));
        scriptLines.add(new ScriptLine("Jaime Lannister", "6"));
        scriptLines.add(new ScriptLine("Tywin Lannister2", "7"));

        LinesCounter linesCounter = new LinesCounter();
        linesCounter.generateScriptsFromFile(new File(WebCrawler.APPLICATION_PATH +File.separator + "Season1" + File.separator + "Baelor.txt"));
    }
}
