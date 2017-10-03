package com.library.controller;

import com.library.domain.Book;
import com.library.domain.BookType;
import com.library.domain.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditController extends CrudController {

    private static Integer itemId;

    @FXML
    private Label savedLabel;
    @FXML
    private Button saveButton;

    @FXML
    private Group bookGroup;
    @FXML
    private Group clientGroup;
    @FXML
    private Group journalGroup;

    @FXML
    private TextField bookAmountText;
    @FXML
    private TextField bookNameText;
    @FXML
    private ComboBox bookTypePicker;

    @FXML
    private TextField clientFirstName;
    @FXML
    private TextField clientLastName;
    @FXML
    private TextField clientPassport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (getSubject().equals(State.BOOK)) {
            bookGroup.setVisible(true);
            if (getAction() == State.EDIT) {
                fillBookInfo();
            }
        } else if (getSubject().equals(State.CLIENT)) {
            clientGroup.setVisible(true);
            if (getAction() == State.EDIT) {
                fillClientInfo();
            }
        }

        savedLabel.setVisible(false);
    }

    public void saveItem(ActionEvent actionEvent) {
        switch (getSubject()) {
            case BOOK:
                saveBook();
                break;
            case CLIENT:
                saveClient();
                break;
        }
    }

    private void saveClient() {
        try {
            Client client = new Client()
                    .withFirstName(clientFirstName.getText())
                    .withLastName(clientLastName.getText())
                    .withPassportNumber(clientPassport.getText());
            if (itemId != null) {
                client.setId(itemId);
            }
            clientDao.saveOrUpdate(client);
            savedLabel.setVisible(true);
            clientFirstName.setText("");
            clientLastName.setText("");
            clientPassport.setText("");
        } catch (Exception e) {
            showError(e);
        }
    }

    private void saveBook() {
        // TODO add check for invalid values (empty or strings for amount)
        // TODO check for duplicates
        try {
            Book book = new Book()
                    .withBookName(bookNameText.getText())
                    .withAmount(Integer.valueOf(bookAmountText.getText()))
                    .withBookType(BookType.getByName((String) bookTypePicker.getValue()));
            if (itemId != null) {
                book.setId(itemId);
            }
            bookDao.saveOrUpdate(book);
            savedLabel.setVisible(true);
            bookNameText.setText("");
            bookAmountText.setText("");
            bookTypePicker.setValue("Usual");
        } catch (Exception e) {
            showError(e);
        }
    }

    private void fillBookInfo() {
        Book book = bookDao.getById(itemId);
        bookNameText.setText(book.getBookName());
        bookAmountText.setText(String.valueOf(book.getAmount()));
        bookTypePicker.setValue(book.getBookType().name());
    }

    private void fillClientInfo() {
        Client client = clientDao.getById(itemId);
        clientFirstName.setText(client.getFirstName());
        clientLastName.setText(client.getLastName());
        clientPassport.setText(client.getPassportNumber());
    }

    public static Integer getItemId() {
        return itemId;
    }

    public static void setItemId(Integer id) {
        itemId = id;
    }

    private void showError(Exception e) {
        savedLabel.setText("Error happened: " + e.getMessage());
        savedLabel.setStyle("-fx-background-color: #ffafb1;");
        savedLabel.setVisible(true);
    }
}
