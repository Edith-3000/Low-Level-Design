#ifndef MOVIE_H
#define MOVIE_H

#include<string>
using namespace std;

class Movie {
    const string id;
    const string name;

public:
    Movie(const string id, const string name) {};
};

// https://stackoverflow.com/questions/1423696/how-to-initialize-a-const-field-in-constructor
Movie::Movie(const string id, const string name): id(id), name(name) {};

#endif