package com.siliconsquad.siliconsquadmoviebooking.components;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieBackgroundPane extends Pane {

    private static final int LOGO_COUNT = 24;

    private final Canvas canvas = new Canvas();
    private final List<FloatingLogo> floatingLogos = new ArrayList<>();

    private final Random random = new Random(42);
    private final Image logoImage;

    private double animationTime = 0.0;

    public MovieBackgroundPane() {
        setMouseTransparent(true);
        setPickOnBounds(false);

        logoImage = new Image(
                MovieBackgroundPane.class.getResource(
                        "/com/siliconsquad/siliconsquadmoviebooking/icon.png"
                ).toExternalForm()
        );

        getChildren().add(canvas);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        widthProperty().addListener(
                (observable, oldValue, newValue) -> initializeLogos()
        );

        heightProperty().addListener(
                (observable, oldValue, newValue) -> initializeLogos()
        );

        AnimationTimer timer = new AnimationTimer() {
            private long previousTime = 0;

            @Override
            public void handle(long currentTime) {
                if (previousTime == 0) {
                    previousTime = currentTime;
                    return;
                }

                double elapsedSeconds =
                        (currentTime - previousTime)
                                / 1_000_000_000.0;

                previousTime = currentTime;
                animationTime += elapsedSeconds;

                updateLogos(elapsedSeconds);
                drawAnimation();
            }
        };

        timer.start();
    }

    private void initializeLogos() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        if (!floatingLogos.isEmpty()) {
            return;
        }

        for (int i = 0; i < LOGO_COUNT; i++) {
            double size = 30 + random.nextDouble() * 34;

            double speedX =
                    randomDirection()
                            * (7 + random.nextDouble() * 12);

            double speedY =
                    randomDirection()
                            * (4 + random.nextDouble() * 9);

            double opacity =
                    0.07 + random.nextDouble() * 0.14;

            double rotationSpeed =
                    randomDirection()
                            * (2 + random.nextDouble() * 8);

            floatingLogos.add(
                    new FloatingLogo(
                            random.nextDouble() * width,
                            random.nextDouble() * height,
                            size,
                            speedX,
                            speedY,
                            opacity,
                            random.nextDouble() * 360,
                            rotationSpeed,
                            random.nextDouble() * Math.PI * 2
                    )
            );
        }
    }

    private int randomDirection() {
        return random.nextBoolean() ? 1 : -1;
    }

    private void updateLogos(double elapsedSeconds) {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        for (FloatingLogo logo : floatingLogos) {
            logo.x += logo.speedX * elapsedSeconds;
            logo.y += logo.speedY * elapsedSeconds;

            logo.rotation +=
                    logo.rotationSpeed * elapsedSeconds;

            double padding = logo.size + 20;

            if (logo.x > width + padding) {
                logo.x = -padding;
            } else if (logo.x < -padding) {
                logo.x = width + padding;
            }

            if (logo.y > height + padding) {
                logo.y = -padding;
            } else if (logo.y < -padding) {
                logo.y = height + padding;
            }
        }
    }

    private void drawAnimation() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        GraphicsContext graphics =
                canvas.getGraphicsContext2D();

        graphics.clearRect(0, 0, width, height);

        drawFloatingLogos(graphics);
        drawPerspectiveGrid(graphics, width, height);
        drawFlowingWaves(graphics, width, height);
        drawLightTrails(graphics, width, height);
        drawParticles(graphics, width, height);
    }

    private void drawFloatingLogos(
            GraphicsContext graphics
    ) {
        for (FloatingLogo logo : floatingLogos) {
            double floatingOffset =
                    Math.sin(
                            animationTime * 0.65
                                    + logo.floatPhase
                    ) * 7.0;

            double pulsingOpacity =
                    logo.opacity
                            + Math.sin(
                            animationTime * 0.8
                                    + logo.floatPhase
                    ) * 0.025;

            pulsingOpacity =
                    Math.max(
                            0.04,
                            Math.min(0.22, pulsingOpacity)
                    );

            graphics.save();

            graphics.setGlobalAlpha(pulsingOpacity);

            graphics.translate(
                    logo.x + logo.size / 2,
                    logo.y
                            + floatingOffset
                            + logo.size / 2
            );

            graphics.rotate(logo.rotation);

            graphics.drawImage(
                    logoImage,
                    -logo.size / 2,
                    -logo.size / 2,
                    logo.size,
                    logo.size
            );

            graphics.restore();
        }

        graphics.setGlobalAlpha(1.0);
    }

    private void drawPerspectiveGrid(
            GraphicsContext graphics,
            double width,
            double height
    ) {
        double vanishingX = width * 0.55;
        double vanishingY = height * 0.38;

        graphics.setStroke(
                Color.rgb(255, 255, 255, 0.13)
        );

        graphics.setLineWidth(1.0);

        for (int i = -18; i <= 18; i++) {
            double destinationX =
                    width / 2.0
                            + i * width / 13.0;

            graphics.strokeLine(
                    vanishingX,
                    vanishingY,
                    destinationX,
                    height + 100
            );
        }

        double floorHeight =
                height - vanishingY;

        for (int i = 0; i < 20; i++) {
            double progress =
                    (i * 0.065
                            + animationTime * 0.12)
                            % 1.0;

            double depth =
                    progress * progress;

            double y =
                    vanishingY
                            + depth * floorHeight;

            double expansion =
                    depth * width * 0.72;

            graphics.setStroke(
                    Color.rgb(
                            255,
                            255,
                            255,
                            0.05 + progress * 0.14
                    )
            );

            graphics.setLineWidth(
                    0.7 + progress * 1.5
            );

            graphics.strokeLine(
                    vanishingX - expansion,
                    y,
                    vanishingX + expansion,
                    y
            );
        }
    }

    private void drawFlowingWaves(
            GraphicsContext graphics,
            double width,
            double height
    ) {
        for (int line = 0; line < 7; line++) {
            double baseY =
                    height * 0.08
                            + line * height * 0.145;

            double phase =
                    animationTime
                            * (28.0 + line * 4.0)
                            + line * 42.0;

            graphics.save();

            graphics.setEffect(
                    new GaussianBlur(8.0)
            );

            graphics.setStroke(
                    Color.rgb(
                            255,
                            255,
                            255,
                            0.10
                    )
            );

            graphics.setLineWidth(6.0);

            drawWave(
                    graphics,
                    width,
                    baseY,
                    phase,
                    line
            );

            graphics.restore();

            graphics.setStroke(
                    Color.rgb(
                            255,
                            255,
                            255,
                            0.34
                    )
            );

            graphics.setLineWidth(1.35);

            drawWave(
                    graphics,
                    width,
                    baseY,
                    phase,
                    line
            );
        }
    }

    private void drawWave(
            GraphicsContext graphics,
            double width,
            double baseY,
            double phase,
            int lineNumber
    ) {
        graphics.beginPath();

        for (
                double x = -30;
                x <= width + 30;
                x += 4
        ) {
            double firstWave =
                    Math.sin(
                            (x + phase)
                                    / (
                                    75.0
                                            + lineNumber * 7.0
                            )
                    )
                            * (
                            12.0
                                    + lineNumber
                    );

            double secondWave =
                    Math.cos(
                            (x - phase * 0.62)
                                    / 130.0
                    ) * 7.0;

            double longCurve =
                    Math.sin(
                            (x + phase * 0.25)
                                    / 250.0
                    ) * 10.0;

            double y =
                    baseY
                            + firstWave
                            + secondWave
                            + longCurve;

            if (x == -30) {
                graphics.moveTo(x, y);
            } else {
                graphics.lineTo(x, y);
            }
        }

        graphics.stroke();
    }

    private void drawLightTrails(
            GraphicsContext graphics,
            double width,
            double height
    ) {
        for (int trail = 0; trail < 5; trail++) {
            double progress =
                    (
                            animationTime
                                    * (
                                    0.06
                                            + trail * 0.008
                            )
                                    + trail * 0.21
                    ) % 1.0;

            double startX =
                    -350
                            + progress
                            * (width + 700);

            double startY =
                    height
                            * (
                            0.15
                                    + trail * 0.17
                    );

            graphics.save();

            graphics.setEffect(
                    new GaussianBlur(5.0)
            );

            graphics.setStroke(
                    Color.rgb(
                            255,
                            255,
                            255,
                            0.18
                    )
            );

            graphics.setLineWidth(3.5);

            drawTrail(
                    graphics,
                    startX,
                    startY
            );

            graphics.restore();

            graphics.setStroke(
                    Color.rgb(
                            255,
                            255,
                            255,
                            0.43
                    )
            );

            graphics.setLineWidth(1.0);

            drawTrail(
                    graphics,
                    startX,
                    startY
            );
        }
    }

    private void drawTrail(
            GraphicsContext graphics,
            double startX,
            double startY
    ) {
        graphics.beginPath();
        graphics.moveTo(startX, startY);

        graphics.bezierCurveTo(
                startX + 140,
                startY - 75,
                startX + 285,
                startY + 75,
                startX + 450,
                startY - 10
        );

        graphics.stroke();
    }

    private void drawParticles(
            GraphicsContext graphics,
            double width,
            double height
    ) {
        for (int i = 0; i < 26; i++) {
            double x =
                    (
                            i * 137.0
                                    + animationTime
                                    * (
                                    8.0
                                            + i % 4
                            )
                    ) % width;

            double y =
                    (
                            i * 83.0
                                    + Math.sin(
                                    animationTime * 0.7
                                            + i
                            ) * 22.0
                    ) % height;

            double opacity =
                    0.10
                            + 0.18
                            * (
                            0.5
                                    + 0.5
                                    * Math.sin(
                                    animationTime * 1.4
                                            + i
                            )
                    );

            graphics.setFill(
                    Color.rgb(
                            255,
                            255,
                            255,
                            opacity
                    )
            );

            graphics.fillOval(
                    x,
                    y,
                    2.0 + i % 3,
                    2.0 + i % 3
            );
        }
    }

    private static class FloatingLogo {

        private double x;
        private double y;

        private final double size;
        private final double speedX;
        private final double speedY;
        private final double opacity;

        private double rotation;

        private final double rotationSpeed;
        private final double floatPhase;

        private FloatingLogo(
                double x,
                double y,
                double size,
                double speedX,
                double speedY,
                double opacity,
                double rotation,
                double rotationSpeed,
                double floatPhase
        ) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speedX = speedX;
            this.speedY = speedY;
            this.opacity = opacity;
            this.rotation = rotation;
            this.rotationSpeed = rotationSpeed;
            this.floatPhase = floatPhase;
        }
    }
}

