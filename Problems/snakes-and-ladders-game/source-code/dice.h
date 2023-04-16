#ifndef DICE_H
#define DICE_H

#include <ctime>
#include <cstdlib>

class Dice {
    int diceCount;
public:
    Dice(int);
    int rollDice();
};

Dice::Dice(int diceCount) {
    this->diceCount = diceCount;
}

// https://www.digitalocean.com/community/tutorials/random-number-generator-c-plus-plus
// https://www.programiz.com/cpp-programming/library-function/ctime/time
int Dice::rollDice() {
    // Instead of providing a seed here, it's better to do it in the main() function
    // time_t currentTime = time(NULL);

    // // Providing a seed value
    // srand((unsigned int) currentTime);

    int lowerBound = diceCount;
    int upperBound = 6 * diceCount;
    int range = upperBound - lowerBound + 1;

    // Get the random number
    int randomValue = lowerBound + (rand() % range);

    return randomValue;
}

#endif