package com.cms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

<<<<<<< HEAD
    @FXML
    private Pane pane;

    @FXML
    private Pane logoutIconPane;

    @FXML
    private Pane quickActionPaneDown;

    @FXML
    private Pane quickActionPaneSide;

    @FXML
=======
    @FXML 
    private Pane pane;

    @FXML 
    private Pane logoutIconPane;

    @FXML 
    private Pane quickActionPaneDown;

    @FXML 
    private Pane quickActionPaneSide;

    @FXML
>>>>>>> e70d97f2817f83cc278945188244a9df60ca8f71
    private Button evaluationButton;

    @FXML
    private Button classesButton;

    @FXML
    private Button studentButton;

    @FXML
    private Button mailButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView starIcon;

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView classesIcon;

    @FXML
    private ImageView studentIcon;

    @FXML
    private ImageView mailIcon;

    @FXML
    private ImageView settingsIcon;

    @FXML
    private ImageView profileIcon;

    private Image lightStarIcon;
    private Image darkStarIcon;

    private Image lightStudentIcon;
    private Image darkStudentIcon;

    private Image lightClassesIcon;
    private Image darkClassesIcon;

    private Image lightMailIcon;
    private Image darkMailIcon;

    private Image lightSettingsIcon;
    private Image darkSettingsIcon;

    private Image lightProfileIcon;
    private Image darkProfileIcon;

    private Map<Button, Double> originalPositions = new HashMap<>();

    @FXML
    public void initialize() {

        // Star Icon

        lightStarIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/star_light.png"));
        darkStarIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/star_dark.png"));

        starIcon.setImage(lightStarIcon);

<<<<<<< HEAD
        evaluationButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> starIcon.setImage(darkStarIcon));
        evaluationButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> starIcon.setImage(lightStarIcon));
=======
        evaluationButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event ->  starIcon.setImage(darkStarIcon));
        evaluationButton.addEventHandler(MouseEvent.MOUSE_EXITED, event ->  starIcon.setImage(lightStarIcon));
>>>>>>> e70d97f2817f83cc278945188244a9df60ca8f71

        // Classes Icon

        lightClassesIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/book_light.png"));
        darkClassesIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/book_dark.png"));

        classesIcon.setImage(lightClassesIcon);

        classesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> classesIcon.setImage(darkClassesIcon));
        classesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> classesIcon.setImage(lightClassesIcon));

        // Students Icon

        lightStudentIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/graduation_light.png"));
        darkStudentIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/graduation_dark.png"));

        studentIcon.setImage(lightStudentIcon);

        studentButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> studentIcon.setImage(darkStudentIcon));
        studentButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> studentIcon.setImage(lightStudentIcon));

        // Mail Icon

        lightMailIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/envelope_light.png"));
        darkMailIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/envelope _dark.png"));

        mailIcon.setImage(lightMailIcon);

        mailButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> mailIcon.setImage(darkMailIcon));
        mailButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> mailIcon.setImage(lightMailIcon));

        // Settings Icon

        lightSettingsIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/settings_light.png"));
        darkSettingsIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/settings_dark.png"));

        settingsIcon.setImage(lightSettingsIcon);

<<<<<<< HEAD
=======

>>>>>>> e70d97f2817f83cc278945188244a9df60ca8f71
        settingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> settingsIcon.setImage(darkSettingsIcon));
        settingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> settingsIcon.setImage(lightSettingsIcon));

        // Profile Icon

        lightProfileIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/user_light.png"));
        darkProfileIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/user_dark.png"));

        profileIcon.setImage(lightProfileIcon);

<<<<<<< HEAD
        profileButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> profileIcon.setImage(darkProfileIcon));
=======
        profileButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> profileIcon.setImage(darkProfileIcon ));
>>>>>>> e70d97f2817f83cc278945188244a9df60ca8f71
        profileButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> profileIcon.setImage(lightProfileIcon));

        setupButtonAnimation(evaluationButton);
        setupButtonAnimation(classesButton);
        setupButtonAnimation(studentButton);
        setupButtonAnimation(mailButton);
        setupButtonAnimation(settingsButton);
        setupButtonAnimation(profileButton);

<<<<<<< HEAD
        logoutButton.layoutYProperty()
                .bind(Bindings.createDoubleBinding(() -> pane.getHeight() - logoutButton.getHeight(),
                        pane.heightProperty(), logoutButton.heightProperty()));

        logoutIconPane.layoutYProperty()
                .bind(Bindings.createDoubleBinding(() -> pane.getHeight() - logoutIconPane.getHeight(),
                        pane.heightProperty(), logoutIconPane.heightProperty()));
=======
        logoutButton.layoutYProperty().bind(Bindings.createDoubleBinding(() -> 
        pane.getHeight() - logoutButton.getHeight(), pane.heightProperty(), logoutButton.heightProperty()));

        logoutIconPane.layoutYProperty().bind(Bindings.createDoubleBinding(() -> 
        pane.getHeight() - logoutIconPane.getHeight(), pane.heightProperty(), logoutIconPane.heightProperty()));
>>>>>>> e70d97f2817f83cc278945188244a9df60ca8f71

    }

    private void setupButtonAnimation(Button button) {
        originalPositions.put(button, button.getTranslateX());

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), button.lookup(".text"));
            tt.setByX(20);
            tt.play();
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), button.lookup(".text"));
            tt.setToX(originalPositions.get(button));
            tt.play();
        });
    }

    public void switchToSchedule(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/com/cms/schedule.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
