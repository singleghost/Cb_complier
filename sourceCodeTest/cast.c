
int main(void){
    int a = 0;
    int [10] a;
    void *p;
    struct FILEP st;

    a = (int)'c';
    p = (int *)&a;
    p = (int **)a;
    p = (struct st*)&st;

}