#ifndef BOOKING_H
#define BOOKING_H

#include<string>
#include "show.h"
#include "common.h"
#include "invalidStateException.h"

class Booking {
    const string id;
    Show* show;
    const string user;
    BOOKING_STATUS bookingStatus;
    vector<Seat*> bookedSeats;

public:
    Booking(const string id, Show* show, const string user, vector<Seat*> bookedSeats);
    Show* getShow();
    BOOKING_STATUS getBookingStatus();
    vector<Seat*> getBookedSeats();
    string getUser();
    void confirmBooking();
};

Booking::Booking(const string id, Show* show, const string user, vector<Seat*> bookedSeats): id(id), show(show), user(user) {
    this->bookedSeats = bookedSeats;
    bookingStatus = BOOKING_STATUS::CREATED;
}

Show* Booking::getShow() {
    return show;
}

BOOKING_STATUS Booking::getBookingStatus() {
    return bookingStatus;
}

vector<Seat*> Booking::getBookedSeats() {
    return bookedSeats;
}

string Booking::getUser() {
    return user;
}

void Booking::confirmBooking() {
    if(bookingStatus != BOOKING_STATUS::CREATED) {
        throw InvalidStateException();
    }

    bookingStatus = BOOKING_STATUS::CONFIRMED;
}

#endif