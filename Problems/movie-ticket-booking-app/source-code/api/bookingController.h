#ifndef BOOKINGCONTROLLER_H
#define BOOKINGCONTROLLER_H

#include<string>
#include "bookingService.h"
#include "showService.h"
#include "theatreService.h"
#include "show.h"
#include "notFoundException.h"

using namespace std;

class BookingController {
public:
    BookingController();
    void createBooking(const string user, const string showId, vector<string> seatIds);
};

BookingController::BookingController() {}

void BookingController::createBooking(const string user, const string showId, vector<string> seatIds) {
    BookingService* bookingServiceInstance = BookingService::getBookingServiceInstance();
    ShowService* showServiceInstance = ShowService::getShowServiceInstance();
    TheatreService* theatreServiceInstance = TheatreService::getTheatreServiceInstance();

    Show* show;
    vector<Seat*> seats;

    try {
        show = showServiceInstance->getShow(showId);

        for(auto seatId: seatIds) {
            seats.push_back(theatreServiceInstance->getSeat(seatId));
        }
    } catch (NotFoundException e) {
        e.what();
        cout << endl;
    }

    bookingServiceInstance->createBooking(user, show, seats);
}

#endif