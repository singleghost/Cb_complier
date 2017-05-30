struct hey_st {
    int b;
};
union hey_union {
    struct hey_st aaa;
};
struct dup_st {
    int aa;
    int aa;
    long bb;
    char bb;
    struct hey_st hey;
    union hey_union hey;
};