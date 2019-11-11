//
// Created by Matai on 22.10.2019.
//

#include "Generator.h"

std::string Generator::Insert_Nonce(std::string block, unsigned int nonce) {
    // Find first occurrence of '<NONCE>{'
    size_t found = block.find("<NONCE>{");
    size_t prev_len;
    if(nonce-1 < 0) {
        prev_len = 1;
    }else{
        prev_len = std::to_string(nonce).length();
    }
    if (found != std::string::npos){
        found =+8;
        if(block.find("<NONCE>{}") == std::string::npos){
            block.insert(found, std::to_string(nonce));
        }else{
            block.replace(found,prev_len,  std::to_string(nonce));
        }
    }
    return block;

}

bool Generator::Check_Hash(std::string hash, int number_of_zeros) {
    for(int i = 0; i<number_of_zeros ; i++){
        if(hash[i] != '0') return false;
    }
    return true;
}

unsigned int Generator::Mine(std::string block, int number_of_zeros) {

    std::mt19937 gen(time(nullptr) );
    std::uniform_int_distribution<unsigned int> distribution(0, UINT32_MAX);

    //generating a random integer:
    unsigned int  nonce;
    std::string sha_hash;
    do{
        nonce = distribution(gen);
        block = Insert_Nonce(block, nonce);
        sha_hash = sha256(block);
    }while(Check_Hash(sha_hash, number_of_zeros));


    return nonce;
}


