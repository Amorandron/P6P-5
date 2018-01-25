package com.janwilts.bigmovie.gui;

import com.janwilts.bigmovie.gui.controls.JFXMovieButton;
import com.janwilts.bigmovie.gui.models.Movie;
import com.janwilts.bigmovie.gui.util.APIRequester;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    public static final Background BUTTON_BACKGROUND = new Background(new BackgroundFill(Paint.valueOf("#383838"), null, null));
    public static final Paint BUTTON_TEXT_COLOR = Paint.valueOf("#808080");
    
    public static final int BUTTON_HEIGHT = 28;
    
    public static Date CURRENT_DATE = Date.from(Instant.now());
    
    private HashMap<String, List<Movie>> movieCache = new HashMap<>();
    
    @FXML
    private JFXTextField searchBar;
    
    @FXML
    private JFXButton infoButton;
    
    @FXML
    private AnchorPane infoPane;
    
    @FXML
    private Text details;
    
    public void onSearch(ActionEvent actionEvent) {
        List<Movie> movies = new ArrayList<>();
        
        String searchQuery = searchBar.getCharacters().toString();
        if (!(searchQuery.isEmpty())) {
            System.out.println("Searching: " + searchQuery);
            
            APIRequester movieRequester = new APIRequester(Movie.class);
            try {
                if (movieCache.containsKey(searchQuery)) movies = movieCache.get(searchQuery);
                else {
                    movies = movieRequester.getArrayFromAPI("/q/movie?movie=" + searchQuery);
                    movieCache.put(searchQuery, movies);
                }
                
                movies = movies.stream().filter(m -> m.getRelease_year() <= CURRENT_DATE.getYear() + 1900).collect(Collectors.toList());
                movies.sort(Comparator.comparing(Movie::getRating_votes).thenComparing(Movie::getRating).thenComparing(Movie::getRelease_year).reversed());
                movies = movies.subList(0, Math.min((int) (infoPane.getHeight() / BUTTON_HEIGHT) - 1, movies.size()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean empty = movies.isEmpty();
        
        infoButton.setDisable(empty);
        infoButton.setVisible(!empty);
        details.setVisible(!empty);
        
        if (!empty) {
            infoPane.getChildren().clear();
            for (int movieIndex = 0; movieIndex < movies.size(); movieIndex++) {
                
                JFXMovieButton button = new JFXMovieButton(movies.get(movieIndex));
                button.setButtonType(JFXButton.ButtonType.FLAT);
                button.setTextFill(BUTTON_TEXT_COLOR);
                button.setBackground(BUTTON_BACKGROUND);
                button.setLayoutY(15 + BUTTON_HEIGHT * movieIndex);
                button.setLayoutX(15);
                button.setOnAction(this::onMovieButtonClicked);
                infoPane.getChildren().add(button);
            }
        }
    }
    
    private void onMovieButtonClicked(ActionEvent event) {
        if (event.getTarget() instanceof JFXMovieButton) {
            infoPane.getChildren().clear();
            
            Movie movie = ((JFXMovieButton) event.getTarget()).getMovie();
            List<String> lines = new ArrayList<>();
            
            lines.add("Title: " + movie.getTitle());
            lines.add("Release Year: " + movie.getRelease_year());
            lines.add("");
            if (movie.getBudget() != null) {
                lines.add("Budget: " + movie.getBudget());
                lines.add("");
            }
            if (movie.getRating() > 0) lines.add("IMDB Rating: " + movie.getRating() + " (" + movie.getRating_votes() + " votes)");
            if (movie.getMpaa_rating() != null) lines.add("MPAA Rating: " + movie.getMpaa_rating() + " (" + movie.getMpaa_reason() + ")");
            
            details.setText(String.join("\n", lines.toArray(new String[0])));
            infoPane.getChildren().add(details);
        }
    }
    
    public void initialize() {
        infoButton.disableProperty().addListener(l -> infoPane.setVisible(!infoButton.isDisable()));
        
        infoButton.setVisible(false);
    }
}
