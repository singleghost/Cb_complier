int add(int a, int b)
{
    return a + b;
}
int main(void)
{
    char [20] str = "\0\"\'\a\b\e\f\n\r\t\v\\hello world\020";  //数组采用的是前置记法
    int a = 6;
    char c = 'b';
    long d = 100000;
    void *p = &d;
    int (int, int) p = add;
    //上面是变量定义,下面是语句,两者不能混杂在一起!!
    d = 1;
    a = (int)9;
}