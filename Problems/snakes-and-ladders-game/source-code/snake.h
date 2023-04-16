#ifndef SNAKE_H
#define SNAKE_H

#include <iostream>
#include "jump.h"
#include "cell.h"
#include "board.h"
using namespace std;

class Snake: public Jump {
    int startCell;
    int endCell;
public:
    Snake();
    int getStartCellId();
    void setStartCell(int pId);
    int getEndCellId();
    void setEndCell(int pId);
};

Snake::Snake() {}

int Snake::getStartCellId() {
    return startCell;
}

void Snake::setStartCell(int pId) {
    startCell = pId;
}

int Snake::getEndCellId() {
    return endCell;
}

void Snake::setEndCell(int pId) {
    endCell = pId;
}

#endif