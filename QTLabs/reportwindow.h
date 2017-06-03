#include <QDialog>
#include <QString>
#include "database.h"
#include <ctime>

namespace Ui {
class ReportWindow;
}

class ReportWindow : public QDialog
{
    Q_OBJECT

public:
    explicit ReportWindow(QWidget *parent = 0);
    ~ReportWindow();
    void setLastId(int id) {
        lastId = id;
    }

private slots:
    void on_addButton_released();    
    void on_actionEdit_textChanged();

private:
    Ui::ReportWindow *ui;
    QString getTimestamp();
    bool getProductivity();
    int lastId;
};
