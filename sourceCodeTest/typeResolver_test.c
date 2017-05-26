struct TESTST {
    int a;
    char c;
};

union TestUnion {
    long tu;
    struct TESTST st;
    char ch;
};

typedef union TestUnion TestUnion;

const unsigned int constant = 199;

int main(void) {
    struct TESTST testst;
    TestUnion aaa;
}

void add(TestUnion *p, TestUnion [] aunion, struct TESTST aaa, union TestUnion ooo)
{
    *((int *)p) = 100;
    aunion[1].tu = -0x99a;
}
