package com.library.controller;

import com.library.ReportWriter;
import com.library.database.JournalDao;
import com.library.domain.Journal;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ReportsController extends BaseController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(ReportsController.class);

    private static final String OUT_OF_DATE_REPORT = "outOfDateReport";
    private static final String NOT_RETURNED_REPORT = "notReturnedBooksReport";
    private static final String RETURNED_IN_RANGE_REPORT = "returnedInRangeReport";
    private static final String CVS_EXT = ".csv";

    @FXML
    private ComboBox reportPicker;

    @FXML
    private Pane rangeReport;

    @FXML
    private Pane outOfDate;

    @FXML
    private Pane didntReturn;

    @FXML
    private CheckBox dateFilter;

    @FXML
    private DatePicker rangePicker;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    private static Reports currentReportType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Reports> reportKinds = Arrays.asList(Reports.OUT_OF_DATE, Reports.RETURN_IN_RANGE, Reports.NOT_RETURNED);
        reportPicker.setItems(FXCollections.observableArrayList(reportKinds));
    }

    public void showReportPanel() {
        currentReportType = (Reports) reportPicker.getSelectionModel().getSelectedItem();
        switch (currentReportType) {
            case OUT_OF_DATE:
                outOfDate.setVisible(true);
                rangeReport.setVisible(false);
                didntReturn.setVisible(false);
                break;
            case NOT_RETURNED:
                didntReturn.setVisible(true);
                outOfDate.setVisible(false);
                rangeReport.setVisible(false);
                break;
            case RETURN_IN_RANGE:
                rangeReport.setVisible(true);
                didntReturn.setVisible(false);
                outOfDate.setVisible(false);
                break;
            default:
                rangeReport.setVisible(false);
                didntReturn.setVisible(false);
                outOfDate.setVisible(false);
        }
    }

    public void disableDataPicker() {
        if (dateFilter.isSelected()) {
            rangePicker.setDisable(false);
        } else {
            rangePicker.setDisable(true);
        }
    }

    public void createReport() {
        String filename;
        switch (currentReportType) {
            case OUT_OF_DATE:
                filename = OUT_OF_DATE_REPORT + new Date().getTime() + CVS_EXT;
                ReportWriter.writeReport(filename, getAllOutOfDate(), "All clients returned books after end date");
                showAlert(Alert.AlertType.INFORMATION, "Report has been created", "Report has been successfully created", "New CSV report in file " + filename + " has been created.");
                break;
            case NOT_RETURNED:
                filename = NOT_RETURNED_REPORT + new Date().getTime() + CVS_EXT;
                if (dateFilter.isSelected()) {
                    ReportWriter.writeReport(filename, getAllNotReturnedJournalsBeforeDate(), "All clients that didn't return their books");
                } else {
                    ReportWriter.writeReport(filename, getAllNotReturnedJournals(), "All clients that didn't return their books");
                }
                showAlert(Alert.AlertType.INFORMATION, "Report has been created", "Report has been successfully created", "New CSV report in file " + filename + " has been created.");
                break;
            case RETURN_IN_RANGE:
                filename = RETURNED_IN_RANGE_REPORT + new Date().getTime() + CVS_EXT;
                ReportWriter.writeReport(filename, getJournalInRange(), "The whole journal from " + fromDate.getValue() + " to " + toDate.getValue());
                showAlert(Alert.AlertType.INFORMATION, "Report has been created", "Report has been successfully created", "New CSV report in file " + filename + " has been created. The whole journal from " + fromDate.getValue() + " to " + toDate.getValue());
                break;
            default:
                LOGGER.info("cant pick up precisely needed type of report");
                showAlert(Alert.AlertType.ERROR, "Report hasn't been created", "Report has not been created!", "New CSV report is unavailable to create. Report this issue to your system administrator or developers team. We are really sorry to make inconvenience staff there.");
        }

    }

    private List<Journal> getAllOutOfDate() {
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getReturnDate() != null && journal.getReturnDate().isAfter(journal.getEndDate())) journals.add(journal);
        }
        return journals;
    }

    private List<Journal> getAllNotReturnedJournalsBeforeDate() {
        LocalDate date = rangePicker.getValue();
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getReturnDate() == null && journal.getEndDate().isBefore(date)) journals.add(journal);
        }
        return journals;
    }

    private List<Journal> getAllNotReturnedJournals() {
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getReturnDate() == null) journals.add(journal);
        }
        return journals;
    }

    private List<Journal> getJournalInRange() {
        LocalDate fromDateValue = fromDate.getValue();
        LocalDate toDateValue = toDate.getValue();
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getStartDate().isAfter(fromDateValue) && journal.getStartDate().isBefore(toDateValue)) journals.add(journal);
        }
        return journals;
    }

    enum Reports {
        OUT_OF_DATE("Books returned after end date"),
        RETURN_IN_RANGE("Journal in range"),
        NOT_RETURNED("Book didn't returned");

        Reports(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }
}
