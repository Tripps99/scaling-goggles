#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

typedef struct{
    int to_N;
    double lenght;
}C;

typedef struct{
    int nm_node;
    C *cn;
    int p_ulic;

    double sum;
    int direction;
    bool exit;
}N;

void init_nodes(N node[], size_t p_exit_nodes, size_t nm)
{
    int i;
        for(i = 0; i < p_exit_nodes; i++){
            node[i].nm_node = i;
            node[i].sum = -1;
            node[i].direction = -1;
            node[i].exit = true;
        }
        for(i = p_exit_nodes;i<nm; i++)
        {
            node[i].nm_node = i;
            node[i].sum = 1000;
            node[i].direction = -1;
            node[i].exit = false;
        }
    int a;
        for(i = 0; i < nm; i++){
            node[i].p_ulic = rand()%5 + 1;
            node[i].cn = (C*)malloc(node[i].p_ulic*sizeof(C));
                for(a = 0; a < node[i].p_ulic; a++){
                    node[i].cn[a].lenght = rand()%10 + 1;
                    do
                    {
                        node[i].cn[a].to_N = rand()%nm;
                    }
                    while(node[i].cn[a].to_N==i);
                }
        }
}


void rec_exit(N node[], int i)
{
    int a;
        for(a = 0; a < node[i].p_ulic; a++){
            if((node[node[i].cn[a].to_N].sum >= (node[i].cn[a].lenght + node[i].sum)) && (node[node[i].cn[a].to_N].exit!=true))
            {
                printf("ted");
                node[node[i].cn[a].to_N].sum = node[i].cn[a].lenght + node[i].sum;
                node[node[i].cn[a].to_N].direction = i;
                rec_exit(node,(node[i].cn[a].to_N));
            }
        }
}


void find_the_exit(N node[], size_t p_exit_nodes, size_t nm)
{
    int i;

        for(i = 0; i < p_exit_nodes; i++){
            rec_exit(node,i);
        }
}

void show_status(N node[], size_t nm)
{
    int i, a;
    int lg;
        for(i = 0; i < nm; i++){
            printf("\n\n %d.Node :\n", i);
                for(a = 0; a < node[i].p_ulic; a++){
                    printf("conection to:%d, lenght of conection: %lf\n", node[i].cn[a].to_N, node[i].cn[a].lenght);
                }
                if(node[i].exit==true)
                {
                    printf("Already vystup\n");
                }
                else{printf("Escape to:%d\n", node[i].direction);}
        }

}

int main()
{
    srand(time(NULL));
    size_t nm;
    scanf("%d", &nm);
    N Node[nm];
    size_t p_exit_nodes = rand()%4 + 1;
    printf("XXXXXXXXXX%d",p_exit_nodes);

            init_nodes(Node,p_exit_nodes, nm);

                show_status(Node, nm);

                find_the_exit(Node, p_exit_nodes, nm);

                show_status(Node, nm);


    return 0;
}
