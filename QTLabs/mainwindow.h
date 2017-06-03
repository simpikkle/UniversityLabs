#include <QMainWindow>
#include <settingsdialog.h>
#include <statisticdialog.h>
#include <aboutdialog.h>
#include <QSystemTrayIcon>
#include <QMenu>
#include <QMessageBox>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
private slots:
    void showReportDialog();
    void setProgressBarValue();
    void on_settingsButton_released();
    void on_statisticButton_released();
    void on_pushButton_released();

    void changeEvent(QEvent*);
    void trayIconActivated(QSystemTrayIcon::ActivationReason reason);
    void trayActionExecute();
    void setTrayIconActions();
    void showTrayIcon();
    void on_pushButton_2_released();

    void on_setNewInterval_released();

    void on_intervalEdit_textChanged(const QString &arg1);

private:
    Ui::MainWindow *ui;
    SettingsDialog *settingsDialog;
    StatisticDialog *statisticsWindows;
    AboutDialog *aboutDialog;

    QMenu *trayIconMenu;
    QAction *minimizeAction;
    QAction *restoreAction;
    QAction *quitAction;
    QSystemTrayIcon *trayIcon;
    QTimer *timer;
    QTimer *progressTimer;

    void createSession();
    void setDefaultProgressBar();
    void setReportWindowTimer();
    void setProgressBarTimer();
    void resetingReportTimer();
    void resetingProgressTimer();
    void setDefaultInterval();
    void setWindowParams();
    void setDefaults();
    void showInfoBox(QString title, QString bodyText);
};
