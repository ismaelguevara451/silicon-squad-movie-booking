package controllers;

import com.siliconsquad.siliconsquadmoviebooking.models.Movie;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MovieCardController {

    @FXML
    private ImageView posterImageView;

    @FXML
    private Label movieNameLabel;

    @FXML
    private Label descriptionLabel;

    private static final Map<String,String> POSTERS = new HashMap<>();

    static{

        POSTERS.put("Matrix","matrix.jpg");
        POSTERS.put("Avengers","avengers.jpg");
        POSTERS.put("Inception","inception.jpg");
        POSTERS.put("The Matrix Reloaded","matrix-reloaded.jpg");
        POSTERS.put("Forrest Gump","forrest-gump.jpg");
        POSTERS.put("Parasite","parasite.jpg");
        POSTERS.put("Terminator","terminator.jpg");
        POSTERS.put("Gladiator","gladiator.jpg");
        POSTERS.put("Jurassic Park","jurassic-park.jpg");
        POSTERS.put("Taxi Driver","taxi-driver.jpg");
        POSTERS.put("Tarzan","tarzan.jpg");
        POSTERS.put("Rush Hour","rush-hour.jpg");
        POSTERS.put("Interstellar","interstellar.jpg");
        POSTERS.put("The Dark Knight","dark-knight.jpg");
        POSTERS.put("Titanic","titanic.jpg");
        POSTERS.put("Pulp Fiction","pulp-fiction.jpg");
        POSTERS.put("The Godfather","godfather.jpg");
        POSTERS.put("Goodfellas","goodfellas.jpg");
    }

    public void setMovie(Movie movie){

        movieNameLabel.setText(movie.getTitle());
        descriptionLabel.setText(movie.getDescription());

        String poster = POSTERS.get(movie.getTitle());

        if(poster==null){
            useFallbackImage();
            return;
        }

        URL url = MovieCardController.class.getResource(
                "/com/siliconsquad/siliconsquadmoviebooking/posters/" + poster
        );

        if(url==null){
            useFallbackImage();
            return;
        }

        try{

            Image img = new Image(url.toExternalForm(),false);

            if(img.isError()){
                useFallbackImage();
            }else{
                posterImageView.setImage(img);
            }

        }catch(Exception e){
            useFallbackImage();
        }
    }

    private void useFallbackImage(){

        URL fallback =
                MovieCardController.class.getResource(
                        "/com/siliconsquad/siliconsquadmoviebooking/icon.png"
                );

        if(fallback!=null){

            posterImageView.setImage(
                    new Image(
                            fallback.toExternalForm(),
                            false
                    )
            );
        }
    }
}