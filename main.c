#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

typedef struct{
    int to_N;
    double lenght_con;
}C;

typedef struct{
    int nm_node;
    C *cn;
    int p_ulic;

    double sum;
    int direction;
    bool way;
}N;

void in_node(N node[], size_t nm)
{
    int i;
        for(i = 0; i < nm; i++){
            node[i].nm_node = i;
            node[i].sum = 0;
            node[i].direction = -1;
            node[i].way = true;
        }
}

void set_data_in(N node[], size_t nm)
{
    int i, a;
    int n;
    const int exits = rand()%4 + 1;
    printf("pocet vychodu %d",exits);

        for(i = 0; i < nm; i++){
            n = rand()%5 + 1;
            node[i].p_ulic = n;
            node[i].cn = (C*)malloc(n*sizeof(C));
                for(a = 0; a < n; a++){
                    node[i].cn[a].lenght_con = rand()%10 + 1;
                    node[i].cn[a].to_N = rand()%nm;
                    if(i < exits)node[i].way = false;
                }
        }


}

void find_the_way(N node[], size_t nm)
{
    int i = 0;
    int a;
    int lg;

        while(node[i].way == false){
            lg = node[i].p_ulic;
             printf("x%d\n", lg);
            for(a = 0; a < lg; a++){
                if(node[i].cn[a].lenght_con - node[node[i].cn[a].to_N].sum > 0 || a == 0){
                    node[node[i].cn[a].to_N].sum += node[i].cn[a].lenght_con - node[node[i].cn[a].to_N].sum;
                    node[node[i].cn[a].to_N].direction = node[i].nm_node;
                }

            }
            i++;
        }
}

void show_status(N node[], size_t nm)
{
    int i, a;
    int lg;
        for(i = 0; i < nm; i++){
            printf("%d.Node :", i);
            lg = node[i].p_ulic;

                for(a = 0; a < lg; a++){
                    printf("conection to:%d, lenght of conection: %lf\n", node[i].cn[a].to_N, node[i].cn[a].lenght_con);
                }
                printf("Escape to:%d\n", node[i].direction);
        }

}

int main()
{
    srand(time(NULL));
    size_t nm;
        scanf("%d", &nm);
        N Node[nm];

            in_node(Node, nm);
            set_data_in(Node, nm);

                show_status(Node, nm);

                find_the_way(Node, nm);
                    show_status(Node, nm);


    return 0;
}
