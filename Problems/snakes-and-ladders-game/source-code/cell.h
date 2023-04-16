#ifndef CELL_H
#define CELL_H

#include "jump.h"

class Cell {
    int id;
    Jump* jump;
public:
    Cell(int pId);
    void addJump(Jump* pJump);
    int getCellId();
    Jump* getJump();
};

Cell::Cell(int pId) {
    id = pId;
    jump = nullptr;
}

void Cell::addJump(Jump* pJump) {
    jump = pJump;
}

int Cell::getCellId() {
    return id;
}

Jump* Cell::getJump() {
    return jump;
}

#endif