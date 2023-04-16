#ifndef BOARD_H
#define BOARD_H

#include <vector>
#include <mutex>
#include "cell.h"
using namespace std;

class Board {
    static Board* boardInstance;
    static mutex mtx;
    int totalCells;
    vector<Cell*> cells;

    // make default constructor as private
    Board();

    // make copy constructor as private
    Board(const Board&);

    // make assignment operator overloading as private
    Board& operator=(const Board&);
public:
    static Board* getBoardInstance();
    void addCell(int pCellId);
    Cell* getCell(int pCellId);
    int getTotalCells();
};

Board* Board::boardInstance = nullptr;

mutex Board::mtx;

Board::Board() {
    totalCells = 0;
}

Board* Board::getBoardInstance() {
    // Double check locking
    if(boardInstance == nullptr) {
        mtx.lock();
        if(boardInstance == nullptr) {
            boardInstance = new Board();
        }
        mtx.unlock();
    }

    return boardInstance;
}

void Board::addCell(int pCellId) {
    Cell* cell = new Cell(pCellId);
    cells.push_back(cell);
    totalCells += 1;
}

Cell* Board::getCell(int pCellId) {
    if((pCellId < 0) || (pCellId > totalCells)) throw ("Error: invalid cell id passed to Board::getCell()");
    return cells[pCellId - 1];
}

int Board::getTotalCells() {
    return totalCells;
}

#endif