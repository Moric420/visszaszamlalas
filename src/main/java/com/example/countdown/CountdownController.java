package com.example.countdown;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class CountdownController {
    @FXML
    private TextField dateTimeInput;

    @FXML
    private Label countdownLabel;

    private Timeline timeline;

    public void startCountdown() {
        String input = dateTimeInput.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        try {
            LocalDateTime targetTime = LocalDateTime.parse(input, formatter);
            LocalDateTime now = LocalDateTime.now();

            if (targetTime.isBefore(now)) {
                showAlert("Hiba", "A megadott időpont a múltban van.");
                return;
            }

            if (timeline != null) {
                timeline.stop();
            }

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateCountdown(targetTime)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } catch (DateTimeParseException e) {
            showAlert("Hiba", "Érvénytelen dátumformátum. Használja: YYYY.MM.DD HH:mm:ss");
        }
    }

    private void updateCountdown(LocalDateTime targetTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(targetTime)) {
            timeline.stop();
            countdownLabel.setText("Idő lejárt!");
            showAlert("Figyelmeztetés", "Az idő lejárt!");
            return;
        }

        long years = ChronoUnit.YEARS.between(now, targetTime);
        now = now.plusYears(years);

        long months = ChronoUnit.MONTHS.between(now, targetTime);
        now = now.plusMonths(months);

        long days = ChronoUnit.DAYS.between(now, targetTime);
        now = now.plusDays(days);

        long hours = ChronoUnit.HOURS.between(now, targetTime);
        now = now.plusHours(hours);

        long minutes = ChronoUnit.MINUTES.between(now, targetTime);
        now = now.plusMinutes(minutes);

        long seconds = ChronoUnit.SECONDS.between(now, targetTime);

        countdownLabel.setText(String.format("%d év %d hó %d nap %02d:%02d:%02d",
                years, months, days, hours, minutes, seconds));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
