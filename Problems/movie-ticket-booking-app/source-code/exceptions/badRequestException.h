#ifndef BADREQUESTEXCEPTION_H
#define BADREQUESTEXCEPTION_H

#include<iostream>
using namespace std;

class BadRequestException: public exception {
public:
    string what();
};

string BadRequestException::what() {
    return "Exception: bad request";
}

#endif