package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainController {
    private int[] weights = new int[10]; // 10 rooms ke liye weight store karne ka array

    @FXML private Label weightLabel1, weightLabel2, weightLabel3, weightLabel4, weightLabel5,
            weightLabel6, weightLabel7, weightLabel8, weightLabel9, weightLabel10;

    @FXML private Circle light1, light2, light3, light4, light5,
            light6, light7, light8, light9, light10;

    @FXML private Button myButton1, myButton2, myButton3, myButton4, myButton5,
            myButton6, myButton7, myButton8, myButton9, myButton10;

    @FXML private Button cleanButton1, cleanButton2, cleanButton3, cleanButton4, cleanButton5,
            cleanButton6, cleanButton7, cleanButton8, cleanButton9, cleanButton10;

    private final int MAX_WEIGHT = 100; // Max weight ko define karna zaroori hai

    @FXML
    private void handleButtonClick1() { updateWeight(0, weightLabel1, light1); }
    @FXML
    private void handleButtonClick2() { updateWeight(1, weightLabel2, light2); }
    @FXML
    private void handleButtonClick3() { updateWeight(2, weightLabel3, light3); }
    @FXML
    private void handleButtonClick4() { updateWeight(3, weightLabel4, light4); }
    @FXML
    private void handleButtonClick5() { updateWeight(4, weightLabel5, light5); }
    @FXML
    private void handleButtonClick6() { updateWeight(5, weightLabel6, light6); }
    @FXML
    private void handleButtonClick7() { updateWeight(6, weightLabel7, light7); }
    @FXML
    private void handleButtonClick8() { updateWeight(7, weightLabel8, light8); }
    @FXML
    private void handleButtonClick9() { updateWeight(8, weightLabel9, light9); }
    @FXML
    private void handleButtonClick10() { updateWeight(9, weightLabel10, light10); }

    @FXML
    private void handleCleanClick1() { resetWeight(0, weightLabel1, light1); }
    @FXML
    private void handleCleanClick2() { resetWeight(1, weightLabel2, light2); }
    @FXML
    private void handleCleanClick3() { resetWeight(2, weightLabel3, light3); }
    @FXML
    private void handleCleanClick4() { resetWeight(3, weightLabel4, light4); }
    @FXML
    private void handleCleanClick5() { resetWeight(4, weightLabel5, light5); }
    @FXML
    private void handleCleanClick6() { resetWeight(5, weightLabel6, light6); }
    @FXML
    private void handleCleanClick7() { resetWeight(6, weightLabel7, light7); }
    @FXML
    private void handleCleanClick8() { resetWeight(7, weightLabel8, light8); }
    @FXML
    private void handleCleanClick9() { resetWeight(8, weightLabel9, light9); }
    @FXML
    private void handleCleanClick10() { resetWeight(9, weightLabel10, light10); }

    private void updateWeight(int index, Label weightLabel, Circle light) {
        if (weights[index] < MAX_WEIGHT) {
            weights[index] += 10; // Har click pe 10 kg badh raha hai
        }
        weightLabel.setText("Weight: " + weights[index] + " Kg");
        updateLightColor(weights[index], light);
    }

    private void resetWeight(int index, Label weightLabel, Circle light) {
        weights[index] = 0;
        weightLabel.setText("Weight: 0 Kg");
        updateLightColor(0, light);
    }

    private void updateLightColor(int weight, Circle light) {
        double percentage = (double) weight / MAX_WEIGHT * 100;
        if (percentage <= 50) {
            light.setFill(Color.GREEN);
        } else if (percentage <= 70) {
            light.setFill(Color.YELLOW);
        } else {
            light.setFill(Color.RED);
        }
    }
}


//    @FXML
//    private void handleButtonClick1() {
//        System.out.println("Weight Added to Room 1");
//    }
//    @FXML
//    private void handleButtonClick2() {
//        System.out.println("Weight Added to Room 2");
//    }
//    @FXML
//    private void handleButtonClick3() {
//        System.out.println("Weight Added to Room 3");
//    }
//    @FXML
//    private void handleButtonClick4() {
//        System.out.println("Weight Added to Room 4");
//    }
//    @FXML
//    private void handleButtonClick5() {
//        System.out.println("Weight Added to Room 5");
//    }
//    @FXML
//    private void handleButtonClick6() {
//        System.out.println("Weight Added to Room 6");
//    }
//    @FXML
//    private void handleButtonClick7() {
//        System.out.println("Weight Added to Room 7");
//    }
//    @FXML
//    private void handleButtonClick8() {
//        System.out.println("Weight Added to Room 8");
//    }
//    @FXML
//    private void handleButtonClick9() {
//        System.out.println("Weight Added to Room 9");
//    }
//    @FXML
//    private void handleButtonClick10() {
//        System.out.println("Weight Added to Room 10");
//    }


//}
