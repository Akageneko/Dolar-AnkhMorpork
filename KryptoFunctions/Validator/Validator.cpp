//
// Created by Matai on 22.10.2019.
//

#include "Validator.h"

bool Validator::Validate(std::string block, int number_of_zeros) {
    std::string hash = sha256(block);
    for(int i = 0; i<number_of_zeros ; i++){
        if(hash[i] != '0') return false;
    }
    return true;

}
