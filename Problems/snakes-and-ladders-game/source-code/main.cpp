#include "game.h"
#include "board.h"
#include "snake.h"
#include "ladder.h"

int main() {
    time_t currentTime = time(NULL);

    // Providing a seed value
    srand((unsigned int) currentTime);

    Board* board = Board::getBoardInstance();

    // Add cells to the board
    for(int i = 1; i <= 30; i++) {
        board->addCell(i);
    }

    Game* game = new Game(1);

    // Add players
    game->addPlayer(1, "Chhota Bheem");
    game->addPlayer(2, "Shinchan");
    game->addPlayer(3, "Po");
    game->addPlayer(4, "Mantis");
    game->addPlayer(5, "Lone Wolf");
    game->addPlayer(6, "Master Shifu");

    // Add several "Snake" jumps
    Snake* snake1 = new Snake();
    snake1->setStartCell(8);
    snake1->setEndCell(2);
    game->addJump(snake1);

    Snake* snake2 = new Snake();
    snake2->setStartCell(17);
    snake2->setEndCell(3);
    game->addJump(snake2);

    // Add several "Ladder" jumps
    Ladder* ladder1 = new Ladder();
    ladder1->setStartCell(6);
    ladder1->setEndCell(10);
    game->addJump(ladder1);

    Ladder* ladder2 = new Ladder();
    ladder2->setStartCell(11);
    ladder2->setEndCell(16);
    game->addJump(ladder2);

    game->startGame();

    return 0;
}