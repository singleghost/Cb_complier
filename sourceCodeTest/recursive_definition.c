typedef union TestUnion tunion;
struct TESTST {
    int a;
    char c;
    tunion uuu;
};

struct recur_st {
    struct TESTST aaa;
};
union TestUnion {
    long tu;
    struct TESTST st;
    char ch;
};


//struct Arr_st {
//    int a;
//    struct Arr_st [7] b;
//};
//
//struct Arr_st [7] array;
int main(void) {
}

