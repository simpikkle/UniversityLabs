#include "reportwindow.h"
#include "ui_reportwindow.h"

ReportWindow::ReportWindow(QWidget *parent) : QDialog(parent), ui(new Ui::ReportWindow) {
    ui->setupUi(this);
    this->setWindowFlags(Qt::Window | Qt::CustomizeWindowHint | Qt::WindowStaysOnTopHint);
}

ReportWindow::~ReportWindow() {
    delete ui;
}

/**
 * @brief ReportWindow::on_addButton_released action after click on add button
 */
void ReportWindow::on_addButton_released() {
    QString actionDetails = ui->actionEdit->toPlainText();
    QString timestamp = getTimestamp();
    bool isProductive = getProductivity();

    DataBase db;
    db.connect();
    db.addReport(actionDetails, isProductive, timestamp, db.getLastSessionId());
    this->hide();
}

/**
 * @brief ReportWindow::getTimestamp allow to get current time and writes this time to suitable formate
 * @return  qstring with formatted date
 */
QString ReportWindow::getTimestamp() {
    time_t t = time(0);
    struct tm * now = localtime( & t );
    QString time = QString::number(now->tm_mon + 1) + "/" +  QString::number(now->tm_mday) + "/" +  QString::number(now->tm_year + 1900) + " " + QString::number(now->tm_hour) + ":" + QString::number(now->tm_min) + ":" + QString::number(now->tm_sec);
    return time;
}

/**
 * @brief ReportWindow::getProductivity get productive or wasted by chechikng what radio button is selected
 * @return boolean, true if productive is selected, false if not
 */
bool ReportWindow::getProductivity() {
    return ui->productiveRadio->isChecked()? true:false;
}

/**
 * @brief ReportWindow::on_actionEdit_textChanged checks what user has eneterd to field and setting enabled or dissabled for add button
 */
void ReportWindow::on_actionEdit_textChanged() {
    if (ui->actionEdit->toPlainText() == "") {
        ui->addButton->setEnabled(false);
    } else {
        ui->addButton->setEnabled(true);
    }
}
