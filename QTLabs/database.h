#include "QtSql/QSqlDatabase"
#include "QSqlQuery"
#include "QVariant"
#include "vector"
#include "reportentity.h"

class DataBase {
private:
    QSqlDatabase db;

public:
    void connect();
    void addReport(QString details, bool isProductive, QString timestamp, int sessionId);
    std::vector<ReportEntity> getActions();
    int getProductiveCount();
    int getProductiveCountForSession();
    void deleteAllData();
    std::vector<int> getSessionIds();
    int getLastSessionId();
};
