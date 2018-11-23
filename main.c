#include <stdio.h>
#include <stdlib.h>
#include "C:\Users\Matus\Desktop\Coding\Headers\D_l_c_l.h"

typedef struct{
    int to_N;
    double lenght_con;
}C;

typedef struct{
    int nm_node;
    C *cn;
    double sum;
    int way;
}N;

void in_node(N node[], size_t nm)
{
    int i;
        for(i = 0; i < nm; i++){
            node[i].nm_node = i;
            node[i].cn = (C*)malloc(sizeof(C));
            node[i].sum = 0;

        }
}

void set_data_in(N node[], size_t nm)
{
    int i, a;
    int n;
        for(i = 0; i < nm; i++){
            n = rand()%5 + 1;
            node[i].cn = (C*)realloc(node[i].cn, n);
                for(a = 0; a < n; a++){
                    node[i].cn[a].lenght_con = rand()%10 + 1;
                    node[i].cn[a].to_N = rand()%nm;
                }
        }
}

int main()
{
    srand(time(NULL));
    size_t nm;
        scanf("%d", &nm);
        N Node[nm];

            in_node(Node, nm);


    return 0;
}
