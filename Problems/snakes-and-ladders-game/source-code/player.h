#ifndef PLAYER_H
#define PLAYER_H

#include <string>
#include <iostream>
#include "cell.h"
using namespace std;

class Player {
    int id;
    string name;
    Cell* position;
public:
    Player(int pId, string pName);
    int getPlayerId();
    string getPlayerName();
    int getPlayerPosition();
    void setPlayerPosition(int pPositionId);
};

Player::Player(int pId, string pName) {
    id = pId;
    name = pName;
    Cell* firstCellPos = nullptr;

    try {
        Board* board = Board::getBoardInstance();
        firstCellPos = board->getCell(1);
    } 
    catch (string exception) {
        cout << exception << endl;
    }

    position = firstCellPos;
}

int Player::getPlayerId() {
    return id;
}

string Player::getPlayerName() {
    return name;
}

int Player::getPlayerPosition() {
    return position->getCellId();
}

void Player::setPlayerPosition(int pPositionId) {
    Cell* cell = nullptr;

    try {
        Board* board = Board::getBoardInstance();
        cell = board->getCell(pPositionId);
    } 
    catch (string exception) {
        cout << exception << endl;
    }

    position = cell;
}

#endif