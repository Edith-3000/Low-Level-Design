#ifndef JUMP_H
#define JUMP_H

#include "cell.h"

class Jump {
public:
    virtual int getStartCellId() = 0;
    virtual void setStartCell(int pId) = 0;
    virtual int getEndCellId() = 0;
    virtual void setEndCell(int pId) = 0;
};

#endif