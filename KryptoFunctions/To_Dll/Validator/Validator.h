//
// Created by Matai on 22.10.2019.
//

#ifndef DOLAR_ANKHMORPORK_VALIDATOR_H
#define DOLAR_ANKHMORPORK_VALIDATOR_H

#include <string>
#include "sha256.h"

class Validator {


public :
    bool Validate(std::string block, int number_of_zeros);
};


#endif //DOLAR_ANKHMORPORK_VALIDATOR_H
