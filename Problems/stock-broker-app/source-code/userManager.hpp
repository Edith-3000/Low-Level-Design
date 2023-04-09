#ifndef USERMANAGER_H
#define USERMANAGER_H

#include<unordered_map>
#include "user.hpp"

using namespace std;

class UserManager {
    static unordered_map<string, User*> users;
public:
    static void addUser(string userId, User* user);
    static User* getUser(string userId);
};

unordered_map<string, User*> UserManager::users;

void UserManager::addUser(string userId, User* user) {
    users[userId] = user;
}

User* UserManager::getUser(string userId) {
    if(users.find(userId) == users.end()) return nullptr;
    else return users[userId];
}

#endif