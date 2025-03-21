package ordenacao;

import java.util.Scanner;


class QuickSort {

    // Variavel para contar o numero de trocas realizadas pelo algoritmo
    protected int contTrocas = 0;


    public void quicksort(int[] vetor, int tamanhoVetor, int l, int r) {
        if (l < r) {
            int pivo = particionar(vetor, l, r);
            System.out.println("\nVetor apos particionamento: ");
            imprimirVetor(vetor, tamanhoVetor);
            quicksort(vetor, tamanhoVetor, l, pivo - 1);
            quicksort(vetor, tamanhoVetor, pivo + 1, r);
        }
    }

    public int particionar(int[] vetor, int l, int r) {
        int x = vetor[r];
        int i = l - 1;
        for (int j = l; j <= r - 1; j++) {
            if (vetor[j] <= x) {
                i = i + 1;
                trocar(vetor, i, j);
            }
        }

        trocar(vetor, i + 1, r);
        return i + 1;
    }

    public void trocar(int[] vetor, int n1, int n2) {
        int aux = vetor[n2];
        vetor[n2] = vetor[n1];
        vetor[n1] = aux;
        contTrocas++;
    }

    public void imprimirVetor(int[] vetor, int tamanhoVetor) {
        System.out.print("[");
        for (int i = 0; i < tamanhoVetor; i++) {
            System.out.print(vetor[i]);
            if (i < tamanhoVetor - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

}

public class Q2Quicksort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean sairSistema = false; //Variavel que indica se o usuario quer sair do sistema

        while (!sairSistema) {

            System.out.println("\n**************************************");
            System.out.println("** Bem-Vindo ao Algoritmo QuickSort **");
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
            QuickSort sort = new QuickSort();

            System.out.println("\n-- Preencha o Vetor de Dados -- ");
            for (int i = 0; i < M; i++) {
                System.out.println("V[" + i + "] = ");
                vetor[i] = sc.nextInt();
            }

            System.out.println("\n\n** Vetor ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n*** QuickSort *** ");
            sort.quicksort(vetor, M, 0, M - 1);

            System.out.println("\n\n** Vetor Ordenado ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n=> Numero total de vezes em que o procedimento trocar foi realizado: ");
            System.out.println(sort.contTrocas);

            System.out.println("\n\n\nDeseja sair do sistema? 1 - Sim || 2 - Nao");
            sairSistema = sc.nextInt() == 1;

        }
    }
}