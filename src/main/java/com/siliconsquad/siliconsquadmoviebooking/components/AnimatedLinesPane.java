package com.siliconsquad.siliconsquadmoviebooking.components;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;

public class AnimatedLinesPane extends Pane {

    private static final double RIGHT_PANEL_WIDTH = 354.0;

    private final Canvas canvas = new Canvas();
    private double animationTime = 0.0;

    public AnimatedLinesPane() {
        setMouseTransparent(true);
        setPickOnBounds(false);

        getChildren().add(canvas);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        widthProperty().addListener((observable, oldValue, newValue) -> draw());
        heightProperty().addListener((observable, oldValue, newValue) -> draw());

        AnimationTimer timer = new AnimationTimer() {
            private long previousTime;

            @Override
            public void handle(long now) {
                if (previousTime == 0) {
                    previousTime = now;
                    return;
                }

                double seconds = (now - previousTime) / 1_000_000_000.0;
                previousTime = now;

                animationTime += seconds;
                draw();
            }
        };

        timer.start();
    }

    private void draw() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0, 0, width, height);

        double dividerX = Math.max(0, width - RIGHT_PANEL_WIDTH);

        drawPerspectiveGrid(graphics, width, height, dividerX);
        drawFlowingLines(graphics, width, height, dividerX);
        drawLightTrails(graphics, width, height, dividerX);
    }

    private Paint createDualColorGradient(
            double width,
            double dividerX,
            double leftOpacity,
            double rightOpacity
    ) {
        double transitionSize = 65.0;

        double leftStop =
                Math.max(0.0, (dividerX - transitionSize) / width);

        double centerStop =
                Math.max(0.0, Math.min(1.0, dividerX / width));

        double rightStop =
                Math.min(1.0, (dividerX + transitionSize) / width);

        return new LinearGradient(
                0,
                0,
                width,
                0,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(255, 255, 255, leftOpacity)),
                new Stop(leftStop, Color.rgb(255, 255, 255, leftOpacity)),
                new Stop(centerStop, Color.rgb(120, 190, 255, 0.20)),
                new Stop(rightStop, Color.rgb(30, 144, 255, rightOpacity)),
                new Stop(1.0, Color.rgb(30, 144, 255, rightOpacity))
        );
    }

    private void drawPerspectiveGrid(
            GraphicsContext graphics,
            double width,
            double height,
            double dividerX
    ) {
        double vanishingX = width * 0.58;
        double vanishingY = height * 0.39;

        Paint gridPaint =
                createDualColorGradient(width, dividerX, 0.17, 0.17);

        graphics.setStroke(gridPaint);
        graphics.setLineWidth(1.0);

        /*
         * Perspective lines spread from one vanishing point across the
         * full page, so the animation visually connects both sections.
         */
        for (int i = -15; i <= 15; i++) {
            double destinationX =
                    width / 2.0 + i * width / 12.0;

            graphics.strokeLine(
                    vanishingX,
                    vanishingY,
                    destinationX,
                    height + 60
            );
        }

        /*
         * Moving depth lines become farther apart near the bottom,
         * producing a stronger 3D floor effect.
         */
        double lowerArea = height - vanishingY;

        for (int i = 0; i < 18; i++) {
            double progress =
                    ((i * 0.075 + animationTime * 0.11) % 1.0);

            double depth = progress * progress;
            double y = vanishingY + depth * lowerArea;

            double sideExpansion = depth * width * 0.60;

            graphics.setLineWidth(0.7 + progress * 1.3);

            graphics.strokeLine(
                    vanishingX - sideExpansion,
                    y,
                    vanishingX + sideExpansion,
                    y
            );
        }
    }

    private void drawFlowingLines(
            GraphicsContext graphics,
            double width,
            double height,
            double dividerX
    ) {
        Paint glowPaint =
                createDualColorGradient(width, dividerX, 0.13, 0.12);

        Paint corePaint =
                createDualColorGradient(width, dividerX, 0.47, 0.33);

        for (int line = 0; line < 7; line++) {
            double baseY =
                    height * 0.10 + line * height * 0.135;

            double speed =
                    24.0 + line * 5.0;

            double phase =
                    animationTime * speed + line * 38.0;

            /*
             * First pass creates a soft glow behind each moving line.
             */
            graphics.save();
            graphics.setEffect(new GaussianBlur(7.0));
            graphics.setStroke(glowPaint);
            graphics.setLineWidth(5.5);
            drawWave(graphics, width, baseY, phase, line);
            graphics.restore();

            /*
             * Second pass draws the clear center of the same line.
             */
            graphics.setStroke(corePaint);
            graphics.setLineWidth(1.35);
            drawWave(graphics, width, baseY, phase, line);
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

        for (double x = -20; x <= width + 20; x += 4) {
            double mainWave =
                    Math.sin((x + phase) / (70.0 + lineNumber * 8.0))
                            * (13.0 + lineNumber * 1.3);

            double detailWave =
                    Math.cos((x - phase * 0.65) / 125.0)
                            * 7.0;

            double curve =
                    Math.sin((x + phase * 0.30) / 240.0)
                            * 10.0;

            double y = baseY + mainWave + detailWave + curve;

            if (x <= -20) {
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
            double height,
            double dividerX
    ) {
        Paint trailPaint =
                createDualColorGradient(width, dividerX, 0.30, 0.22);

        graphics.setStroke(trailPaint);

        for (int trail = 0; trail < 4; trail++) {
            double progress =
                    (animationTime * (0.055 + trail * 0.008)
                            + trail * 0.24) % 1.0;

            double startX = -220 + progress * (width + 440);
            double startY =
                    height * (0.20 + trail * 0.18);

            graphics.setLineWidth(1.2 + trail * 0.25);

            graphics.beginPath();
            graphics.moveTo(startX, startY);

            graphics.bezierCurveTo(
                    startX + 130,
                    startY - 70,
                    startX + 250,
                    startY + 75,
                    startX + 390,
                    startY - 15
            );

            graphics.stroke();
        }
    }
}
