#ifndef SEATLOCK_H
#define SEATLOCK_H

#include<ctime>
#include<string>
#include "seat.h"
#include "show.h"

class SeatLock {
    Seat* seat;
    Show* show;
    const int timeoutInSeconds;
    const time_t lockTime;
    const string lockedBy;

public:
    SeatLock(Seat* seat, Show* show, const int timeoutInSeconds, const time_t lockTime, const string lockedBy);
    bool isLockExpired();
    string getLockedBy();
};

SeatLock::SeatLock(Seat* seat, Show* show, const int timeoutInSeconds, const time_t lockTime, const string lockedBy):
                   seat(seat), show(show), timeoutInSeconds(timeoutInSeconds), lockTime(lockTime), lockedBy(lockedBy) {}

bool SeatLock::isLockExpired() {
    time_t lockExpiryTime = lockTime + timeoutInSeconds;
    time_t currentTime = time(nullptr);

    return (lockExpiryTime < currentTime);
}

string SeatLock::getLockedBy() {
    return lockedBy;
}

#endif