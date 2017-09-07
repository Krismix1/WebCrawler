import java.util.Map;

/**
 * Created by Chris on 05-Sep-17.
 */
public class Statistics {

    //This class should throw some null pointer exceptions
    private String characterName;
    private Map<String, Integer> linesPerEpisodes;
    private int totalLines = -1;

    public Statistics(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void registerLine(String episodeName) {
        linesPerEpisodes.put(episodeName, linesPerEpisodes.getOrDefault(episodeName, 0) + 1);
    }

    public int getTotalLines() {
        if (totalLines < 0) {
            totalLines = calculateTotalOfLines();
        }
        return totalLines;
    }

    private int calculateTotalOfLines() {
        int total = 0;
        for (String episode : linesPerEpisodes.keySet()) {
            total += linesPerEpisodes.get(episode);
        }
        return total;
    }
}
