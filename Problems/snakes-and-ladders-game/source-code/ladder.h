#ifndef LADDER_H
#define LADDER_H

#include <iostream>
#include "jump.h"
#include "cell.h"
#include "board.h"
using namespace std;

class Ladder: public Jump {
    int startCell;
    int endCell;
public:
    Ladder();
    int getStartCellId();
    void setStartCell(int pId);
    int getEndCellId();
    void setEndCell(int pId);
};

Ladder::Ladder() {}

int Ladder::getStartCellId() {
    return startCell;
}

void Ladder::setStartCell(int pId) {
    startCell = pId;
}

int Ladder::getEndCellId() {
    return endCell;
}

void Ladder::setEndCell(int pId) {
    endCell = pId;
}

#endif