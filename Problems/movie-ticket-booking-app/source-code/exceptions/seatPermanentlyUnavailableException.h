#ifndef SEATPERMANENTLYUNAVAILABLEEXCEPTION_H
#define SEATPERMANENTLYUNAVAILABLEEXCEPTION_H

#include<iostream>
using namespace std;

class SeatPermanentlyUnavailableException: exception {
public:
    string what();
};

string SeatPermanentlyUnavailableException::what() {
    return "Exception: seat permanently unavailable";
}

#endif