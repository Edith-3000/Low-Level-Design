#ifndef SEATLOCKPROVIDER_H
#define SEATLOCKPROVIDER_H

#include<string>
#include<vector>
#include "show.h"
#include "seat.h"

class SeatLockProvider {
public:
    virtual void lockSeats(Show* show, vector<Seat*> seats, string user) = 0;
    virtual void unlockSeats(Show* show, vector<Seat*> seats, string user) = 0;
    virtual vector<Seat*> getLockedSeats(Show* show) = 0;
};

#endif