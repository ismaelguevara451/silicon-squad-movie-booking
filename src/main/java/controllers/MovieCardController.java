package controllers;

import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MovieCardController {

    private static final Duration HALF_FLIP_DURATION =
            Duration.millis(340);

    private static final Duration HOVER_DURATION =
            Duration.millis(260);

    private static final Duration RETURN_DELAY =
            Duration.seconds(2);

    @FXML
    private StackPane cardRoot;

    @FXML
    private VBox frontSide;

    @FXML
    private VBox backSide;

    @FXML
    private Label movieNameLabel;

    @FXML
    private Label backMovieNameLabel;

    @FXML
    private Label descriptionLabel;

    private final PauseTransition automaticReturn =
            new PauseTransition(RETURN_DELAY);

    private final DropShadow cardShadow =
            new DropShadow();

    private boolean showingBack = false;
    private boolean mouseInside = false;
    private boolean flipping = false;
    private boolean requestedBack = false;

    private Timeline currentFlip;
    private ParallelTransition currentHoverAnimation;

    @FXML
    public void initialize() {
        configureCard();
        showFrontImmediately();

        automaticReturn.setOnFinished(event -> {
            if (!mouseInside) {
                requestSide(false);
            }
        });

        cardRoot.setOnMouseEntered(event -> {
            mouseInside = true;
            automaticReturn.stop();

            animateHoverIn();
            requestSide(true);
        });

        cardRoot.setOnMouseExited(event -> {
            mouseInside = false;

            animateHoverOut();
            automaticReturn.playFromStart();
        });
    }

    private void configureCard() {
        cardRoot.setRotationAxis(
                new Point3D(0, 1, 0)
        );

        cardRoot.setCache(true);
        frontSide.setCache(true);
        backSide.setCache(true);

        cardShadow.setRadius(15);
        cardShadow.setSpread(0.08);
        cardShadow.setOffsetX(0);
        cardShadow.setOffsetY(5);
        cardShadow.setColor(
                Color.rgb(0, 55, 110, 0.24)
        );

        cardRoot.setEffect(cardShadow);
    }

    public void setMovie(Movie movie) {
        if (movie == null) {
            return;
        }

        movieNameLabel.setText(movie.getTitle());
        backMovieNameLabel.setText(movie.getTitle());
        descriptionLabel.setText(movie.getDescription());

        showFrontImmediately();
    }

    public void setMovieName(String movieName) {
        movieNameLabel.setText(movieName);
        backMovieNameLabel.setText(movieName);
    }

    private void requestSide(boolean displayBack) {
        requestedBack = displayBack;

        if (flipping) {
            return;
        }

        if (requestedBack == showingBack) {
            return;
        }

        startFlip(requestedBack);
    }

    private void startFlip(boolean displayBack) {
        flipping = true;

        if (currentFlip != null) {
            currentFlip.stop();
        }

        double startingAngle = cardRoot.getRotate();

        Timeline closeHalf = new Timeline(
                new KeyFrame(
                        Duration.ZERO,

                        new KeyValue(
                                cardRoot.rotateProperty(),
                                startingAngle,
                                Interpolator.EASE_BOTH
                        ),

                        new KeyValue(
                                cardRoot.scaleXProperty(),
                                cardRoot.getScaleX(),
                                Interpolator.EASE_BOTH
                        ),

                        new KeyValue(
                                cardRoot.scaleYProperty(),
                                cardRoot.getScaleY(),
                                Interpolator.EASE_BOTH
                        )
                ),

                new KeyFrame(
                        HALF_FLIP_DURATION,

                        new KeyValue(
                                cardRoot.rotateProperty(),
                                90,
                                Interpolator.SPLINE(
                                        0.42,
                                        0.0,
                                        0.58,
                                        1.0
                                )
                        ),

                        new KeyValue(
                                cardRoot.scaleXProperty(),
                                1.035,
                                Interpolator.EASE_BOTH
                        ),

                        new KeyValue(
                                cardRoot.scaleYProperty(),
                                0.985,
                                Interpolator.EASE_BOTH
                        )
                )
        );

        closeHalf.setOnFinished(event -> {
            if (displayBack) {
                showBackImmediately();
                cardRoot.setRotate(-90);
            } else {
                showFrontImmediately();
                cardRoot.setRotate(90);
            }

            Timeline openHalf = new Timeline(
                    new KeyFrame(
                            Duration.ZERO,

                            new KeyValue(
                                    cardRoot.rotateProperty(),
                                    cardRoot.getRotate()
                            ),

                            new KeyValue(
                                    cardRoot.scaleXProperty(),
                                    1.035
                            ),

                            new KeyValue(
                                    cardRoot.scaleYProperty(),
                                    0.985
                            )
                    ),

                    new KeyFrame(
                            HALF_FLIP_DURATION,

                            new KeyValue(
                                    cardRoot.rotateProperty(),
                                    0,
                                    Interpolator.SPLINE(
                                            0.16,
                                            1.0,
                                            0.30,
                                            1.0
                                    )
                            ),

                            new KeyValue(
                                    cardRoot.scaleXProperty(),
                                    mouseInside ? 1.025 : 1.0,
                                    Interpolator.EASE_OUT
                            ),

                            new KeyValue(
                                    cardRoot.scaleYProperty(),
                                    mouseInside ? 1.025 : 1.0,
                                    Interpolator.EASE_OUT
                            )
                    )
            );

            openHalf.setOnFinished(finished -> {
                cardRoot.setRotate(0);
                flipping = false;

                if (requestedBack != showingBack) {
                    startFlip(requestedBack);
                    return;
                }

                if (!mouseInside && showingBack) {
                    automaticReturn.playFromStart();
                }
            });

            currentFlip = openHalf;
            openHalf.play();
        });

        currentFlip = closeHalf;
        closeHalf.play();
    }

    private void animateHoverIn() {
        stopHoverAnimation();

        ScaleTransition scale =
                new ScaleTransition(
                        HOVER_DURATION,
                        cardRoot
                );

        scale.setToX(1.025);
        scale.setToY(1.025);
        scale.setInterpolator(
                Interpolator.SPLINE(
                        0.16,
                        1.0,
                        0.30,
                        1.0
                )
        );

        TranslateTransition lift =
                new TranslateTransition(
                        HOVER_DURATION,
                        cardRoot
                );

        lift.setToY(-6);
        lift.setInterpolator(
                Interpolator.EASE_OUT
        );

        Timeline shadowAnimation =
                new Timeline(
                        new KeyFrame(
                                HOVER_DURATION,

                                new KeyValue(
                                        cardShadow.radiusProperty(),
                                        24,
                                        Interpolator.EASE_OUT
                                ),

                                new KeyValue(
                                        cardShadow.offsetYProperty(),
                                        11,
                                        Interpolator.EASE_OUT
                                ),

                                new KeyValue(
                                        cardShadow.spreadProperty(),
                                        0.13,
                                        Interpolator.EASE_OUT
                                ),

                                new KeyValue(
                                        cardShadow.colorProperty(),
                                        Color.rgb(
                                                0,
                                                75,
                                                155,
                                                0.36
                                        ),
                                        Interpolator.EASE_OUT
                                )
                        )
                );

        currentHoverAnimation =
                new ParallelTransition(
                        scale,
                        lift,
                        shadowAnimation
                );

        currentHoverAnimation.play();
    }

    private void animateHoverOut() {
        stopHoverAnimation();

        ScaleTransition scale =
                new ScaleTransition(
                        HOVER_DURATION,
                        cardRoot
                );

        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.setInterpolator(
                Interpolator.SPLINE(
                        0.22,
                        1.0,
                        0.36,
                        1.0
                )
        );

        TranslateTransition lower =
                new TranslateTransition(
                        HOVER_DURATION,
                        cardRoot
                );

        lower.setToY(0);
        lower.setInterpolator(
                Interpolator.EASE_OUT
        );

        Timeline shadowAnimation =
                new Timeline(
                        new KeyFrame(
                                HOVER_DURATION,

                                new KeyValue(
                                        cardShadow.radiusProperty(),
                                        15,
                                        Interpolator.EASE_OUT
                                ),

                                new KeyValue(
                                        cardShadow.offsetYProperty(),
                                        5,
                                        Interpolator.EASE_OUT
                                ),

                                new KeyValue(
                                        cardShadow.spreadProperty(),
                                        0.08,
                                        Interpolator.EASE_OUT
                                ),

                                new KeyValue(
                                        cardShadow.colorProperty(),
                                        Color.rgb(
                                                0,
                                                55,
                                                110,
                                                0.24
                                        ),
                                        Interpolator.EASE_OUT
                                )
                        )
                );

        currentHoverAnimation =
                new ParallelTransition(
                        scale,
                        lower,
                        shadowAnimation
                );

        currentHoverAnimation.play();
    }

    private void stopHoverAnimation() {
        if (currentHoverAnimation != null) {
            currentHoverAnimation.stop();
        }
    }

    private void showFrontImmediately() {
        showingBack = false;

        frontSide.setManaged(true);
        frontSide.setVisible(true);
        frontSide.setOpacity(1);

        backSide.setManaged(false);
        backSide.setVisible(false);
        backSide.setOpacity(0);
    }

    private void showBackImmediately() {
        showingBack = true;

        frontSide.setManaged(false);
        frontSide.setVisible(false);
        frontSide.setOpacity(0);

        backSide.setManaged(true);
        backSide.setVisible(true);
        backSide.setOpacity(1);
    }
}
