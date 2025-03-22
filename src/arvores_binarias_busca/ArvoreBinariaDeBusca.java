package arvores_binarias_busca;

import java.util.Scanner;

class NoArvoreBin {
    int chave;
    NoArvoreBin pai;
    NoArvoreBin esquerda;
    NoArvoreBin direita;

    NoArvoreBin(int chave) {
        this.chave = chave;
    }
}

class ArvoreBin {
    NoArvoreBin raiz;
}

class procedimento {

    public static void executarPercursoPreOrdem(NoArvoreBin pt) {
        System.out.print(pt.chave + " ");
        if (pt.esquerda != null) {
            executarPercursoPreOrdem(pt.esquerda);
        }
        if (pt.direita != null) {
            executarPercursoPreOrdem(pt.direita);
        }
    }


    public static void executarPercursoEmOrdem(NoArvoreBin pt) {
        if (pt.esquerda != null) {
            executarPercursoEmOrdem(pt.esquerda);
        }
        System.out.print(pt.chave + " ");
        if (pt.direita != null) {
            executarPercursoEmOrdem(pt.direita);
        }
    }


    public static void executarPercursoPosOrdem(NoArvoreBin pt) {
        if (pt.esquerda != null) {
            executarPercursoPosOrdem(pt.esquerda);
        }
        if (pt.direita != null) {
            executarPercursoPosOrdem(pt.direita);
        }
        System.out.print(pt.chave + " ");
    }


    public static NoArvoreBin buscarArvoreBin(int ch, NoArvoreBin pt) {
        while (pt != null && pt.chave != ch) {
            if (ch < pt.chave) {
                pt = pt.esquerda;
            } else {
                pt = pt.direita;
            }
        }
        return pt;
    }


    public static void inserirArvoreBin(NoArvoreBin X, ArvoreBin T) {
        NoArvoreBin pai = null;
        NoArvoreBin pt = T.raiz;

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
        } else if (X.chave <= pai.chave) {
            pai.esquerda = X;
        } else {
            pai.direita = X;
        }
    }

    public static NoArvoreBin encontrarMinimo(NoArvoreBin pt) {
        while (pt.esquerda != null) {
            pt = pt.esquerda;
        }
        return pt;
    }


    public static NoArvoreBin encontrarSucessor(NoArvoreBin pt) {
        if (pt.direita != null) {
            return encontrarMinimo(pt.direita);
        } else {
            NoArvoreBin pai = pt.pai;
            while (pai != null && (pai.direita != null && pt.chave == pai.direita.chave)) {
                pt = pai;
                pai = pai.pai;
            }
            return pai;
        }
    }


    public static void removerSemFilhos(NoArvoreBin pt, ArvoreBin T) {
        NoArvoreBin pai = pt.pai;

        if (pai != null) {
            if (pt.chave <= pai.chave) {
                pai.esquerda = null;
            } else {
                pai.direita = null;
            }
        } else {
            T.raiz = null;
        }
        pt.pai = null;
    }


    public static void removerUnicoFilho(NoArvoreBin pt, ArvoreBin T) {
        NoArvoreBin pai = pt.pai;
        NoArvoreBin filho;

        if (pt.esquerda != null) {
            filho = pt.esquerda;
            pt.esquerda = null;
        } else {
            filho = pt.direita;
            pt.direita = null;
        }

        filho.pai = pai;

        if (pai != null) {
            if (pai.chave >= filho.chave) {
                pai.esquerda = filho;
            } else {
                pai.direita = filho;
            }
        } else {
            T.raiz = filho;
        }

        pt.pai = null;
    }


    public static void removerDoisFilhos(NoArvoreBin pt, ArvoreBin T) {
        NoArvoreBin sucessor = encontrarSucessor(pt);
        sucessor = removerArvoreBin(sucessor.chave, T); //remover o sucessor da arvore

        sucessor.pai = pt.pai;
        pt.pai = null;

        sucessor.direita = pt.direita;
        pt.direita = null;

        sucessor.esquerda = pt.esquerda;
        pt.esquerda = null;

        NoArvoreBin pai = sucessor.pai;
        if (pai != null) {
            if (pai.chave >= sucessor.chave) {
                pai.esquerda = sucessor;
            } else {
                pai.direita = sucessor;
            }
        } else {
            T.raiz = sucessor;
        }

        //vincular filhos esquerda e direita(caso tenha) ao novo pai
        sucessor.esquerda.pai = sucessor;

        if (sucessor.direita != null) {
            sucessor.direita.pai = sucessor;
        }
    }


    //ch-> chave do no a ser removido
    public static NoArvoreBin removerArvoreBin(int ch, ArvoreBin T) {
        NoArvoreBin pt = buscarArvoreBin(ch, T.raiz);
        if (pt != null) {
            if (pt.direita == null && pt.esquerda == null) {
                removerSemFilhos(pt, T);
            } else if (pt.direita != null && pt.esquerda != null) {
                removerDoisFilhos(pt, T);
            } else {
                removerUnicoFilho(pt, T);
            }
        } else {
            System.out.println("No " + ch + " nao existe!");
        }

        return pt;
    }

}

public class ArvoreBinariaDeBusca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("UTILIZANDO ARVORE BINARIA DE BUSCA");
        System.out.println("-------------------------\n");

        boolean sairSistema = false;

        while (!sairSistema) {
            ArvoreBin T = new ArvoreBin();

            boolean condicaoParada = false;

            while (!condicaoParada) {
                System.out.println("\n\nIforme o que deseja fazer: ");
                System.out.println("1 - Buscar | 2 - Inserir | 3 - Sucessor | 4 - Remover | 5 - Percursos | 6 - Sair");
                int escolha = sc.nextInt();

                switch (escolha) {
                    case 1:
                        System.out.println("Informe a chave do no que deseja buscar: ");
                        int chave = sc.nextInt();
                        NoArvoreBin pt = procedimento.buscarArvoreBin(chave, T.raiz);
                        if (pt != null) {
                            System.out.println("No " + pt.chave + " foi encontrado");
                        } else {
                            System.out.println("Nao foi encontrado");
                        }
                        break;
                    case 2:
                        System.out.println("Informe a chave do no que deseja inserir: ");
                        int ch = sc.nextInt();
                        NoArvoreBin X = new NoArvoreBin(ch);
                        procedimento.inserirArvoreBin(X, T);
                        break;
                    case 3:
                        System.out.println("Informe a chave do no que deseja encontrar o sucessor: ");
                        int c = sc.nextInt();
                        NoArvoreBin N = procedimento.buscarArvoreBin(c, T.raiz);
                        if (N != null) {
                            NoArvoreBin resultado = procedimento.encontrarSucessor(N);
                            if (resultado != null) {
                                System.out.println("O sucessor: " + resultado.chave + " foi encontrado");
                            } else {
                                System.out.println("Nao foi encontrado");
                            }
                        } else {
                            System.out.println("No com esta chave nao foi encontrado");
                        }
                        break;
                    case 4:
                        System.out.println("Informe a chave do no que deseja remover: ");
                        int chav = sc.nextInt();
                        NoArvoreBin result = procedimento.removerArvoreBin(chav, T);
                        if (result != null) {
                            System.out.println("No " + result.chave + " foi removido");
                        }
                        break;
                    case 5:
                        if (T.raiz != null) {
                            System.out.println("** PRE-ORDEM **");
                            procedimento.executarPercursoPreOrdem(T.raiz);
                            System.out.println("\n** EM-ORDEM **");
                            procedimento.executarPercursoEmOrdem(T.raiz);
                            System.out.println("\n** POS-ORDEM **");
                            procedimento.executarPercursoPosOrdem(T.raiz);
                        } else {
                            System.out.println("Arvore vazia");
                        }
                        break;
                    case 6:
                        condicaoParada = true;
                        break;
                    default:
                        System.out.println("Opcao invalida");
                }
            }

            System.out.println("Deseja sair do sistema? 1 - Sim | 2 - Nao");
            sairSistema = sc.nextInt() == 1;
        }
    }
}
