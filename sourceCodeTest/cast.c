struct FILEP {
    unsigned char a;
};

int main(int argc, char **argv){
    int aa = 0;
    int [10] a;
    void *p;
    struct FILEP st;

    aa = (int)'c';
    p = (int *)&a;
    p = (int **)a;
    p = (struct st*)&st;

}