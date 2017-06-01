struct FILEP {
    int fd;
    char *start;
};
int main(void) {
    int [10] a;
    char *str = "abcdef";
    struct FILEP st;
    struct FILEP *p = &st;
    struct FILEP [100][100] ast;
    a[0] = 100;
    st.fd = 1;
    p->start = str;
    ast[1][2].start = str;
}