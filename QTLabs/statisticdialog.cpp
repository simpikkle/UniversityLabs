#include "statisticdialog.h"
#include "ui_statisticdialog.h"
#include "iterator"
#include "database.h"
#include "vector"
#include "QColor"

StatisticDialog::StatisticDialog(QWidget *parent) : QDialog(parent), ui(new Ui::StatisticDialog) {
    ui->setupUi(this);
    displayStatistics();
}

/**
 * @brief StatisticDialog::displayStatistics displays all statistic in window
 */
void StatisticDialog::displayStatistics() {
    std::vector<ReportEntity> reports;
    DataBase db;
    db.connect();
    reports = db.getActions();
    int i=0;
    int reportsPerSession = 0;
    if (reports.size() > 0) {
        std::vector<ReportEntity>::iterator it = reports.begin();
        for (it = reports.begin(); it < reports.end(); it++) {
            ReportEntity report = *it;
            if (db.getLastSessionId() == report.getSessionId()) {
                reportsPerSession++;
                QString label;
                label += " " + report.getDetails();
                label += " \n\t(" + report.getTimestamp() + ")";
                ui->listWidget->addItem(label);
                //colorize productive and wasted actions in green and red
                if (report.getProductive()) {
                   ui->listWidget->item(i)->setBackground(QColor(209, 255, 174, 127));
                } else {
                    ui->listWidget->item(i)->setBackground(QColor(255, 155, 147, 127));
                }
                i++;
            }
        }
        int productiveCount = db.getProductiveCountForSession();
        calculateAndDisplayStatistic(productiveCount, reportsPerSession);
    } else {
        displayNotResultYet();
    }
}

/**
 * @brief StatisticDialog::calculateAndDisplayStatistic calculates and displays number bellow statistic list view
 * @param productiveCount count of productive actions
 * @param reportsPerSession count of all reports per session
 */
void StatisticDialog::calculateAndDisplayStatistic(int productiveCount, int reportsPerSession) {
    ui->allRecordsValue->setText(QString::number(reportsPerSession));
    ui->productiveValue->setText(QString::number(productiveCount));
    ui->wastedValue->setText(QString::number(reportsPerSession-productiveCount-1));
    int percentage = productiveCount*100/reportsPerSession;
    ui->efficienceValue->setText(QString::number(percentage));
    colorPercentage(percentage);
}

/**
 * @brief StatisticDialog::colorPercentage colors percentage label that related with percentage
 * @param percentage
 */
void StatisticDialog::colorPercentage(int percentage) {
    if (percentage<=25) {
        ui->efficienceValue->setStyleSheet("QLabel {color: #FF4D41;}");
        ui->statusText->setText("Don't give up!");
    }
    if (percentage>25 && percentage<=50) {
        ui->efficienceValue->setStyleSheet("QLabel {color: #FFA774;}");
        ui->statusText->setText("You can do better!");
    }
    if (percentage>50 && percentage<=75) {
        ui->efficienceValue->setStyleSheet("QLabel {color: #FFFE22;}");
        ui->statusText->setText("Good job, keep working!");
    }
    if (percentage>75) {
        ui->efficienceValue->setStyleSheet("QLabel {color: #BBFF85;}");
        ui->statusText->setText("Excellent!");
    }
}

/**
 * @brief StatisticDialog::displayNotResultYet displaying not result info if sessions activity is empty
 */
void StatisticDialog::displayNotResultYet() {
    ui->listWidget->addItem("NO RESULTS YET\nAdd your first action, and then report will be shown here.");
    ui->listWidget->item(0)->setForeground(QColor(24, 129, 199, 127));
}

StatisticDialog::~StatisticDialog() {
    delete ui;
}
