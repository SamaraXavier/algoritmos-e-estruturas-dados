package ordenacao;

import java.util.Scanner;

import static java.lang.Math.floor;

class HeapSort {

    //Variaveis para contar o numero de chamadas dos procedimentos "trocar" e "maxHeapify" realizadas pelo algoritmo
    protected int contadorTrocas;
    protected int contadorMaxHeapify;


    public int retornarIndiceFilhoEsquerda(int i) {
        return (2 * i) + 1;
    }

    public int retornarIndiceFilhoDireita(int i) {
        return (2 * i) + 2;
    }

    public void maxHeapfy(int[] arr, int i, int heapSize) {
        contadorMaxHeapify++;
        int l = retornarIndiceFilhoEsquerda(i);
        int r = retornarIndiceFilhoDireita(i);
        int maior;
        if (l <= heapSize && arr[l] > arr[i]) {
            maior = l;
        } else {
            maior = i;
        }
        if (r <= heapSize && arr[r] > arr[maior]) {
            maior = r;
        }
        if (maior != i) {
            trocar(arr, i, maior); //Trocar o conteudo das posicoes do vetor
            maxHeapfy(arr, maior, heapSize);
        }
    }

    public void construirMaxHeap(int[] arr, int heapSize) {
        //floor-> funcao piso (arredonda para baixo)
        for (int i = (int) floor(heapSize / 2); i >= 0; i--) {
            maxHeapfy(arr, i, heapSize);
        }
    }

    public void heapSort(int[] arr, int tamanhoArr) {
        int heapSize = tamanhoArr - 1; //Ultimo indice com elemento do vetor
        construirMaxHeap(arr, heapSize);
        System.out.println("\nVetor apos a construcao do Max-Heap:");
        imprimirVetor(arr, tamanhoArr);
        System.out.println("\n");

        for (int i = tamanhoArr - 1; i > 0; i--) {
            trocar(arr, 0, i);
            heapSize = heapSize - 1;
            maxHeapfy(arr, 0, heapSize);
            System.out.println("\nVetor apos maxHeapfy:");
            imprimirVetor(arr, tamanhoArr);
        }

    }

    public void trocar(int[] arr, int n1, int n2) {
        int aux = arr[n2];
        arr[n2] = arr[n1];
        arr[n1] = aux;
        contadorTrocas++;
    }


    public void imprimirVetor(int[] arr, int tamanhoVetor) {
        System.out.print("[");
        for (int i = 0; i < tamanhoVetor; i++) {
            System.out.print(arr[i]);
            if (i < tamanhoVetor - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }
}

public class Q3Heapsort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean sairSistema = false; //Variavel que indica se o usuario quer sair do sistema

        while (!sairSistema) {

            System.out.println("\n**************************************");
            System.out.println("** Bem-Vindo ao Algoritmo HeapSort **");
            System.out.println("**************************************");

            boolean tamanhoValido = false;
            int M = 0;

            while (!tamanhoValido) {
                System.out.println("\nDigite o tamanho do vetor: ");
                M = sc.nextInt();

                if (M > 0) {
                    tamanhoValido = true;
                } else {
                    System.out.println(" >>Tamanho invalido! Tente novamente.");
                }
            }

            int[] vetor = new int[M];
            HeapSort sort = new HeapSort();

            System.out.println("\n-- Preencha o Vetor de Dados -- ");
            for (int i = 0; i < M; i++) {
                System.out.println("V[" + i + "] = ");
                vetor[i] = sc.nextInt();
            }


            System.out.println("\n\n** Vetor ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n\n*** Heapsort *** ");
            sort.heapSort(vetor, M);

            System.out.println("\n\n\n** Vetor Ordenado ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n=> Numero de total de vezes em que o procedimento trocar foi realizado: ");
            System.out.println(sort.contadorTrocas);

            System.out.println("\n=> Numero de total de vezes em que o procedimento maxHeapfy foi realizado: ");
            System.out.println(sort.contadorMaxHeapify);

            System.out.println("\n\n\nDeseja sair do sistema? 1 - Sim || 2 - Nao");
            sairSistema = sc.nextInt() == 1;

        }
    }
}
