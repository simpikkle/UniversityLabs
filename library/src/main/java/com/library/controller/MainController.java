package com.library.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MainController extends BaseController {

    @FXML
    private ImageView bookPicture;

    @FXML
    private ImageView clientPicture;

    @FXML
    private ImageView reportPicture;

    @FXML
    private ImageView journalPicture;

    public ImageView getBookPicture() {
        return bookPicture;
    }

    public ImageView getClientPicture() {
        return clientPicture;
    }

    public ImageView getReportPicture() {
        return reportPicture;
    }

    public ImageView getJournalPicture() {
        return journalPicture;
    }
}
