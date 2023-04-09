#ifndef EXCHANGECONNECTOR_H
#define EXCHANGECONNECTOR_H

#include<mutex>
#include<iostream>
#include "order.hpp"
using namespace std;

class ExchangeConnector {
    static ExchangeConnector* exchangeConnectorInstance;
    static mutex mtx;
    ExchangeConnector();
    ExchangeConnector(const ExchangeConnector&);
    ExchangeConnector& operator=(const ExchangeConnector&);
public:
    static ExchangeConnector* getExchangeConnector();
    static void sendOrderTOExchange(string pUserId, Order* pOrder);
};

ExchangeConnector* ExchangeConnector::exchangeConnectorInstance = nullptr;

mutex ExchangeConnector::mtx;

ExchangeConnector::ExchangeConnector() {}

ExchangeConnector* ExchangeConnector::getExchangeConnector() {
    // Double check locking
    if(exchangeConnectorInstance == nullptr) {
        mtx.lock();
        if(exchangeConnectorInstance == nullptr) {
            exchangeConnectorInstance = new ExchangeConnector();
        }
        mtx.unlock();
    }

    return exchangeConnectorInstance;
}

void ExchangeConnector::sendOrderTOExchange(string pUserId, Order* pOrder) {
    cout << "Order sent to EXCHANGE, Congratulations!" << endl;
}

#endif