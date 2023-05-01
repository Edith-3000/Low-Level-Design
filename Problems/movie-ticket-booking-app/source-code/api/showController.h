#ifndef SHOWCONTROLLER_H
#define SHOWCONTROLLER_H

#include "movie.h"
#include "theatreService.h"
#include "movieService.h"
#include "showService.h"
#include "screen.h"

class ShowController {
public:
    ShowController();
    void createShow(const string movieId, const string screenId, const time_t startTime, const int durationInSeconds);
};

ShowController::ShowController() {}

void ShowController::createShow(const string movieId, const string screenId, const time_t startTime, 
                                const int durationInSeconds) {
    TheatreService* theatreServiceInstance = TheatreService::getTheatreServiceInstance();
    MovieService* movieServiceInstance = MovieService::getMovieServiceInstance();
    ShowService* showServiceInstance = ShowService::getShowServiceInstance();

    Movie* movie = movieServiceInstance->getMovie(movieId);
    Screen* screen = theatreServiceInstance->getScreen(screenId);

    showServiceInstance->createShow(movie, screen, startTime, durationInSeconds);
}

#endif