#ifndef NOTFOUNDEXCEPTION_H
#define NOTFOUNDEXCEPTION_H

#include<iostream>
using namespace std;

class NotFoundException: public exception {
public:
    string what();
};

string NotFoundException::what() {
    return "Exception: not found";
}

#endif