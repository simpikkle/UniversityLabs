#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "reportwindow.h"
#include "QTimer"
#include "iostream"
#include "allsessions.h"

static int reportInterval = 10*60*1000;

using namespace std;

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow) {
    ui->setupUi(this);
    setDefaults();
    createSession();
}

MainWindow::~MainWindow() {
    delete ui;
}

/**
 * @brief MainWindow::setDefaults  method that calls other methods to set up default labels, values, timers, and so on
 */
void MainWindow::setDefaults() {
    setWindowParams();
    setDefaultInterval();
    setDefaultProgressBar();
    setReportWindowTimer();
    setProgressBarTimer();
}

/**
 * @brief MainWindow::setWindowParams set up tray icons settings, windows buttons (in window main line)
 */
void MainWindow::setWindowParams() {
    this -> setTrayIconActions();
    this -> showTrayIcon();
    this->setWindowFlags(Qt::Window | Qt::WindowMinimizeButtonHint | Qt::WindowMinMaxButtonsHint | Qt::WindowMaximizeButtonHint | Qt::CustomizeWindowHint);
}

/**
 * @brief MainWindow::setDefaultInterval set up default interva value and label to activity and manage tabs
 */
void MainWindow::setDefaultInterval() {
    ui->intervalValueLabel->setText(QString::number(reportInterval/60000) + " min");
    ui->intervalEdit->setText(QString::number(reportInterval/60000));
}

/**
 * @brief MainWindow::setDefaultProgressBar set up default progress bar value and interval.
 * setProgressBarValue provides data and setting up bar value from DB in percentes
 */
void MainWindow::setDefaultProgressBar() {
    ui->progressBar->setMinimum(0);
    ui->progressBar->setMaximum(100);
    setProgressBarValue();
}

/**
 * @brief MainWindow::setReportWindowTimer set up timer that shows report windows every N minutes
 */
void MainWindow::setReportWindowTimer() {
    timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()), this, SLOT(showReportDialog()));
    timer->start(reportInterval);
}

/**
 * @brief MainWindow::setProgressBarTimer set up timer that displays progress bar value every N minutes + 10 seconds
 */
void MainWindow::setProgressBarTimer() {
    progressTimer = new QTimer(this);
    connect(progressTimer, SIGNAL(timeout()), this, SLOT(setProgressBarValue()));
    progressTimer->start(reportInterval+10000);
}

/**
 * @brief MainWindow::showReportDialog display the report window with report form
 */
void MainWindow::showReportDialog() {
    ReportWindow *window = new ReportWindow;
    window->exec();
}

/**
 * @brief MainWindow::on_settingsButton_released displays settings window
 */
void MainWindow::on_settingsButton_released() {
    settingsDialog = new SettingsDialog;
    settingsDialog->exec();
}

/**
 * @brief MainWindow::on_statisticButton_released displays statistic window (with curent results or finished results)
 */
void MainWindow::on_statisticButton_released() {
    statisticsWindows = new StatisticDialog;
    statisticsWindows->exec();
}

/**
 * @brief MainWindow::on_pushButton_released open window with 'about' information
 */
void MainWindow::on_pushButton_released() {
    aboutDialog = new AboutDialog;
    aboutDialog->exec();
}

/**
 * @brief MainWindow::showTrayIcon  displays tray icon on the tray panel
 */
void MainWindow::showTrayIcon() {
    trayIcon = new QSystemTrayIcon(this);
    QIcon trayImage(":/images/icons/trayIcon.png");
    trayIcon -> setIcon(trayImage);
    trayIcon -> setContextMenu(trayIconMenu);
    // Action Listener for tray icon clicks
    connect(trayIcon, SIGNAL(activated(QSystemTrayIcon::ActivationReason)), this, SLOT(trayIconActivated(QSystemTrayIcon::ActivationReason)));
    trayIcon -> show();
}

void MainWindow::trayActionExecute() {
    this->show();
}

/**
 * @brief MainWindow::trayIconActivated events with tray icon (in tray panel)
 * @param reason
 */
void MainWindow::trayIconActivated(QSystemTrayIcon::ActivationReason reason) {
    switch (reason) {
        case QSystemTrayIcon::Trigger:
        case QSystemTrayIcon::DoubleClick:
            this -> trayActionExecute();
            break;
        default:
            break;
    }
}

/**
 * @brief MainWindow::setTrayIconActions set context menu for tray icon
 */
void MainWindow::setTrayIconActions() {
    // Setting actions...
    restoreAction = new QAction("Restore", this);
    quitAction = new QAction("Quit", this);

    // Connecting actions to slots...
    connect (restoreAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    connect (quitAction, SIGNAL(triggered()), qApp, SLOT(quit()));

    // Setting system tray's icon menu...
    trayIconMenu = new QMenu(this);
    trayIconMenu -> addAction (restoreAction);
    trayIconMenu -> addAction (quitAction);
}

/**
 * @brief MainWindow::changeEvent  cathce minimize to task panel and retrieve to tray panel
 * @param event
 */
void MainWindow::changeEvent(QEvent *event) {
    QMainWindow::changeEvent(event);
    if (event -> type() == QEvent::WindowStateChange) {
        if (isMinimized()) {
            this -> hide();
        }
        if (isActiveWindow()) {
            setProgressBarValue();
        }
    }
}

/**
 * @brief MainWindow::setProgressBarValue select data from db and refresh progress bar value int percentes
 */
void MainWindow::setProgressBarValue() {
    DataBase db;
    db.connect();
    int allActionsCount = db.getActions().size();
    int percentage =  (allActionsCount == 0)? 0: db.getProductiveCount() * 100 / allActionsCount;
    ui->progressBar->setValue(percentage);
    ui->progressBar->setToolTip(QString("Your productive is ") + QString::number(percentage) + "%");
}

void MainWindow::on_pushButton_2_released() {
    AllSessions *allSessions = new AllSessions;
    allSessions->exec();
}

void MainWindow::on_setNewInterval_released() {
    reportInterval = ui->intervalEdit->text().toInt() * 60 * 1000;
    this->ui->intervalValueLabel->setText(QString::number(reportInterval/60000) + " min");
    resetingReportTimer();
    resetingProgressTimer();
    showInfoBox("Success", "Interval has been changed successfully");
}

/**
 * @brief MainWindow::resetingProgressTimer reset and start with new interval timer for progress bar
 */
void MainWindow::resetingReportTimer() {
    timer->stop();
    timer->start(reportInterval);
}

/**
 * @brief MainWindow::resetingProgressTimer reset and start with new interval timer for report window
 */
void MainWindow::resetingProgressTimer() {
    progressTimer->stop();
    progressTimer->start();
}

/**
 * @brief MainWindow::showInfoBox show messagebox with customizeble info
 * @param title  window title QString
 * @param bodyText  message text QString
 */
void MainWindow::showInfoBox(QString title, QString bodyText) {
    QMessageBox msgBox;
      msgBox.setText(title);
      msgBox.setInformativeText(bodyText);
      msgBox.setStandardButtons(QMessageBox::Ok);
      msgBox.setDefaultButton(QMessageBox::Ok);
      msgBox.exec();
}

/**
 * @brief MainWindow::on_intervalEdit_textChanged edit text changed listener, makes enabled or disabled Apply button
 * @param arg1
 */
void MainWindow::on_intervalEdit_textChanged(const QString &arg1) {
    if (arg1.toInt() >= 1) {
        ui->setNewInterval->setEnabled(true);
    } else {
        ui->setNewInterval->setEnabled(false);
    }
}

/**
 * @brief MainWindow::createSession provide new session after application starting
 */
void MainWindow::createSession() {
    time_t t = time(0);
    struct tm * now = localtime( & t );
    QString time = QString::number(now->tm_mon + 1) + "/" +  QString::number(now->tm_mday) + "/" +  QString::number(now->tm_year + 1900) + " " + QString::number(now->tm_hour) + ":" + QString::number(now->tm_min) + ":" + QString::number(now->tm_sec);
    DataBase db;
    db.connect();
    int sessionId = db.getLastSessionId();
    db.addReport("Starting new session", true, time, ++sessionId);
}
