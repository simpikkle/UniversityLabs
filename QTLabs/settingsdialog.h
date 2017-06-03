#include <QDialog>
#include "configuration.h"

namespace Ui {
class SettingsDialog;
}

class SettingsDialog : public QDialog
{
    Q_OBJECT

    Configuration conf;
public:
    explicit SettingsDialog(QWidget *parent = 0);
    ~SettingsDialog();

private slots:
    void on_buttonBox_accepted();

    void on_deleteDBData_clicked();

private:
    Ui::SettingsDialog *ui;
};
