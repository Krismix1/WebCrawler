/**
 * Created by Chris on 05-Sep-17.
 */
public class ScriptLine {

    private String characterName;
    private String line;

    public ScriptLine(String characterName, String line) {
        this.characterName = characterName;
        this.line = line;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getLine() {
        return line;
    }
}
