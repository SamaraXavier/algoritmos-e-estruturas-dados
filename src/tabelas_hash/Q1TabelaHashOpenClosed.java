package tabelas_hash;

import java.util.Scanner;

class noListaDuplaEnc {
    int chave;
    noListaDuplaEnc proximo;
    noListaDuplaEnc anterior;

    noListaDuplaEnc(int chave) {
        this.chave = chave;
        this.proximo = null;
        this.anterior = null;
    }
}

class listaDuplaEnc {
    noListaDuplaEnc cabeca;

    listaDuplaEnc() {
        this.cabeca = null;
    }
}

class tabelaHash {
    int tamanho;
    listaDuplaEnc[] tabela;

    public tabelaHash(int tam) {
        this.tamanho = tam;
        this.tabela = new listaDuplaEnc[tam];
    }
}

class procedimentos {

    public static void inserirListaDuplaEnc(noListaDuplaEnc X, listaDuplaEnc lista) {
        X.proximo = lista.cabeca;
        if (lista.cabeca != null) {
            lista.cabeca.anterior = X;
        }
        lista.cabeca = X;
    }

    public static void imprimirListaDuplaEnc(listaDuplaEnc lista) {
        noListaDuplaEnc pt = lista.cabeca;
        while (pt != null) {
            System.out.print(pt.chave);
            if (pt.proximo != null) {
                System.out.print(" -> ");
            } else {
                System.out.print(" ");
            }
            pt = pt.proximo;
        }
    }

    public static int hashFunction(int k, int m) {
        return k % m;
    }


    public static int inserirNoTabelaHash(noListaDuplaEnc X, tabelaHash H) {
        int indice = -1; //valor para verificar em qual posicao do vetor o elemento foi inserido
        int k = hashFunction(X.chave, H.tamanho);

        //utilizar Open Address Hashing caso haja posicao disponivel na tabela
        if (H.tabela[k] == null) {
            listaDuplaEnc novaLista = new listaDuplaEnc();
            novaLista.cabeca = X;
            H.tabela[k] = novaLista;
            indice = k;
        } else {
            int i = (k + 1) % H.tamanho;
            while (i != k) {
                if (H.tabela[i] == null) {
                    listaDuplaEnc novaLista = new listaDuplaEnc();
                    novaLista.cabeca = X;
                    H.tabela[i] = novaLista;
                    indice = i;
                    i = k; //sair do laco
                } else {
                    i = (i + 1) % H.tamanho;
                }
            }
        }

        //caso a tabela ja esteja cheia e o elemento nao foi inserido, utilizar logica do Closed Address Hashing
        if (indice == -1) {
            inserirListaDuplaEnc(X, H.tabela[k]);
            indice = k;
        }

        return indice;
    }

    public static void imprimirTabelaHash(tabelaHash H) {
        int n = H.tamanho;
        System.out.println("\n-- TABELA HASH --");
        for (int i = 0; i < n; i++) {
            System.out.print("\n");
            System.out.print("tabela[" + i + "] = ");
            if (H.tabela[i] != null) {
                imprimirListaDuplaEnc(H.tabela[i]);
            } else {
                System.out.print("null");
            }
        }
    }
}


public class Q1TabelaHashOpenClosed {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("UTILIZANDO TABELA HASH");
        System.out.println("-------------------------\n");

        boolean sairSistema = false;

        while (!sairSistema) {
            boolean condicaoParada = false;
            System.out.println("Informe o tamanho do vetor estatico: ");
            int tam = sc.nextInt();
            tabelaHash H = new tabelaHash(tam);
            while (!condicaoParada) {
                System.out.println("\n\nIforme o que deseja fazer: ");
                System.out.println("1 - Inserir | 2 - Sair");
                int escolha = sc.nextInt();
                switch (escolha) {
                    case 1:
                        System.out.println("Informe a chave que deseja inserir: ");
                        int ch = sc.nextInt();
                        noListaDuplaEnc X = new noListaDuplaEnc(ch);
                        int indice = procedimentos.inserirNoTabelaHash(X, H);
                        System.out.println("Inserido na posicao " + indice + " da tabela");
                        procedimentos.imprimirTabelaHash(H);
                        break;
                    case 2:
                        condicaoParada = true;
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            }

            System.out.println("Deseja sair do sistema? 1 - Sim | 2 - Nao");
            sairSistema = sc.nextInt() == 1;
        }
    }
}
