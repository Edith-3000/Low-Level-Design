#ifndef SHOW_H
#define SHOW_H

#include<string>
#include<ctime>
#include "movie.h"
#include "screen.h"
using namespace std;

class Show {
    const string id;
    const Movie* movie;
    const Screen* screen;
    const time_t startTime;
    const int durationInSeconds;

public:
    Show(const string id, const Movie* movie, const Screen* screen, const time_t startTime, 
         const int durationInSeconds);
};

Show::Show(const string id, const Movie* movie, const Screen* screen, const time_t startTime,
           const int durationInSeconds): id(id), movie(movie), screen(screen), startTime(startTime), 
           durationInSeconds(durationInSeconds) {}

#endif