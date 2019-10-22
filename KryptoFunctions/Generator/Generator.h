//
// Created by Matai on 22.10.2019.
//

#ifndef DOLAR_ANKHMORPORK_GENERATOR_H
#define DOLAR_ANKHMORPORK_GENERATOR_H
#include <string>
#include <random>
#include <ctime>
#include "sha256.h"
class Generator {
private:
    std::string static Insert_Nonce(std::string block, unsigned int nonce);
    static bool Check_Hash(std::string block, int number_of_zeros);

public:
    unsigned int Mine(std::string block, int number_of_zeros);


};


#endif //DOLAR_ANKHMORPORK_GENERATOR_H
