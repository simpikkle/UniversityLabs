package com.library.controller;

import com.library.database.BookDao;
import com.library.database.ClientDao;
import com.library.database.JournalDao;
import com.library.domain.Book;
import com.library.domain.BookType;
import com.library.domain.Client;
import com.library.domain.Journal;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CrudController extends BaseController implements Initializable {

    private static State subject;

    private static State action;

    private ClientDao clientDao = new ClientDao();
    private BookDao bookDao = new BookDao();
    private JournalDao journalDao = new JournalDao();

    @FXML
    protected TableView tableView;

    @FXML
    protected Button addNewButton;

    @FXML
    protected Button editButton;

    @FXML
    protected Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (tableView != null) {
            tableView.setEditable(true);
        }

        if (action == null) {
            switch (subject) {
                case BOOK:
                    fillBooks();
                    break;
                case CLIENT:
                    fillClients();
                    break;
                case JOURNAL:
                    fillJournal();
                    break;
                default:
                    throw new IllegalStateException("State is not defined!");
            }
        }
    }

    public void openAddingDialog(ActionEvent actionEvent) {
        setAction(State.ADD);
        createNextStage(ADD_EDIT_WINDOW, action + " " + subject, addNewButton);
    }

    public void openEditingDialog(ActionEvent actionEvent) {
        setAction(State.EDIT);
        createNextStage(ADD_EDIT_WINDOW, action + " " + subject, addNewButton);
    }

    public void deleteItem(ActionEvent actionEvent) {

    }

    private void fillBooks() {
        List<Book> books = bookDao.getAll();

        TableColumn bookNameColumn = new TableColumn("Name");
        bookNameColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("bookName"));
        TableColumn amountColumn = new TableColumn("Amount");
        amountColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Integer>("amount"));
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, BookType>("bookType"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(bookNameColumn, amountColumn, typeColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(FXCollections.observableArrayList(books));
    }

    private void fillClients() {
        List<Client> clients = clientDao.getAll();

        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("firstName"));
        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("lastName"));
        TableColumn passportColumn = new TableColumn("Passport");
        passportColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("passportNumber"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, passportColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(FXCollections.observableArrayList(clients));
    }

    private void fillJournal() {
        List<Journal> journals = journalDao.getAll();

        TableColumn bookColumn = new TableColumn("Book");

        bookColumn.setCellValueFactory(cellDataFeatures -> {
            TableColumn.CellDataFeatures value = (TableColumn.CellDataFeatures)cellDataFeatures;
            Journal journal = (Journal) value.getValue();
            return Bindings.createStringBinding(() -> journal.getBook().getBookName());
        });

        TableColumn clientColumn = new TableColumn("Client");

        clientColumn.setCellValueFactory(cellDataFeatures -> {
            TableColumn.CellDataFeatures value = (TableColumn.CellDataFeatures)cellDataFeatures;
            Journal journal = (Journal) value.getValue();
            return Bindings.createStringBinding(() -> journal.getClient().getFirstName()
                    + " " + journal.getClient().getLastName());
        });

        TableColumn startDateColumn = new TableColumn("Start date");
        startDateColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Date>("startDate"));
        TableColumn endDateColumn = new TableColumn("End date");
        endDateColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Date>("endDate"));
        TableColumn returnDateColumn = new TableColumn("Return date");
        returnDateColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Date>("returnDate"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(bookColumn, clientColumn, startDateColumn, endDateColumn, returnDateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(FXCollections.observableArrayList(journals));
    }

    public static State getSubject() {
        return subject;
    }

    public static void setSubject(State stateToSet) {
        subject = stateToSet;
    }

    public static State getAction() {
        return action;
    }

    public static void setAction(State action) {
        CrudController.action = action;
    }
}
