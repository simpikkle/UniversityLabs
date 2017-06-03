#include "reportentity.h"

/**
 * @brief ReportEntity::ReportEntity Entity class that represents actions table
 * @param newId
 * @param newDetails
 * @param productive
 * @param newTimestamp
 * @param newSessionId
 */
ReportEntity::ReportEntity(int newId, QString newDetails, bool productive, QString newTimestamp, int newSessionId) {
    id = newId;
    details = newDetails;
    isProductive = productive;
    timestamp = newTimestamp;
    sessionId = newSessionId;
}

int ReportEntity::getId() {
    return id;
}

bool ReportEntity::getProductive() {
    return isProductive;
}

QString ReportEntity::getDetails() {
    return details;
}

QString ReportEntity::getTimestamp() {
    return timestamp;
}

void ReportEntity::setId(int newId) {
    id = newId;
}

void ReportEntity::setDetails(QString newDetails) {
    details = newDetails;
}

void ReportEntity::setProductive(bool productive) {
    isProductive = productive;
}

void ReportEntity::setTimestamp(QString newTimestamp) {
    timestamp = newTimestamp;
}

void ReportEntity::setSessionId(int newSessionId) {
    sessionId = newSessionId;
}

int ReportEntity::getSessionId() {
    return sessionId;
}
