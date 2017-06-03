#include "allsessions.h"
#include "ui_allsessions.h"
#include "database.h"

AllSessions::AllSessions(QWidget *parent) : QDialog(parent), ui(new Ui::AllSessions) {
    ui->setupUi(this);
    customPlot = ui->customPlot;
    DataBase db;
    db.connect();
    int count = 0;
    int totalProductivity = 0;
    int productive = 0;
    std::vector<ReportEntity> reports = db.getActions();
    std::vector<int> sessionIds = db.getSessionIds();
    QVector<double> sessions(101), y(101); // initialize with entries 0..100
    ui->label_2->setText(QString::number(sessionIds.size()));

    if (sessionIds.size() > 0) {
        std::vector<int>::iterator it = sessionIds.begin();
        for (int i = 0; i < sessionIds.size(); i++) {
            count = 0;
            productive = -1;
            QString label = "Session " + QString::number(sessionIds.at(i));
            std::vector<ReportEntity>::iterator it = reports.begin();
            for (it = reports.begin(); it < reports.end(); it++) {
                ReportEntity report = *it;
                if (report.getSessionId() == sessionIds.at(i)) {
                    count++;
                    if (report.getProductive()) {
                        productive++;
                    }
                }
            }
            int productivePerSession = productive*100/count;
            y[i] = productivePerSession;
            sessions[i] = i;
            totalProductivity += productivePerSession;
            label += " [" + QString::number(count) + " items] ";
            label += QString::number(productivePerSession) + "% productive";
            ui->listWidget->addItem(label);
        }
    }
    ui->label_4->setText(QString::number(totalProductivity/sessionIds.size()));

    // generate some data:
    // create graph and assign data to it:
    customPlot->addGraph();
    customPlot->graph(0)->setData(sessions, y);
    // give the axes some labels:
    customPlot->xAxis->setLabel("Sessions");
    customPlot->yAxis->setLabel("Productivity");
    // set axes ranges, so we see all data:
    customPlot->xAxis->setRange(0, sessionIds.size());
    customPlot->yAxis->setRange(0, 100);
    customPlot->replot();
}

AllSessions::~AllSessions() {
    delete ui;
}
