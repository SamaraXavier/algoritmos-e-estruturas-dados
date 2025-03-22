package caracteres_strings;

import java.util.Scanner;

class PilhaCharSeq {
    protected int top;
    protected int size;
    protected char[] stack;

    public PilhaCharSeq(int size) {
        this.top = -1;
        this.size = size;
        this.stack = new char[size];
    }

    public void push(char c) {
        if (top != size - 1) {
            top++;
            stack[top] = c;
        }
    }

    public char pop() {
        char result = ' ';
        if (top > -1) {
            result = stack[top];
            top--;
        }
        return result;
    }
}

class AvaliarCadeiacaractere {

    public boolean avaliacaoCadeiacaractere(String s) {
        boolean avaliado = true;
        char[] cadeia = s.toCharArray(); //Funcao para transforma a cadeia de caracteres (string) em um vetor de caracteres
        int n = 0; //Variavel para armazenar o tamanho do vetor

        //For-each para sabermos o tamanho do vetor
        for (char c : cadeia) {
            n++;
        }

        PilhaCharSeq pilha = new PilhaCharSeq(n);
        int i = 0;

        while (i < n && avaliado) {
            if (cadeia[i] == '{' || cadeia[i] == '[' || cadeia[i] == '(') {
                pilha.push(cadeia[i]);
            } else if (cadeia[i] == '}' || cadeia[i] == ']' || cadeia[i] == ')') {
                char q = pilha.pop();
                if (cadeia[i] == '}' && q != '{') {
                    avaliado = false;
                } else if (cadeia[i] == ']' && q != '[') {
                    avaliado = false;
                } else if (cadeia[i] == ')' && q != '(') {
                    avaliado = false;
                }
            }

            i = i + 1;
        }

        if (pilha.top != -1) {
            avaliado = false;
        }

        return avaliado;
    }
}

public class FormacaoCaracteres {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AvaliarCadeiacaractere av = new AvaliarCadeiacaractere();
        char escolha = 's';

        while (escolha != 'n' && escolha != 'N') {
            System.out.println("\n**************************************");
            System.out.println("** Avaliando Cadeias de Caracteres **");
            System.out.println("**************************************");
            System.out.println("\nInsira a cadeia de caracteres a ser avaliada: ");
            String cadeia = sc.nextLine();
            System.out.println("Resultado: ");
            boolean avaliacao = av.avaliacaoCadeiacaractere(cadeia);
            if (avaliacao) {
                System.out.print("bem formada.");
            } else {
                System.out.print("malformada.");
            }

            System.out.println("\n=>Deseja avaliar outra cadeia? (s/n)");
            escolha = sc.nextLine().charAt(0); //Pega o primeiro caractere digitado
            System.out.println("\n");
        }
    }
}