#ifndef SCREEN_H
#define SCREEN_H

#include<string>
#include "theatre.h"
#include "seat.h"
using namespace std;

class Screen {
    const string id;
    const string name;
    Theatre* theatre;
    vector<Seat*> seats;
    // Other screen metadata.

public:
    Screen(const string id, const string name, Theatre* theatre);
    void addSeat(Seat* seat);
};

Screen::Screen(const string id, const string name, Theatre* theatre): id(id), name(name), 
                                                                      theatre(theatre) {}

void Screen::addSeat(Seat* seat) {
    seats.push_back(seat);
}

#endif