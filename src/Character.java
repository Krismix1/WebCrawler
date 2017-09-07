import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Chris on 05-Sep-17.
 */
public class Character {
    private List<String> lines;

    public Character() {
        lines = new LinkedList<>();
    }

    public Stream<String> getLines() {
        return lines.stream();
    }

    public boolean addLine(String line) {
        return lines.add(line);
    }

    public int getNumberOfLines() {
        return lines.size();
    }
}
