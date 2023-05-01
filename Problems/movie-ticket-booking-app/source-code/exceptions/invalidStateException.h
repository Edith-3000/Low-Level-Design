#ifndef INVALIDSTATEEXCEPTION_H
#define INVALIDSTATEEXCEPTION_H

#include<iostream>
using namespace std;

class InvalidStateException: exception {
public:
    string what();
};

string InvalidStateException::what() {
    return "Exception: invalid state";
}

#endif