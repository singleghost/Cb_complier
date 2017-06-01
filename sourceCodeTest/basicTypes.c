struct FILEP {
    int a;
};
union FILEA {
    char c;
    struct FILEP dd;
};

int main(void) {
    char a;
    unsigned char aa;
    short b;
    unsigned short bb;
    int c;
    unsigned int cc;
    long d;
    unsigned long dd;
    struct FILEP s;
    union FILEA u;
    void *p1;
    int *p2;
    char *p3;
    struct FILEP *p4;
    union FILEA *p5;

}