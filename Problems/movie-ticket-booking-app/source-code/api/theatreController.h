#ifndef THEATRECONTROLLER_H
#define THEATRECONTROLLER_H

#include<string>
#include "theatreService.h"
using namespace std;

class TheatreController {
public:
    TheatreController();
    void createTheatre(const string theatreName);
    void createScreenInTheatre(const string screenName, const string theatreId);
    void createSeatInScreen(const int rowNumber, const int seatNumber, const string screenId);
};

TheatreController::TheatreController() {}

void TheatreController::createTheatre(const string theatreName) {
    TheatreService* theatreServiceInstance = TheatreService::getTheatreServiceInstance();
    theatreServiceInstance->createTheatre(theatreName);
}

void TheatreController::createScreenInTheatre(const string screenName, const string theatreId) {
    TheatreService* theatreServiceInstance = TheatreService::getTheatreServiceInstance();
    Theatre* theatre = theatreServiceInstance->getTheatre(theatreId);
    theatreServiceInstance->createScreenInTheatre(screenName, theatre);
}

void TheatreController::createSeatInScreen(const int rowNumber, const int seatNumber, const string screenId) {
    TheatreService* theatreServiceInstance = TheatreService::getTheatreServiceInstance();
    Screen* screen = theatreServiceInstance->getScreen(screenId);
    theatreServiceInstance->createSeatInScreen(rowNumber, seatNumber, screen);
}

#endif