#include "database.h"

/**
 * @brief DataBase::connect set connection with DB
 */
void DataBase::connect() {
    //seting up DB connection
    db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName("/Users/eermashova/Documents/QT/test/db.sqlite");
    if (!db.open()) {
        printf("\nerror with connection to db\n");
    }
}

/**
 * @brief DataBase::addReport make query to db with ready params, adding record
 * @param details - what actions user did
 * @param isProductive - productive or wasted time
 * @param timestamp - time label
 */
void DataBase::addReport(QString details, bool isProductive, QString timestamp, int sessionId) {
    QSqlQuery query;
    query.prepare("INSERT INTO actions (action_details, is_productive, timestamp, session_id) VALUES (:details, :productive, :timestamp, :session_id);");
    query.bindValue(":details", details);
    query.bindValue(":productive", isProductive);
    query.bindValue(":timestamp", timestamp);
    query.bindValue(":session_id", sessionId);
    query.exec();
}

/**
 * @brief DataBase::getActions
 * @return vector with ReportEntities that will be get from DB
 */
std::vector<ReportEntity> DataBase::getActions() {
    std::vector<ReportEntity> reports;
    QSqlQuery query;
    query.exec("SELECT * FROM actions");

    //getting values from query and writing to vector
    while (query.next()) {
        int id = query.value(0).toInt();
        QString details = query.value(1).toString();
        bool isProductive = query.value(2).toBool();
        QString timestamp = query.value(3).toString();
        int sessionId = query.value(4).toInt();
        reports.push_back(ReportEntity(id, details, isProductive, timestamp, sessionId));
    }
    return reports;
}


/**
 * @brief DataBase::getProductiveCount
 * @return int count of productive actions from data base
 */
int DataBase::getProductiveCount() {
    QSqlQuery query;
    int i=0;
    query.exec("SELECT id FROM actions WHERE is_productive=1");
    while(query.next()) {
        i++;
    }
    return i-1;
}

/**
 * @brief DataBase::getProductiveCountForSession
 * @return int count of productive actions from data base for current session
 */
int DataBase::getProductiveCountForSession() {
    QSqlQuery query;
    int i=0;
    QString sql;
    sql = QString("SELECT id FROM actions WHERE is_productive=1 AND session_id=")
            .append(QString::number(getLastSessionId()));
    query.exec(sql);
    while(query.next()) {
        i++;
    }
    return i-1;
}

/**
 * @brief DataBase::deleteAllData  delete all records in base
 */
void DataBase::deleteAllData() {
    QSqlQuery query;
    query.exec("DELETE FROM actions");
}

/**
 * @brief DataBase::getSessionsCount   provide sql statement to get count of sessions
 * @return integer sessions count
 */
std::vector<int> DataBase::getSessionIds() {
    std::vector<int> sessionIds;
    QSqlQuery query;
    query.exec("select distinct session_id from actions");
    while(query.next()) {
        sessionIds.push_back(query.value(0).toInt());
    }
    return sessionIds;
}

/**
 * @brief DataBase::getLastSessionId get last session id from data base
 * @return int last session id
 */
int DataBase::getLastSessionId() {
    QSqlQuery query;
    int sessionId = 0;
    query.exec("select max(session_id) from actions");
    while(query.next()) {
        sessionId = query.value(0).toInt();
    }
    return sessionId;
}
