import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 05-Sep-17.
 */
public class Statistics {

    //This class should throw some null pointer exceptions
    private String characterName;
    private Map<String, Integer> linesPerEpisodes;
    private int totalLines = 0;

    public Statistics(String characterName) {
        this.characterName = characterName;
        linesPerEpisodes = new HashMap<>();
    }

    public String getCharacterName() {
        return characterName;
    }

    public void registerLines(String episodeName, int amount) {
        linesPerEpisodes.put(episodeName, linesPerEpisodes.getOrDefault(episodeName, 0) + amount);
        totalLines += amount;
    }

    public int getTotalLines() {
        return totalLines;
    }

    @Override
    public String toString() {
        return linesPerEpisodes.toString();
    }
}
