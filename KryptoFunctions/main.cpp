//
// Created by Matai on 22.10.2019.
//
#include <iostream>
#include "sha256.h"
#include "Generator.h"
#include "Validator.h"


using std::string;
using std::cout;
using std::endl;


struct TRANSACTION {
    string sender_s = "<SENDER_PUBLIC_KEY>";
    string sender;
    string reciver_s = "<RECIVER_PUBLIC_KEY>";
    string reciver;
    string amount_s = "<AMOUNT>";
    string amount;
    string signature_s = "<SIGNATURE>";
    string signature;
    string open_field = "{";
    string close_field = "}";
};

template <int number_of_transactions>
struct BLOCK{
    string nonce_s = "<NONCE>";
    string nonce;
    string previous_s = "<PREVIOUS_BLOCK_HASH>";
    string previous;
    TRANSACTION transactions[number_of_transactions];
};



int main(int argc, char *argv[])
{

    string input = "ENYTHING";
    string output1 = sha256(input);
    cout << "sha256('"<< input << "'):" << output1 << endl;




    return 0;
}


