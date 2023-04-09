#ifndef STOCK_H
#define STOCK_H

#include<string>
#include "common.hpp"
using namespace std;

class Stock {
    string name;
    double price;
    EXCHANGE exchange;
public:
    Stock(string pName, double pPrice, EXCHANGE pExchange);
};

Stock::Stock(string pName, double pPrice, EXCHANGE pExchange) : 
    name(pName), price(pPrice), exchange(pExchange) {}

#endif