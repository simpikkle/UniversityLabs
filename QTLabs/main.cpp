#include "mainwindow.h"
#include "reportwindow.h"
#include <ctime>
#include <QApplication>

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    return a.exec();
}
