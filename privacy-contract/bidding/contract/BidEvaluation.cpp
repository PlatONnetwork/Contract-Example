#include "integer.h"

#include "code/BidEvaluation.pb.h"
using namespace BidEvaluation;

/*
Alice a,b,c
Bob p

plang64.exe -config config.json -I. -Iinclude -Icode BidEvaluation.cpp -protobuf-cc code/BidEvaluation.pb.cc -o code/BidEvaluation.cpp.ll -mpcc code/BidEvaluation.mpcc.cpp

mkdir -p code
protoc --cpp_out=./code BidEvaluation.proto
protoc --java_out=./code BidEvaluation.proto
plang -config config.json -I. -Iinclude -Icode BidEvaluation.cpp -protobuf-cc code/BidEvaluation.pb.cc -protobuf-java code/network/platon/bidevaluation/BidEvaluation.java -o code/BidEvaluation.cpp.ll -mpcc code/BidEvaluation.mpcc.cpp

*/
int BidEvaluationResult(Bidder bidder, int price) {
    printf("Bidder: %d, %d, %d\n", bidder.a(), bidder.b(), bidder.c());
    printf(" price: %d\n", price);
    
    emp::Integer _a(bidder.a(), emp::ALICE);
    emp::Integer _b(bidder.b(), emp::ALICE);
    emp::Integer _c(bidder.c(), emp::ALICE);
    
    emp::Integer _p(price, emp::BOB);
    
    // algorithm
    emp::Integer _y = (_a + _b + _c) / 3;
    emp::Integer _z = (_y + _p) / 2;
    
    emp::Integer arr[3] = {_a,_b,_c};
    int idx[3] = {1,2,3};
    if((arr[0] - arr[1]).reveal_int() > 0) {emp::Integer t=arr[0];arr[0]=arr[1];arr[1]=t; int i=idx[0];idx[0]=idx[1];idx[1]=i;}
    if((arr[1] - arr[2]).reveal_int() > 0) {emp::Integer t=arr[1];arr[1]=arr[2];arr[2]=t; int i=idx[1];idx[1]=idx[2];idx[2]=i;}
    if((arr[0] - arr[1]).reveal_int() > 0) {emp::Integer t=arr[0];arr[0]=arr[1];arr[1]=t; int i=idx[0];idx[0]=idx[1];idx[1]=i;}
    
    int i;
    for (i = 0; i<3; i++){
        if ((arr[i] - _z).reveal_int() > 0) break;
    }
    
    printf(" idx: %d %d %d\n", idx[0], idx[1], idx[2]);
    printf(" i: %d\n", i);
    
    if (i == 0) return 0; // all > z;
    i--;
    if ((i > 0) && ((arr[i] - arr[i-1]).reveal_int() == 0)) return 0; // two or more bidders have the same price
    return idx[i];
}

