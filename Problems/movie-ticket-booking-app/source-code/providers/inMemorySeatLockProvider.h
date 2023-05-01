#ifndef INMEMORYSEATLOCKPROVIDER_H
#define INMEMORYSEATLOCKPROVIDER_H

#include<map>
#include<mutex>
#include "seatLockProvider.h"
#include "show.h"
#include "seat.h"
#include "seatLock.h"
#include "seatTemporarilyUnavailableException.h"

using namespace std;

class InMemorySeatLockProvider: public SeatLockProvider {
    static InMemorySeatLockProvider* inMemorySeatLockProviderInstance;
    static mutex mtx;
    const int lockTimeout;
    map<Show*, map<Seat*, SeatLock*>> locks;

    InMemorySeatLockProvider(const int lockTimeout);
    InMemorySeatLockProvider(const InMemorySeatLockProvider&);

public:
    static InMemorySeatLockProvider* getInMemorySeatLockProviderInstance(const int lockTimeout);
    void lockSeats(Show* show, vector<Seat*> seats, string user);
    void unlockSeats(Show* show, vector<Seat*> seats, string user);
    vector<Seat*> getLockedSeats(Show* show);
    bool isSeatLocked(Show* show, Seat* seat);
    void lockSeat(Show* show, Seat* seat, string user);
    bool validateLock(Show* show, Seat* seat, string user);
    void unlockSeat(Show* show, Seat* seat);
};

InMemorySeatLockProvider* InMemorySeatLockProvider::inMemorySeatLockProviderInstance = nullptr;

mutex InMemorySeatLockProvider::mtx;

InMemorySeatLockProvider::InMemorySeatLockProvider(const int lockTimeout): lockTimeout(lockTimeout) {}

InMemorySeatLockProvider* InMemorySeatLockProvider::getInMemorySeatLockProviderInstance(const int lockTimeout = 10) {
    // Double check locking
    if(inMemorySeatLockProviderInstance == nullptr) {
        mtx.lock();
        if(inMemorySeatLockProviderInstance == nullptr) {
            inMemorySeatLockProviderInstance = new InMemorySeatLockProvider(lockTimeout);
        }
        mtx.unlock();
    }

    return inMemorySeatLockProviderInstance;
}

void InMemorySeatLockProvider::lockSeats(Show* show, vector<Seat*> seats, string user) {
    mtx.lock();
        for(auto seat: seats) {
            if(isSeatLocked(show, seat)) {
                throw SeatTemporarilyUnavailableException();
            }
        }

        for(auto seat: seats) {
            lockSeat(show, seat, user);
        }
    mtx.unlock();
}

void InMemorySeatLockProvider::unlockSeats(Show* show, vector<Seat*> seats, string user) {
    for(auto seat: seats) {
        if(isSeatLocked(show, seat)) {
            unlockSeat(show, seat);
        }
    }
}

vector<Seat*> InMemorySeatLockProvider::getLockedSeats(Show* show) {
    vector<Seat*> lockedSeats;

    if(locks.find(show) == locks.end()) {
        return lockedSeats;
    }

    for(auto p: locks[show]) {
        if(isSeatLocked(show, p.first)) {
            lockedSeats.push_back(p.first);
        }
    }

    return lockedSeats;
}

bool InMemorySeatLockProvider::isSeatLocked(Show* show, Seat* seat) {
    return locks.count(show) && locks[show].count(seat) && !locks[show][seat]->isLockExpired();
}

void InMemorySeatLockProvider::lockSeat(Show* show, Seat* seat, string user) {
    SeatLock* lock = new SeatLock(seat, show, lockTimeout, time(nullptr), user);
    locks[show].insert({seat, lock});
}

bool InMemorySeatLockProvider::validateLock(Show* show, Seat* seat, string user) {
    return (isSeatLocked(show, seat) == true) && (locks[show][seat]->getLockedBy() == user);
}

void InMemorySeatLockProvider::unlockSeat(Show* show, Seat* seat) {
    if(locks.find(show) == locks.end()) return;
    if(locks[show].find(seat) == locks[show].end()) return;

    locks[show].erase(seat);
}

#endif