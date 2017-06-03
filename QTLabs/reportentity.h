#include "QString"

class ReportEntity {
private:
    int id;
    QString details;
    bool isProductive;
    QString timestamp;
    int sessionId;

public:
    ReportEntity(int newId, QString newDetails, bool productive, QString newTimestamp, int newSessionId);
    void setId(int newId);
    void setDetails(QString newDetails);
    void setProductive(bool productive);
    void setTimestamp(QString newTimestamp);
    void setSessionId(int newSessionId);

    int getId();
    QString getDetails();
    bool getProductive();
    QString getTimestamp();
    int getSessionId();

};
