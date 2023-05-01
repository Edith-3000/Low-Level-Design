#ifndef THEATRE_H
#define THEATRE_H

#include<string>
#include<vector>
#include "screen.h"
using namespace std;

class Theatre {
    const string id;
    const string name;
    vector<Screen*> screens;
    // Other theatre metadata.

public:
    Theatre(const string id, const string name);
    void addScreen(Screen* screen);
};

Theatre::Theatre(const string id, const string name): id(id), name(name) {}

void Theatre::addScreen(Screen* screen) {
    screens.push_back(screen);
}

#endif