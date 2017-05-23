int main(void){
    int a = 100;
    short b;
    int i,j = 11;
    for(i = 0; i < 100; i++)
        for(j = 10; j; j--) {
            a += 1;
            if(a == 1000) goto LABEL_END;
        }

    return add(1, 2);
    LABEL_END:
        return 0;

}