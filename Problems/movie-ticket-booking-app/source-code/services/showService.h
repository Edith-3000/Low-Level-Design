#ifndef SHOWSERVICE_H
#define SHOWSERVICE_H

#include<map>
#include<memory>
#include<string>
#include<mutex>
#include "show.h"
#include "screenAlreadyOccupiedException.h"
#include"notFoundException.h"

#include <boost/uuid/uuid.hpp>            // uuid class
#include <boost/uuid/uuid_generators.hpp> // generators
#include <boost/uuid/uuid_io.hpp>         // streaming operators etc.

using namespace boost::uuids;
using namespace std;

class ShowService {
    static ShowService* showServiceInstance;
    static mutex mtx;
    map<string, shared_ptr<Show>> shows;

    ShowService();
    ShowService(const ShowService&);

public:
    static ShowService* getShowServiceInstance();
    void createShow(Movie* movie, Screen* screen, time_t startTime, int durationInSeconds);
    bool checkIfShowCreationAllowed(Screen* screen, time_t startTime, int durationInSeconds);
    Show* getShow(const string showId);
};

ShowService* ShowService::showServiceInstance = nullptr;

mutex ShowService::mtx;

ShowService::ShowService() {}

ShowService* ShowService::getShowServiceInstance() {
    // Double check locking
    if(showServiceInstance == nullptr) {
        mtx.lock();
        if(showServiceInstance == nullptr) {
            showServiceInstance = new ShowService();
        }
        mtx.unlock();
    }

    return showServiceInstance;
}

void createShow(Movie* movie, Screen* screen, time_t startTime, int durationInSeconds) {
    if(!checkIfShowCreationAllowed(screen, startTime, durationInSeconds)) {
        throw ScreenAlreadyOccupiedException();
    }
    
    random_generator gen;
    uuid id = gen();
    string strId = to_string(id);

    shared_ptr<Show> show = make_shared<Show>(movie, screen, startTime, durationInSeconds);
    
    shows.insert({strId, show});
}

bool ShowService::checkIfShowCreationAllowed(Screen* screen, time_t startTime, int durationInSeconds) {
    // TODO: Implement this. This method will return whether the screen is free at a particular time for
    // specific duration. This function will be helpful in checking whether the show can be scheduled in that slot
    // or not.
    return true;
}

Show* ShowService::getShow(const string showId) {
    if(shows.find(showId) == shows.end()) {
        throw NotFoundException();
    }

    return shows[showId].get();
}

#endif