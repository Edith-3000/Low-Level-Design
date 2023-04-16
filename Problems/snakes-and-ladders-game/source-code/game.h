#ifndef GAME_H
#define GAME_H

#include <queue>
#include "dice.h"
#include "board.h"
#include "player.h"
using namespace std;

class Game {
    Dice* dice;
    Board* board;
    Player* winner;
    queue<Player*> playersQueue;
    Player* findNextTurnPlayer();
    bool isJumpPresent(int pPositionId);
public:
    Game(int diceCount);
    void addPlayer(int pPlayerId, string pPlayerName);
    void addJump(Jump* pJump);
    void startGame();
};

Player* Game::findNextTurnPlayer() {
    Player* nextTurnPlayer = playersQueue.front();
    
    playersQueue.pop();
    playersQueue.push(nextTurnPlayer);

    return nextTurnPlayer;
}

bool Game::isJumpPresent(int pPositionId) {
    Cell* cell = board->getCell(pPositionId);
    return (cell->getJump() != nullptr); 
}

Game::Game(int diceCount) {
    dice = new Dice(diceCount);
    board = Board::getBoardInstance();
    winner = nullptr;
}

void Game::addPlayer(int pPlayerId, string pPlayerName) {
    Player* newPLayer = new Player(pPlayerId, pPlayerName);
    playersQueue.push(newPLayer);
}

void Game::addJump(Jump* pJump) {
    int startCellId = pJump->getStartCellId();
    int endCellId = pJump->getEndCellId();

    Cell* startCell = board->getCell(startCellId);
    startCell->addJump(pJump);

    Cell* endCell = board->getCell(endCellId);
    endCell->addJump(pJump);
}

void Game::startGame() {
    while(winner == nullptr) {
        // Find which player's turn is this 
        Player* curplayer = findNextTurnPlayer();

        // Print for logging purpose
        cout << "Current player - ";
        cout << "id: " << curplayer->getPlayerId() << ", ";
        cout << "name: '" << curplayer->getPlayerName() << "', ";
        cout << "position: " << curplayer->getPlayerPosition() << endl;

        // Roll the dice
        int score = dice->rollDice();
        cout << "Dice rolled - score: " << score << endl;

        // Get new position
        int nextPosition = curplayer->getPlayerPosition() + score;

        if(nextPosition > board->getTotalCells()) {
            cout << "Cannot move this player" << endl;
            cout << endl;
            continue;
        }

        // Check for jumps
        if(isJumpPresent(nextPosition)) {
            Cell* cell = board->getCell(nextPosition);
            Jump* jump = cell->getJump();

            int start = jump->getStartCellId();
            int end = jump->getEndCellId();

            if(start < end) {
                cout << "Moved up by LADDER" << endl;
                curplayer->setPlayerPosition(end);
            } else {
                cout << "Bitten by SNAKE" << endl;
                curplayer->setPlayerPosition(end);
            }
        }

        else {
            curplayer->setPlayerPosition(nextPosition);
        }      

        cout << "Player's new position is: " << curplayer->getPlayerPosition() << endl;

        cout << endl;

        // Check for winning position
        if(curplayer->getPlayerPosition() == board->getTotalCells()) {
            winner = curplayer;
        }
    }

    cout << "Winner is - ";
    cout << "id: " << winner->getPlayerId() << ", ";
    cout << "name: '" << winner->getPlayerName() << "'" << endl;

    cout << endl;
}

#endif