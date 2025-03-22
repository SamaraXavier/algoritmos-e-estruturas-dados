package arvores_avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class NoArvoreAVL {
    int chave;
    int altura;
    int fatorBalanceamento;
    NoArvoreAVL pai;
    NoArvoreAVL esquerda;
    NoArvoreAVL direita;

    NoArvoreAVL(int chave) {
        this.chave = chave;
        this.altura = 1;
        this.fatorBalanceamento = 0;
        this.pai = null;
        this.esquerda = null;
        this.direita = null;
    }
}

class ArvoreAVL {
    NoArvoreAVL raiz;

    public ArvoreAVL() {
        this.raiz = null;
    }
}

class procedimentos {

    public static String executarPercursoEmOrdem(NoArvoreAVL pt) {
        if (pt == null) {
            return "";
        }

        String resultado = "";
        if (pt.esquerda != null) {
            resultado += executarPercursoEmOrdem(pt.esquerda);
        }
        if (pt.fatorBalanceamento > 0) {
            resultado += pt.chave + "(+" + pt.fatorBalanceamento + ")" + " ";
        } else {
            resultado += pt.chave + "(" + pt.fatorBalanceamento + ")" + " ";
        }
        if (pt.direita != null) {
            resultado += executarPercursoEmOrdem(pt.direita);
        }

        return resultado;
    }

    public static void inserirArvoreBin(NoArvoreAVL X, ArvoreAVL T) {
        NoArvoreAVL pai = null;
        NoArvoreAVL pt = T.raiz;
        while (pt != null) {
            pai = pt;
            if (X.chave <= pt.chave) {
                pt = pt.esquerda;
            } else {
                pt = pt.direita;
            }
        }
        X.pai = pai;
        if (pai == null) {
            T.raiz = X;
        } else {
            if (X.chave <= pai.chave) {
                pai.esquerda = X;
            } else {
                pai.direita = X;
            }
        }
    }

    public static void inserirArvoreAVL(NoArvoreAVL X, ArvoreAVL T) {
        inserirArvoreBin(X, T);

        boolean eAVL = verificarSeEstaBalanceada(X, T);
        if (eAVL) {
            EscreverArquivoDeSaida.escrever("arvore ja balanceada.");
        } else {
            balancear(X, T);
        }
        String arvore = executarPercursoEmOrdem(T.raiz);
        EscreverArquivoDeSaida.escrever(arvore);
        EscreverArquivoDeSaida.escrever("" + T.raiz.altura);
    }


    public static int fatorBalanceamento(NoArvoreAVL pt) {
        if (pt == null) return 0;
        int alturaEsquerda;
        int alturaDireita;

        if (pt.esquerda == null) {
            alturaEsquerda = 0;
        } else {
            alturaEsquerda = pt.esquerda.altura;
        }
        if (pt.direita == null) {
            alturaDireita = 0;
        } else {
            alturaDireita = pt.direita.altura;
        }
        return alturaDireita - alturaEsquerda;
    }

    public static boolean verificarSeEstaBalanceada(NoArvoreAVL pt, ArvoreAVL T) {
        if (pt == null) return true;
        else {
            boolean balanceada = true;
            while (pt != null) {
                atualizarNoArvore(pt);
                if (pt.fatorBalanceamento > 1 || pt.fatorBalanceamento < -1) {
                    balanceada = false;
                }

                pt = pt.pai;

            }
            return balanceada;
        }
    }

    public static void balancear(NoArvoreAVL pt, ArvoreAVL T) {

        while (pt != null) {
            atualizarNoArvore(pt);

            if (pt.fatorBalanceamento > 1) {
                EscreverArquivoDeSaida.escrever("no responsavel: " + pt.chave);
                String arvore = executarPercursoEmOrdem(T.raiz);
                EscreverArquivoDeSaida.escrever(arvore);
                if (fatorBalanceamento(pt.direita) < 0) {
                    EscreverArquivoDeSaida.escrever("rotacao esquerda dupla.");
                    pt = rotacaoDuplaEsquerda(pt, T);
                } else {
                    EscreverArquivoDeSaida.escrever("rotacao esquerda.");
                    pt = rotacaoEsquerda(pt, T);
                }
            } else if (pt.fatorBalanceamento < -1) {
                EscreverArquivoDeSaida.escrever("no responsavel: " + pt.chave);
                String arvore = executarPercursoEmOrdem(T.raiz);
                EscreverArquivoDeSaida.escrever(arvore);
                if (fatorBalanceamento(pt.esquerda) > 0) {
                    pt = rotacaoDuplaDireita(pt, T);
                    EscreverArquivoDeSaida.escrever("rotacao direita dupla.");
                } else {
                    EscreverArquivoDeSaida.escrever("rotacao direita.");
                    pt = rotacaoDireita(pt, T);
                }
            }

            pt = pt.pai;
        }
    }

    public static int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public static void atualizarNoArvore(NoArvoreAVL pt) {
        int alturaEsq;
        int alturaDir;
        if (pt.esquerda != null) {
            alturaEsq = pt.esquerda.altura;
        } else {
            alturaEsq = 0;
        }
        if (pt.direita != null) {
            alturaDir = pt.direita.altura;
        } else {
            alturaDir = 0;
        }
        pt.altura = max(alturaEsq, alturaDir) + 1;
        pt.fatorBalanceamento = alturaDir - alturaEsq;
    }

    //ROTACOES
    public static NoArvoreAVL rotacaoDireita(NoArvoreAVL pt, ArvoreAVL T) {
        NoArvoreAVL raizAtualizada = pt.esquerda;
        pt.esquerda = raizAtualizada.direita;

        if (raizAtualizada.direita != null) {
            raizAtualizada.direita.pai = pt;
        }

        raizAtualizada.pai = pt.pai;

        if (pt.pai == null) {
            T.raiz = raizAtualizada;
        } else if (pt == pt.pai.direita) {
            pt.pai.direita = raizAtualizada;
        } else {
            pt.pai.esquerda = raizAtualizada;
        }

        raizAtualizada.direita = pt;
        pt.pai = raizAtualizada;

        atualizarNoArvore(pt);
        atualizarNoArvore(raizAtualizada);

        return raizAtualizada;
    }

    public static NoArvoreAVL rotacaoEsquerda(NoArvoreAVL pt, ArvoreAVL T) {
        NoArvoreAVL raizAtualizada = pt.direita;
        pt.direita = raizAtualizada.esquerda;

        if (raizAtualizada.esquerda != null) {
            raizAtualizada.esquerda.pai = pt;
        }

        raizAtualizada.pai = pt.pai;

        if (pt.pai == null) {
            T.raiz = raizAtualizada;
        } else if (pt == pt.pai.esquerda) {
            pt.pai.esquerda = raizAtualizada;
        } else {
            pt.pai.direita = raizAtualizada;
        }

        raizAtualizada.esquerda = pt;
        pt.pai = raizAtualizada;

        atualizarNoArvore(pt);
        atualizarNoArvore(raizAtualizada);

        return raizAtualizada;
    }

    public static NoArvoreAVL rotacaoDuplaDireita(NoArvoreAVL pt, ArvoreAVL T) {
        pt.esquerda = rotacaoEsquerda(pt.esquerda, T);
        return rotacaoDireita(pt, T);
    }

    public static NoArvoreAVL rotacaoDuplaEsquerda(NoArvoreAVL pt, ArvoreAVL T) {
        pt.direita = rotacaoDireita(pt.direita, T);
        return rotacaoEsquerda(pt, T);
    }
}


public class ArvoreAVLMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("** ARVORE AVL **");
        System.out.println("Informe o caminho de um arquivo txt (Ex.: \\Users\\nome\\Desktop\\Algoritmos\\nomeArquivo.txt): ");
        System.out.println("Nao esquecer de colocar .txt ao fim do nome do arquivo! O arquivo de saida sera gerado no diretorio atual.");
        System.out.print("> ");
        String caminho = sc.nextLine();

        if (caminho == null) {
            System.out.println("Invalido");
            return;
        }

        File fileEntrada = new File(caminho);
        File fileSaida = new File("L6Q1_out.txt");

        if (fileSaida.exists()) {
            fileSaida.delete();
        }

        try (Scanner scanner = new Scanner(fileEntrada)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }

                ArvoreAVL arvore = new ArvoreAVL();

                Scanner linhaScanner = new Scanner(linha);
                while (linhaScanner.hasNextInt()) {
                    int chave = linhaScanner.nextInt();
                    procedimentos.inserirArvoreAVL(new NoArvoreAVL(chave), arvore);
                }

                EscreverArquivoDeSaida.escrever(""); //pular linha entre arvores distintas
            }
            System.out.println("Programa executado com sucesso! Arquivo de saida L6Q1_out.txt gerado.");
        } catch (FileNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

class EscreverArquivoDeSaida {
    static void escrever(String s) {
        File fileOut = new File("L6Q1_out.txt");
        try (FileWriter fw = new FileWriter(fileOut, true)) {
            fw.write(s + "\n");
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
