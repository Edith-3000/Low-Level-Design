#ifndef THEATRESERVICE_H
#define THEATRESERVICE_H

#include<string>
#include<map>
#include<mutex>
#include "theatre.h"
#include "screen.h"
#include "seat.h"
#include<notFoundException.h>

#include <boost/uuid/uuid.hpp>            // uuid class
#include <boost/uuid/uuid_generators.hpp> // generators
#include <boost/uuid/uuid_io.hpp>         // streaming operators etc.

using namespace boost::uuids;

class TheatreService {
    static TheatreService* theatreServiceInstance;
    static mutex mtx;
    map<string, shared_ptr<Theatre>> theatres;
    map<string, shared_ptr<Screen>> screens;
    map<string, shared_ptr<Seat>> seats;

    TheatreService();
    TheatreService(const TheatreService&);

public:
    static TheatreService* getTheatreServiceInstance();
    void createTheatre(const string theatreName);
    void createScreenInTheatre(const string screenName, Theatre* theatre);
    void createSeatInScreen(const int rowNumber, const int seatNumber, Screen* screen);
    Theatre* getTheatre(const string theatreId);
    Screen* getScreen(const string screenId);
    Seat* getSeat(const string seatId);
};

TheatreService* TheatreService::theatreServiceInstance = nullptr;

mutex TheatreService::mtx;

TheatreService::TheatreService() {}

TheatreService* TheatreService::getTheatreServiceInstance() {
    // Double check locking
    if(theatreServiceInstance == nullptr) {
        mtx.lock();
        if(theatreServiceInstance == nullptr) {
            theatreServiceInstance = new TheatreService();
        }
        mtx.unlock();
    }

    return theatreServiceInstance;
}

void TheatreService::createTheatre(const string theatreName) {
    random_generator gen;
    uuid id = gen();
    string strId = to_string(id);

    shared_ptr<Theatre> theatre = make_shared<Theatre>(strId, theatreName);

    theatres.insert({strId, theatre});
}

void TheatreService::createScreenInTheatre(const string screenName, Theatre* theatre) {
    random_generator gen;
    uuid id = gen();
    string strId = to_string(id);

    shared_ptr<Screen> screen = make_shared<Screen>(strId, screenName, theatre);

    screens.insert({strId, screen});
    theatre->addScreen(screen.get());
}

void TheatreService::createSeatInScreen(const int rowNumber, const int seatNumber, Screen* screen) {
    random_generator gen;
    uuid id = gen();
    string strId = to_string(id);

    shared_ptr<Seat> seat = make_shared<Seat>(id, rowNumber, seatNumber);

    seats.insert({strId, seat});
    screen->addSeat(seat.get());
}

Theatre* TheatreService::getTheatre(const string theatreId) {
    if(theatres.find(theatreId) == theatres.end()) {
        throw NotFoundException();
    }

    return theatres[theatreId].get();
}

Screen* TheatreService::getScreen(const string screenId) {
    if(screens.find(screenId) == screens.end()) {
        throw NotFoundException();
    }

    return screens[screenId].get();
}

Seat* TheatreService::getSeat(const string seatId) {
    if(seats.find(seatId) == seats.end()) {
        throw NotFoundException();
    }

    return seats[seatId].get();
}

#endif