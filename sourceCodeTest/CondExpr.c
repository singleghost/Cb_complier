int main(void)
{
    int a;
    long b = 5;
    char c = 'a';

    a = b > 0 ? 1 : 10;
    a = b > 0 ? c : a - 1;
}