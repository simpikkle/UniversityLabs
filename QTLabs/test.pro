#-------------------------------------------------
#
# Project created by QtCreator 2016-12-08T18:39:50
#
#-------------------------------------------------

QT       += core gui sql

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets printsupport

TARGET = test
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    settingsdialog.cpp \
    statisticdialog.cpp \
    reportwindow.cpp \
    aboutdialog.cpp \
    database.cpp \
    reportentity.cpp \
    allsessions.cpp \
    qcustomplot.cpp

HEADERS  += mainwindow.h \
    settingsdialog.h \
    statisticdialog.h \
    reportwindow.h \
    aboutdialog.h \
    configuration.h \
    database.h \
    reportentity.h \
    allsessions.h \
    qcustomplot.h

FORMS    += mainwindow.ui \
    settingsdialog.ui \
    statisticdialog.ui \
    reportwindow.ui \
    aboutdialog.ui \
    allsessions.ui

RESOURCES += \
    icons.qrc

DISTFILES += \
    configuration.properties
