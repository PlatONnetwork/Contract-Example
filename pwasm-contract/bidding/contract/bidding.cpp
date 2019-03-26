//auto create contract

#include <stdlib.h>
#include <string.h>
#include <string>

#include <platon/platon.hpp>

PLATON_EVENT(bidding, int32_t);

namespace bidding {
    class BiddingContract : public platon::Contract {
        public:
            int calc(uint64_t a, uint64_t b, uint64_t c, uint64_t p) {
                double y = (a+b+c) / 3.0;
                double z = (y+p) / 2.0;

                double arr[3];
                arr[0] = z - a;
                arr[1] = z - b;
                arr[2] = z - c;

                double min = arr[0];
                int idx = -1;
                if (min >= 0.0f) {
                    idx = 1;
                }
                for (int i = 1; i < 3; i++) {
                    if (arr[i] >= 0.0f && (arr[i] < min || min < 0.0f))  {
                        min = arr[i];
                        idx = i+1;
                        continue;
                    }
                }
                platon::println("arr[0]", arr[0], "arr[1]", arr[1], "arr[2]", arr[2], "idx", idx);
                if (idx < 0){
                    PLATON_EMIT_EVENT(bidding, 0);
                    return 0;
                }
                PLATON_EMIT_EVENT(bidding, idx);
                return idx;
            }
    };
}

PLATON_ABI(bidding::BiddingContract, calc);
