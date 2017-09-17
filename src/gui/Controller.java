package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private ChoiceBox<String> seasonChoice;

    @FXML
    private void downloadSeason() {
        String season = seasonChoice.getValue().replace(' ', '-');
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.saveLyricsForSeason("https://genius.com/albums/Game-of-thrones/" + season + "-scripts");
    }

    @FXML
    private TextField searchForTextField;
    @FXML
    private TableView<Pair<String, Integer>> occurrencesTable;
    @FXML
    private CheckBox capitalizationCheckBox;

    @FXML
    private void searchText() {
        String textToSearch = searchForTextField.getText();
        TextSearcher textSearcher = new TextSearcher();
        // TODO: 14-Sep-17 Put a choice box for season
        List<Pair<String, Integer>> occurrences = textSearcher.searchForWordsInSeason(textToSearch, "Season1", capitalizationCheckBox.isSelected());
        occurrencesTable.setItems(FXCollections.observableList(occurrences));
    }

    @FXML
    private ChoiceBox<String> characterNamesChoice;
    @FXML
    private TableView<Statistics> statisticsTableView;

    @FXML
    private void gatherStatistics() {
        String character = characterNamesChoice.getValue();
        LinesCounter linesCounter = new LinesCounter();
        if (character.equalsIgnoreCase("All")) {
            System.out.println("Debug: Calculating stats");
            Map<String, Statistics> stats = linesCounter.countLinesForSeason("Season1");

            System.out.println("Debug: Done calculating stats");
            statisticsTableView.setItems(FXCollections.observableList(stats.values().stream().collect(Collectors.toList())));
        } else {
//            throw new UnsupportedOperationException("Unable to search for 1 character only");
            Statistics results = linesCounter.countLinesForSeason(character, "Season1");
            statisticsTableView.setItems(FXCollections.observableArrayList(results));
        }
    }

    @FXML
    private void initialize() {
        String[] seasons = new String[7];
        for (int i = 0; i < seasons.length; i++) {
            seasons[i] = "Season " + (i + 1);
        }
        seasonChoice.setItems(FXCollections.observableArrayList(seasons));
        seasonChoice.getSelectionModel().selectFirst();

        occurrencesTable.setEditable(false);
        ObservableList<TableColumn<Pair<String, Integer>, ?>> columns = occurrencesTable.getColumns();
        columns.get(0).setCellValueFactory(new PropertyValueFactory<>("left"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<>("right"));

        List<String> characters = WebCrawler.getCharactersName();
        characters.add(0, "All");
        characterNamesChoice.setItems(FXCollections.observableList(characters));
        characterNamesChoice.getSelectionModel().selectFirst();

        statisticsTableView.setEditable(false);
        ObservableList<TableColumn<Statistics, ?>> statisticsTableColumns = statisticsTableView.getColumns();
        statisticsTableColumns.get(0).setCellValueFactory(new PropertyValueFactory<>("characterName"));
        statisticsTableColumns.get(1).setCellValueFactory(new PropertyValueFactory<>("totalLines"));
    }
}
