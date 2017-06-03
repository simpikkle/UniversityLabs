#include <QTime>
#include <iostream>
#include <fstream>
using namespace std;

class Configuration {
private:
    int seconds = 1*10*1000;
    int lastSessionId;

    void writeToFile() {
        ofstream myfile;
          myfile.open ("configuration.properties");
          myfile << seconds;
          myfile.close();
    }

public:
    void setInterval(int _seconds) {
        seconds = _seconds;
        writeToFile();
    }

    int getInterval() {
        return seconds;
    }

    int getLastSessionId() {
        return lastSessionId;
    }

    void setLastSessionId(int newSessionId) {
        lastSessionId = newSessionId;
    }
};
