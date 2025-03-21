package ordenacao;

import java.util.Scanner;

import static java.lang.Math.floor;


class MergeSort {

    public void merge(int[] vetor, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = vetor[p + i];
        }

        for (int j = 0; j < n2; j++) {
            R[j] = vetor[q + j + 1];
        }

        int i = 0;
        int j = 0;
        int k = p;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                vetor[k] = L[i];
                i++;
            } else {
                vetor[k] = R[j];
                j++;
            }
            k++;

        }

        while (i < n1) {
            vetor[k] = L[i];
            k++;
            i++;
        }

        while (j < n2) {
            vetor[k] = R[j];
            j++;
            k++;
        }

    }

    public void mergesort(int[] vetor, int tamanhoVetor, int p, int r) {
        if (p < r) {
            int q = (int) floor((p + r) / 2); //floor-> funcao piso (arredonda para baixo)
            mergesort(vetor, tamanhoVetor, p, q);
            mergesort(vetor, tamanhoVetor, q + 1, r);
            merge(vetor, p, q, r);
            System.out.println("\nVetor apos merge:");
            imprimirVetor(vetor, tamanhoVetor);
        }
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

public class Q1Mergesort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean sairSistema = false; //Variavel que indica se o usuario quer sair do sistema

        while (!sairSistema) {

            System.out.println("\n**************************************");
            System.out.println("** Bem-Vindo ao Algoritmo MergeSort **");
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
            MergeSort sort = new MergeSort();

            System.out.println("\n\n-- Preencha o Vetor de Dados -- ");
            for (int i = 0; i < M; i++) {
                System.out.println("V[" + i + "] = ");
                vetor[i] = sc.nextInt();
            }

            System.out.println("\n\n** Vetor ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n*** MergeSort *** ");
            sort.mergesort(vetor, M, 0, M - 1);

            System.out.println("\n\n** Vetor Ordenado ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n\nDeseja sair do sistema? 1 - Sim || 2 - Nao");
            sairSistema = sc.nextInt() == 1;

        }
    }
}
