#ifndef MOVIESERVICE_H
#define MOVIESERVICE_H

#include<map>
#include<string>
#include<memory>
#include "movie.h"
#include<notFoundException.h>

#include <boost/uuid/uuid.hpp>            // uuid class
#include <boost/uuid/uuid_generators.hpp> // generators
#include <boost/uuid/uuid_io.hpp>         // streaming operators etc.

using namespace std;
using namespace boost::uuids;

class MovieService {
    static MovieService* movieServiceInstance;
    static mutex mtx;
    map<string, shared_ptr<Movie>> movies;

    MovieService();
    MovieService(const MovieService&);

public:
    static MovieService* getMovieServiceInstance();
    void createMovie(const string movieName);
    Movie* getMovie(const string movieId);
};

MovieService* MovieService::movieServiceInstance = nullptr;

mutex MovieService::mtx;

MovieService::MovieService() {}

MovieService* MovieService::getMovieServiceInstance() {
    // Double check locking
    if(movieServiceInstance == nullptr) {
        mtx.lock();
        if(movieServiceInstance == nullptr) {
            movieServiceInstance = new MovieService();
        }
        mtx.unlock();
    }

    return movieServiceInstance;
}

void MovieService::createMovie(const string movieName) {
    random_generator gen;
    uuid id = gen();
    string strId = to_string(id);

    shared_ptr<Movie> movie = make_shared<Movie>(strId, movieName);
    
    // This can also be used, but always try to create smart pointer using above method
    // shared_ptr<Movie> movie(new Movie(strId, movieName));

    movies.insert({strId, movie});
}

Movie* MovieService::getMovie(const string movieId) {
    if(movies.find(movieId) == movies.end()) {
        throw NotFoundException();
    }

    return movies[movieId].get();
}

#endif