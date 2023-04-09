#ifndef ORDERVALIDATOR_H
#define ORDERVALIDATOR_H

#include<string>
#include<iostream>
#include "order.hpp"
#include "user.hpp"
#include "userManager.hpp"
using namespace std;

class OrderValidator {
public:
    bool validateOrder(string pUserId, Order* pOrder);
};

bool OrderValidator::validateOrder(string pUserId, Order* pOrder) {
    User* user = UserManager::getUser(pUserId);

    if(pOrder->getTxnType() == TXN_TYPE::BUY) {
        cout << "Checking if user with id [" << pUserId << "] has FUNDS to BUY" << endl;
    }
    else {
        cout << "Checking if user with id [" << pUserId << "] has STOCKS to SELL" << endl;
    }

    // Cuurently assuming that order is always valid
    cout << "Assuming that this order is valid" << endl;

    return true;
}

#endif