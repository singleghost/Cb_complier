import stdio;

int add(int a, int b) {
    return a + b;
}
int main(void){
    int i;
    int x = 0;
    char c = 'a';
    long [10] arr;
    while(1) {
        printf("hello");
        x++;
        if(x > 10)
            if(i >= 0)
                break;
            else
                continue;
        else
            add(1,2);
    }

    do {
        x--;
    } while(x);

    switch(c) {
        case 'a':
        case 'b':
        case 'd':
                printf("aaa");
                break;
        case 'c':
            printf("ddd");
            break;
        default:
            x++;
    }

    for(i = 0; i < 10; i++) {
        int c = 10;
        char [1] a;
        arr[i] = ~i;
    }
}