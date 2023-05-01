#ifndef MOVIECONTROLLER_H
#define MOVIECONTROLLER_H

#include<string>
#include "movieService.h"
using namespace std;

class MovieController {
public:
    MovieController();
    void createMovie(const string movieName);
};

MovieController::MovieController() {};

void MovieController::createMovie(const string movieName) {
    MovieService* movieServiceInstance = MovieService::getMovieServiceInstance();
    movieServiceInstance->createMovie(movieName);
}

#endif