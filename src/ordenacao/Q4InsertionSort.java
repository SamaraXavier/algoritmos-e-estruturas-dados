package ordenacao;


import java.util.Scanner;

class InsertionSort {

    public void insertionSort(int[] arr, int tamanhoArr) {
        for (int j = 1; j < tamanhoArr; j++) {
            int trocas = 0;
            int key = arr[j];
            int i = j - 1;
            while (i >= 0 && arr[i] > key) {
                arr[i + 1] = arr[i];
                i = i - 1;
                trocas++;
            }
            arr[i + 1] = key;
            System.out.println("Vetor apos iteracao:");
            imprimirVetor(arr, tamanhoArr);
            System.out.println("\nNumero de trocas: " + trocas + "\n");
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

public class Q4InsertionSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean sairSistema = false; //Variavel que indica se o usuario quer sair do sistema

        while (!sairSistema) {

            System.out.println("\n**************************************");
            System.out.println("** Bem-Vindo ao Algoritmo InsertionSort **");
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
            InsertionSort sort = new InsertionSort();

            System.out.println("\n\n-- Preencha o Vetor de Dados -- ");
            for (int i = 0; i < M; i++) {
                System.out.println("V[" + i + "] = ");
                vetor[i] = sc.nextInt();
            }

            System.out.println("\n\n** Vetor ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n\n*** InsertionSort *** ");
            sort.insertionSort(vetor, M);

            System.out.println("\n** Vetor Ordenado ** ");
            sort.imprimirVetor(vetor, M);

            System.out.println("\n\n\nDeseja sair do sistema? 1 - Sim || 2 - Nao");
            sairSistema = sc.nextInt() == 1;

        }
    }
}
