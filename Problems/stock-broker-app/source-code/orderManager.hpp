#ifndef ORDERMANAGER_H
#define ORDERMANAGER_H

#include "orderValidator.hpp"
#include "orderExecutioner.hpp"

class OrderManager {
    OrderValidator* validator;
    OrderExecutioner* executioner;
public:
    OrderManager();
    void placeOrder(string pUserId, Order* pOrder);
};

OrderManager::OrderManager() {
    validator = new OrderValidator();
    executioner = new OrderExecutioner();
}

void OrderManager::placeOrder(string pUserId, Order* pOrder) {
    if(validator->validateOrder(pUserId, pOrder) == true) {
        executioner->placeOrder(pUserId, pOrder);
    }

    else {
        cout << "Oops! order can't be executed" << endl;
    }
}

#endif