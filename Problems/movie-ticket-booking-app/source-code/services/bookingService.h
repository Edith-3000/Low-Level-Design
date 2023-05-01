#ifndef BOOKINGSERVICE_H
#define BOOKINGSERVICE_H

#include <map>
#include <mutex>
#include "booking.h"
#include "notFoundException.h"
#include "seatPermanentlyUnavailableException.h"
#include "seatLockProvider.h"
#include "inMemorySeatLockProvider.h"

#include <boost/uuid/uuid.hpp>            // uuid class
#include <boost/uuid/uuid_generators.hpp> // generators
#include <boost/uuid/uuid_io.hpp>         // streaming operators etc.

using namespace std;
using namespace boost::uuids;

class BookingService {
    static BookingService* bookingServiceInstance;
    static mutex mtx;
    map<string, Booking*> bookings;

    BookingService();
    BookingService(const BookingService&);

public:
    static BookingService* getBookingServiceInstance();
    Booking* getBooking(const string bookingId);
    void createBooking(const string user, Show* show, vector<Seat*> seats);
    bool isAnySeatAlreadyBooked(Show* show, vector<Seat*>);
    vector<Seat*> getBookedSeats(Show* show);
    vector<Booking*> getAllBookings(Show* show);
    void confirmBooking(Booking* booking, const string user);
};

BookingService* BookingService::bookingServiceInstance = nullptr;

mutex BookingService::mtx;

BookingService::BookingService() {}

BookingService* BookingService::getBookingServiceInstance() {
    // Double Check Locking
    if(bookingServiceInstance == nullptr) {
        mtx.lock();
        if(bookingServiceInstance == nullptr) {
            bookingServiceInstance = new BookingService();
        }
        mtx.unlock();
    }

    return bookingServiceInstance;
}

Booking* BookingService::getBooking(const string bookingId) {
    if(bookings.find(bookingId) == bookings.end()) {
        throw NotFoundException();
    }

    return bookings[bookingId];
}

void BookingService::createBooking(const string user, Show* show, vector<Seat*> seats) {
    if(isAnySeatAlreadyBooked(show, seats)) {
        throw SeatPermanentlyUnavailableException();
    }

    // Hardcoding lockTimeout to be 10
    SeatLockProvider* seatLockProvider = InMemorySeatLockProvider::getInMemorySeatLockProviderInstance(10);
    
    try {
        seatLockProvider->lockSeats(show, seats, user);
    } catch (SeatTemporarilyUnavailableException e) {
        e.what();
    }

    random_generator gen;
    uuid id = gen();
    string strId = to_string(id);

    Booking* booking = new Booking(strId, show, user, seats);
    bookings.insert({strId, booking});

    // TODO: Create timer for booking expiry
}

bool BookingService::isAnySeatAlreadyBooked(Show* show, vector<Seat*> seats) {
    vector<Seat*> bookedSeats = getBookedSeats(show);

    for(auto seat: seats) {
        if(find(bookedSeats.begin(), bookedSeats.end(), seat) != bookedSeats.end()) {
            return true;
        }
    }

    return false;
}

vector<Seat*> BookingService::getBookedSeats(Show* show) {
    vector<Booking*> bookingsOfShow = getAllBookings(show);
    vector<Seat*> bookedSeats;

    for(auto booking: bookingsOfShow) {
        if(booking->getBookingStatus() != BOOKING_STATUS::CONFIRMED) continue;

        vector<Seat*> bookedSeatsOfBooking = booking->getBookedSeats();
        for(auto seat: bookedSeatsOfBooking) {
            bookedSeats.push_back(seat);
        }
    }

    return bookedSeats;
}

vector<Booking*> BookingService::getAllBookings(Show* show) {
    vector<Booking*> bookingsOfShow;

    for(auto [_, booking]: bookings) {
        if(booking->getShow() == show) {
            bookingsOfShow.push_back(booking);
        }
    }

    return bookingsOfShow;
}

void BookingService::confirmBooking(Booking* booking, const string user) {
    if(booking->getUser() != user) {
        throw BadRequestException();
    }

    // SeatLockProvider* seatLockProvider = InMemorySeatLockProvider::getInMemorySeatLockProviderInstance();
    InMemorySeatLockProvider* seatLockProvider = InMemorySeatLockProvider::getInMemorySeatLockProviderInstance();

    for(auto seat: booking->getBookedSeats()) {
        if(seatLockProvider->validateLock(booking->getShow(), seat, user) == false) {
            throw BadRequestException();
        }
    }

    try {
        booking->confirmBooking();
    } catch(InvalidStateException e) {
        e.what();
        cout << endl;
    }
}

#endif