#ifndef ORDER_H
#define ORDER_H

#include "stock.hpp"

class Order {
    TXN_TYPE txnType;
    ORDER_TYPE orderType;
    EXCHANGE exchange;
    int quantity;
    double price;
    Stock* stock;
    // vector<Transactions> transactions;
    // ORDER_STATUS orderStatus;
    // time orderTime;

public:
    Order(TXN_TYPE pTxnType,ORDER_TYPE pOrderType, EXCHANGE pExchange, int pQuantity, double pPrice, Stock* pStock);
    TXN_TYPE getTxnType();
};

Order::Order(TXN_TYPE pTxnType,ORDER_TYPE pOrderType, EXCHANGE pExchange, int pQuantity, double pPrice, Stock* pStock) :
    txnType(pTxnType), orderType(pOrderType), exchange(pExchange), quantity(pQuantity), price(pPrice), stock(pStock) {}

TXN_TYPE Order::getTxnType() {
    return txnType;
}

#endif