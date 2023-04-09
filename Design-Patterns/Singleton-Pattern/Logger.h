#ifndef LOGGER_H
#define LOGGER_H
#include <iostream>
#include <string>
#include <mutex>
using namespace std;

class Logger {
  static Logger* loggerInstance;
  static mutex mtx;

  // make default constructor as private
  Logger();

  // make copy constructor as private
  Logger(const Logger&);

  // make assignment operator overloading as private
  Logger& operator=(const Logger&);

public:
    static Logger* getLoggerInstance();
    void Log(string s);
};

Logger* Logger::loggerInstance = nullptr;

mutex Logger::mtx;

Logger::Logger() {
  cout << "New instance of Logger created" << endl;
}

Logger* Logger::getLoggerInstance() {
  // Double Check Locking
  if(loggerInstance == nullptr) {
    mtx.lock();
    if(loggerInstance == nullptr) {
      loggerInstance = new Logger();
    }
    mtx.unlock();
  }

  return loggerInstance;
}

void Logger::Log(string message) {
  cout << message << endl;
}

#endif