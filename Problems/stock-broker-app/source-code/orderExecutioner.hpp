#ifndef ORDEREXECUTIONER_H
#define ORDEREXECUTIONER_H

#include<string>
#include "order.hpp"
#include "exchangeConnector.hpp"
using namespace std;

class OrderExecutioner {
public:
    void placeOrder(string pUserId, Order* porder);
};

void OrderExecutioner::placeOrder(string pUserId, Order* porder) {
    ExchangeConnector* exchangeConnectorInstance = ExchangeConnector::getExchangeConnector();
    exchangeConnectorInstance->sendOrderTOExchange(pUserId, porder);
}

#endif