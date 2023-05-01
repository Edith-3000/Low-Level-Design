#ifndef SEATTEMPORARILYUNAVAILABLEEXCEPTION_H
#define SEATTEMPORARILYUNAVAILABLEEXCEPTION_H

#include <iostream>
using namespace std;

class SeatTemporarilyUnavailableException: public exception {
public:
    string what();
};

string SeatTemporarilyUnavailableException::what() {
    return "Exception: seat temporarily unavailable";
}

#endif