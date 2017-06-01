int main(void){
    int a = 0;
    char [10] cc;
    a = sizeof(int *);
    a = sizeof(int);
    a = sizeof(cc); //这里和 C 语言不同，sizeof (数组)的时候是把数组名当成指针来看待的，返回的是指针类型的大小
    a = sizeof(cc[9]);
}