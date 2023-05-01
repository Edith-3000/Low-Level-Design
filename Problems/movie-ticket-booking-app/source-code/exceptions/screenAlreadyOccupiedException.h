#ifndef SCREENALREADYOCCUPIEDEXCEPTION_H
#define SCREENALREADYOCCUPIEDEXCEPTION_H

#include<iostream>
using namespace std;

class ScreenAlreadyOccupiedException: exception {
public:
    string what();
};

string ScreenAlreadyOccupiedException::what() {
    return "Exception: screen already occupied";
}

#endif