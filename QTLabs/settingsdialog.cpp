#include "settingsdialog.h"
#include "ui_settingsdialog.h"
#include "database.h"
#include "QMessageBox"

SettingsDialog::SettingsDialog(QWidget *parent) : QDialog(parent), ui(new Ui::SettingsDialog) {
    ui->setupUi(this);
}

SettingsDialog::~SettingsDialog() {
    delete ui;
}

/**
 * @brief SettingsDialog::on_buttonBox_accepted close window if OK button is pressed
 */
void SettingsDialog::on_buttonBox_accepted() {
    this->close();
}

/**
 * @brief SettingsDialog::on_deleteDBData_clicked delete data by delete button clicking
 */
void SettingsDialog::on_deleteDBData_clicked() {
    if (QMessageBox::Yes == QMessageBox::question(this, "Confirm deleting", "Are you sure that you want to delete all records?", QMessageBox::Yes|QMessageBox::No)) {
        DataBase db;
        db.connect();
        db.deleteAllData();
        QMessageBox::information(this, "Successfully deleted", "All data are removed.", QMessageBox::Ok);
    }
}
