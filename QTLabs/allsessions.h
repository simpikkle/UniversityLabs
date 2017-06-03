#ifndef ALLSESSIONS_H
#define ALLSESSIONS_H

#include <QDialog>
#include <qcustomplot.h>

namespace Ui {
class AllSessions;
}

class AllSessions : public QDialog
{
    Q_OBJECT

public:
    explicit AllSessions(QWidget *parent = 0);
    ~AllSessions();

private:
    Ui::AllSessions *ui;
    QCustomPlot *customPlot;
};

#endif // ALLSESSIONS_H
