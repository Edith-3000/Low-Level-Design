#ifndef SEAT_H
#define SEAT_H

#include<string>
using namespace std;

class Seat {
    const string id;
    const int rowNumber;
    const int seatNumber;

public:
    Seat(const string id, const int rowNumber, const int seatNumber);
};

Seat::Seat(const string id, const int rowNumber, const int seat): id(id), rowNumber(rowNumber), 
                                                                  seatNumber(seatNumber) {}

#endif