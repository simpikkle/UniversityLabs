#include <QDialog>

namespace Ui {
class StatisticDialog;
}

class StatisticDialog : public QDialog
{
    Q_OBJECT

public:
    explicit StatisticDialog(QWidget *parent = 0);
    ~StatisticDialog();

private:
    Ui::StatisticDialog *ui;
    void colorPercentage(int percentage);
    void calculateAndDisplayStatistic(int productiveCount, int reportsPerSession);
    void displayNotResultYet();
    void displayStatistics();

};
