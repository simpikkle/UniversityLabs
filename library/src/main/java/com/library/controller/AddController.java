package com.library.controller;

import com.library.domain.Book;
import com.library.domain.BookType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController extends CrudController {

    @FXML
    private Button saveButton;
    @FXML
    private Group bookGroup;
    @FXML
    private TextField bookAmountText;
    @FXML
    private TextField bookNameText;
    @FXML
    private ComboBox bookTypePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (getAction().equals(State.ADD)) {
            if (getSubject().equals(State.BOOK)) {
                bookGroup.setVisible(true);
            }
        }
    }

    public void saveItem(ActionEvent actionEvent) {
        if (getSubject().equals(State.BOOK)) {
            saveBook();
        }
    }

    private void saveBook() {
        // TODO add check for invalid amount value
        Book book = new Book()
                .withBookName(bookNameText.getText())
                .withAmount(Integer.valueOf(bookAmountText.getText()))
                .withBookType(BookType.getByName((String) bookTypePicker.getValue()));
        bookDao.saveOrUpdate(book);
        // TODO add error message/success message
    }
}
