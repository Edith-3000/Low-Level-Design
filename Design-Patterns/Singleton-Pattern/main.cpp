#include "Logger.h"
#include <thread>

void user_1_logs() {
  Logger* logger1 = Logger::getLoggerInstance();
  logger1->Log("Hello from USER 1");
}

void user_2_logs() {
  Logger* logger2 = Logger::getLoggerInstance();
  logger2->Log("Hello from USER 2");
}

int main() {
  thread t1(user_1_logs);
  thread t2(user_2_logs);

  t1.join();
  t2.join();
  
  return 0;
}