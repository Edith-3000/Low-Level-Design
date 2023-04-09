#ifndef USER_H
#define USER_H

#include <string>
using namespace std;

class User {
    string userId;
public:
    User(string pUserId);
};

User::User(string pUserId) : userId(pUserId) {}

#endif