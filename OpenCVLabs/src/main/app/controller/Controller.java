package main.app.controller;

import javafx.fxml.FXML;

public class Controller extends BaseWorkflowController {

    private CameraController cameraController;

    @FXML
    protected void onGetStartedClick() throws Exception {
        startBlock.setVisible(false);
        workBlock.setVisible(true);
        cameraController = new CameraController(histogram, nPeople, dPeople);
        cameraController.startCamera(cameraView);
        cameraController.initializeFaceDetection();
    }

    @FXML
    public void changeColor() {
        cameraController.setIsGray(grayCheckBox.isSelected());
    }

    public void showLogo() {
        cameraController.setLogo(logoCheckBox.isSelected());
        updateGrayScale(logoCheckBox.isSelected());
    }

    public void showHistogram() {
        cameraController.setHistogram(histogramCheckBox.isSelected());
        if (histogramCheckBox.isSelected()) {
            histogram.setVisible(true);
        } else {
            histogram.setVisible(false);
        }
    }

    public void detectFaces() {
        cameraController.setDetect(faceCheckBox.isSelected());
        updateGrayScale(faceCheckBox.isSelected());
        faceBlock.setVisible(faceCheckBox.isSelected());
    }

    private void updateGrayScale(boolean selected) {
        if (selected) {
            cameraController.setIsGray(false);
            grayCheckBox.setSelected(false);
            grayCheckBox.setDisable(true);
        } else {
            grayCheckBox.setDisable(false);
        }
    }

    public void takeScreenshot() {
        cameraController.setLogo(false);
        cameraController.setDetect(false);
        cameraController.takeScreenShot();
        cameraController.setDetect(faceCheckBox.isSelected());
        cameraController.setLogo(logoCheckBox.isSelected());
    }
}
