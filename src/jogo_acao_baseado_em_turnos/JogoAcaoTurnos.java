package jogo_acao_baseado_em_turnos;

import java.util.Scanner;

class Lutador {

    private int identificador;
    private Time time;
    private int valorDano;
    private int numVida;
    private int valorBaseIniciativa;

    //Inicializar atributos do lutador
    public Lutador(int identificador, Time time, int valorDano, int valorIniciativa) {
        setIdentificador(identificador);
        this.numVida = 100;
        setTime(time);
        setValorDano(valorDano);
        setValorBaseIniciativa(valorIniciativa);
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getNumVida() {
        return numVida;
    }

    public void setNumVida(int numVida) {
        if (numVida < 0) {
            this.numVida = 0;
        } else {
            this.numVida = numVida;
        }
    }

    public int getValorDano() {
        return valorDano;
    }

    public void setValorDano(int valorDano) {
        this.valorDano = valorDano;
    }

    public int getValorBaseIniciativa() {
        return valorBaseIniciativa;
    }

    public void setValorBaseIniciativa(int valorBaseIniciativa) {
        if (valorBaseIniciativa < 1 || valorBaseIniciativa > 100) {
            System.out.println("\nValor de iniciativa invalido. A iniciativa do lutador sera 1 por padrao.\n");
            this.valorBaseIniciativa = 1;
        } else {
            this.valorBaseIniciativa = valorBaseIniciativa;
        }
    }

    @Override
    public String toString() {
        String resultado = "\n ** Lutador **";
        resultado += "\nID: " + getIdentificador() + "\n";
        resultado += "\nTime: " + getTime().getNomeTime();
        resultado += "\nValor Dano: " + getValorDano() + "\n";
        resultado += "\nValor Iniciativa: " + getValorBaseIniciativa();

        return resultado;
    }
}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

class Time {

    private String nomeTime;
    private int ultimoLutador;
    private int ultimoMortos;
    private Lutador[] lutadores;
    private Lutador[] mortos;

    //Inicializar atributos do time
    public Time(String nomeTime) {
        setNomeTime(nomeTime);
        this.ultimoLutador = -1;
        this.ultimoMortos = -1;
        this.lutadores = new Lutador[50];
        this.mortos = new Lutador[70];
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public int getUltimoLutador() {
        return ultimoLutador;
    }

    public void setUltimoLutador(int ultimoLutador) {
        this.ultimoLutador = ultimoLutador;
    }

    public int getUltimoMortos() {
        return this.ultimoMortos;
    }

    public void setUltimoMortos(int ultimoMortos) {
        this.ultimoMortos = ultimoMortos;
    }

    public Lutador[] getLutadores() {
        return lutadores;
    }

    public Lutador[] getMortos() {
        return mortos;
    }

    public void inserirMorto(Lutador morto) {
        if ((morto != null) && (ultimoMortos < 69)) {
            int indice;
            int i = 0;
            while ((i <= ultimoMortos) && (mortos[i].getValorBaseIniciativa() > morto.getValorBaseIniciativa())) {
                i++;
            }
            indice = i;
            ultimoMortos = ultimoMortos + 1;
            for (int j = ultimoMortos; j > indice; j--) {
                mortos[j] = mortos[j - 1];
            }
            this.mortos[indice] = morto;
        } else {
            System.out.println("\nCemiterio cheio!");
        }
    }

    //Ordenar lutadores em ordem decrescente de iniciativa
    public void ordenarTimeOrdemDecrescente() {
        if (ultimoLutador > -1) {
            for (int j = 1; j <= ultimoLutador; j++) {
                Lutador key = lutadores[j];
                int i = j - 1;

                while ((i > -1) && (lutadores[i].getValorBaseIniciativa() < key.getValorBaseIniciativa())) {
                    lutadores[i + 1] = lutadores[i];
                    i = i - 1;
                }

                lutadores[i + 1] = key;
            }
        }

    }

    public String relatorioStatus() {
        ordenarTimeOrdemDecrescente();

        String status = "\n\n";
        status += "*************************************";
        status += "\nTIME: " + nomeTime;
        status += "\nLutadores vivos: " + (ultimoLutador + 1);
        for (int i = 0; i <= ultimoLutador; i++) {
            status += "\n-> ID: " + lutadores[i].getIdentificador() + " | Valor Base de Inciativa: " + lutadores[i].getValorBaseIniciativa() + " | Pontos de Vida: " + lutadores[i].getNumVida();
        }
        status += "\n-----------------------------------";
        status += "\nLutadores mortos: " + (ultimoMortos + 1);
        for (int i = 0; i <= ultimoMortos; i++) {
            status += "\n-> ID: " + mortos[i].getIdentificador() + " | Valor Base de Inciativa: " + mortos[i].getValorBaseIniciativa() + " | Pontos de Vida: " + mortos[i].getNumVida();
        }

        status += "\n*************************************";
        return status;
    }

}

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

class SistemaControle {
    private Time time1;
    private Time time2;

    //Inicializar os dois times
    public SistemaControle(Time time1, Time time2) {
        this.time1 = time1;
        this.time2 = time2;
    }

    //x->identificador do Lutador buscado
    public int buscarIndice(int x) {
        int indice = -1;
        for (int i = 0; i <= time1.getUltimoLutador(); i++) {
            if (time1.getLutadores()[i].getIdentificador() == x) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            for (int i = 0; i <= time2.getUltimoLutador(); i++) {
                if (time2.getLutadores()[i].getIdentificador() == x) {
                    indice = i;
                    break;
                }
            }
            if (indice == -1) {
                for (int i = 0; i <= time1.getUltimoMortos(); i++) {
                    if (time1.getMortos()[i].getIdentificador() == x) {
                        indice = i;
                        break;
                    }
                }
                if (indice == -1) {
                    for (int i = 0; i <= time2.getUltimoMortos(); i++) {
                        if (time2.getMortos()[i].getIdentificador() == x) {
                            indice = i;
                            break;
                        }
                    }
                }
            }
        }
        return indice;
    }


    //X-> lutador a ser inserido
    public void inserirLutador(Lutador X, int escolhaTime) {
        if ((escolhaTime == 1) && (time1.getUltimoLutador() < 49)) {
            if (buscarIndice(X.getIdentificador()) == -1) {
                time1.setUltimoLutador(time1.getUltimoLutador() + 1);
                time1.getLutadores()[time1.getUltimoLutador()] = X;
            } else {
                System.out.println("\nErro: Lutador de chave " + X.getIdentificador() + " ja existe!");
            }
        } else if ((escolhaTime == 2) && (time2.getUltimoLutador() < 49)) {
            if (buscarIndice(X.getIdentificador()) == -1) {
                this.time2.setUltimoLutador(time2.getUltimoLutador() + 1);
                this.time2.getLutadores()[time2.getUltimoLutador()] = X;
            } else {
                System.out.println("\nErro: Lutador de chave " + X.getIdentificador() + " ja existe!");
            }
        } else {
            System.out.println("\nErro: Time " + escolhaTime + " ja atingiu capacidade total");
        }

    }


    //Funcao para remover lutador (vivo) de um dos times
    public Lutador fugaLutador(int x) {
        Lutador removido = null;
        int indice = buscarIndice(x);

        if (indice == -1) {
            System.out.println("\nErro: Lutador de chave " + x + " nao existe!");
        } else {
            if (time1.getUltimoLutador() >= indice && time1.getLutadores()[indice].getIdentificador() == x) {
                removido = time1.getLutadores()[indice];
                for (int i = indice; i < time1.getUltimoLutador(); i++) {
                    time1.getLutadores()[i] = time1.getLutadores()[i + 1];
                }
                time1.setUltimoLutador(time1.getUltimoLutador() - 1);
            } else if (time2.getUltimoLutador() >= indice && time2.getLutadores()[indice].getIdentificador() == x) {
                removido = time2.getLutadores()[indice];
                for (int i = indice; i < time2.getUltimoLutador(); i++) {
                    time2.getLutadores()[i] = time2.getLutadores()[i + 1];
                }
                time2.setUltimoLutador(time2.getUltimoLutador() - 1);
            } else {
                System.out.println("\nLutador de chave " + x + " esta morto e nao pode ser removido!");
            }
        }

        return removido;
    }


    //Fase de Organizacao dos Times
    public void organizacaoTimes() {
        Scanner sc = new Scanner(System.in);
        boolean sairOrganizacao = false;
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++");
        System.out.println("\nPrimeira etapa: FASE DE ORGANIZACAO DE TIMES");
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++\n");
        while (!sairOrganizacao) {
            System.out.println("\n ---- Organizacao de Times -------------\n");
            System.out.println("1 - Insercao de lutadores em times");
            System.out.println("\n2 - Obter status de um time");
            System.out.println("\n3 - Fuga de lutador (remocao)");
            System.out.println("\n4 - Ir para a Fase de Combate");
            System.out.println("\n-----------------------------------");
            System.out.println("\nDigite o numero da opcao desejada:");
            int escolha = sc.nextInt();

            switch (escolha) {
                case 1:
                    boolean sairInsercao = false;
                    while (!sairInsercao) {
                        System.out.println("\n\n++++++++++++++++++++++++++++++++++");
                        System.out.println("\n** INSERCAO DE LUTADORES **");
                        System.out.println("\n1 - Inserir novo lutador");
                        System.out.println("\n2 - Voltar ao menu principal");
                        int escolha2 = sc.nextInt();
                        switch (escolha2) {
                            case 1:
                                System.out.println("\n - Informe as caracteristicas do lutador a ser inserido -");
                                System.out.println("\nIdentificador: ");
                                int id = sc.nextInt();

                                int time = 0;
                                //Laco para garantir que o time escolhido foi o 1 ou 2
                                while (time != 1 && time != 2) {
                                    System.out.println("\nTime (1 ou 2): ");
                                    time = sc.nextInt();
                                    if ((time != 1) && (time != 2)) {
                                        System.out.println("Erro: time invalido! Digite o time novamente.");
                                    }
                                }

                                int valorDano = 0;
                                //Laco para garantir que o valor de dano do lutador sera maior que 0
                                while (valorDano < 1) {
                                    System.out.println("\nValor de dano: ");
                                    valorDano = sc.nextInt();
                                    if (valorDano < 1) {
                                        System.out.println("\nValor de dano invalido! Digite novamente");
                                    }
                                }

                                int valorBaseIniciativa = 0;
                                //Laco para garantir que o valor de base de iniciativa passado pelo usuario sera entre 1 e 100
                                while (valorBaseIniciativa < 1 || valorBaseIniciativa > 100) {
                                    System.out.println("\nValor de Base de Iniciativa (entre 1 e 100): ");
                                    valorBaseIniciativa = sc.nextInt();
                                    if (valorBaseIniciativa < 1 || valorBaseIniciativa > 100) {
                                        System.out.println("\nValor de iniciativa invalido. Digite novamente");
                                    }
                                }


                                if (time == 1) {
                                    Lutador novo = new Lutador(id, this.time1, valorDano, valorBaseIniciativa);
                                    inserirLutador(novo, time);
                                } else {
                                    Lutador novo = new Lutador(id, this.time2, valorDano, valorBaseIniciativa);
                                    inserirLutador(novo, time);
                                }
                                break;

                            case 2:
                                sairInsercao = true;
                                break;

                            default:
                                System.out.println("\nOpcao invalida!");
                        }
                        System.out.println("\n++++++++++++++++++++++++++++++++++");
                    }
                    break;

                case 2:
                    boolean sairRelatorio = false;
                    while (!sairRelatorio) {
                        System.out.println("\n++++++++++++++++++++++++++++++++++");
                        System.out.println("\n** RELATORIO DE STATUS **\n");
                        System.out.println("1 - Status Time 1");
                        System.out.println("\n2 - Status Time 2");
                        System.out.println("\n3 - Voltar ao menu principal");
                        int escolha2 = sc.nextInt();
                        switch (escolha2) {
                            case 1:
                                System.out.println(time1.relatorioStatus());
                                break;
                            case 2:
                                System.out.println(time2.relatorioStatus());
                                break;
                            case 3:
                                sairRelatorio = true;
                                break;
                            default:
                                System.out.println("\nOpcao invalida!");
                        }
                        System.out.println("\n++++++++++++++++++++++++++++++++++\n");
                    }
                    break;

                case 3:
                    System.out.println("\n++++++++++++++++++++++++++++++++++");
                    System.out.println("\n** FUGA DE LUTADOR **");
                    System.out.println("\nInserir o identificador do lutador que deseja remover: ");
                    int id = sc.nextInt();

                    Lutador aRemover = fugaLutador(id);
                    if (aRemover != null) {
                        System.out.println("\nO lutador de chave " + id + " foi removido com sucesso!");
                    }
                    System.out.println("\n++++++++++++++++++++++++++++++++++");
                    break;

                case 4:
                    if (time1.getUltimoLutador() > -1 && time2.getUltimoLutador() > -1) {
                        //Organizar os times em filas em ordem decrescente de iniciativa para iniciar o combate
                        time1.ordenarTimeOrdemDecrescente();
                        time2.ordenarTimeOrdemDecrescente();
                        sairOrganizacao = true;
                    } else {
                        System.out.println("\nTime sem lutadores! Insira mais lutadores nos times antes de ir para a proxima fase.");
                    }
                    break;
                default:
                    System.out.println("\nOpcao invalida!");
            }


        }

    }

    //Funcao para reorganizar as filas dos times ao retirar um Lutador para atacar
    public void reorganizarFilas() {
        for (int i = 0; i < time1.getUltimoLutador(); i++) {
            time1.getLutadores()[i] = time1.getLutadores()[i + 1];
        }
        time1.setUltimoLutador(time1.getUltimoLutador() - 1);
        for (int i = 0; i < time2.getUltimoLutador(); i++) {
            time2.getLutadores()[i] = time2.getLutadores()[i + 1];
        }
        time2.setUltimoLutador(time2.getUltimoLutador() - 1);
    }

    //Funcao para representar o ataque entre os primeiros lutadores das filas dos times
    public void ataque(Lutador atacante1, Lutador atacante2) {
        if (atacante1.getNumVida() > 0 && atacante2.getNumVida() > 0) {
            System.out.println("\n\nATAQUE|TIMES: " + atacante1.getTime().getNomeTime() + " x " + atacante2.getTime().getNomeTime());
            System.out.println("\n=> Lutadores: " + atacante1.getIdentificador() + " x " + atacante2.getIdentificador());
            System.out.println("\n=> Vida: " + atacante1.getNumVida() + " x " + atacante2.getNumVida());
            System.out.println("\n=> Iniciativas: " + atacante1.getValorBaseIniciativa() + " x " + atacante2.getValorBaseIniciativa());
            System.out.println("\n=> Danos: " + atacante1.getValorDano() + " x " + atacante2.getValorDano());
            atacante1.setNumVida(atacante1.getNumVida() - atacante2.getValorDano());
            atacante2.setNumVida(atacante2.getNumVida() - atacante1.getValorDano());
            System.out.println("\n=> Vida Atual: " + atacante1.getNumVida() + " x " + atacante2.getNumVida());
        }
    }


    //Fase de combate
    public void combate() {
        System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++");
        System.out.println("\nSegunda etapa: COMBATE");
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++\n\n");

        //Contador para garantir que todos os lutadores dos times vao lutar
        int contadorTime1 = 0;
        int contadorTime2 = 0;

        //Guardar o indice dos ultimos lutadores dos times
        int n = time1.getUltimoLutador();
        int m = time2.getUltimoLutador();

        while ((contadorTime1 <= n || contadorTime2 <= m) && (time1.getUltimoLutador() > -1 && time2.getUltimoLutador() > -1)) {
            Lutador atacante1 = time1.getLutadores()[0];
            Lutador atacante2 = time2.getLutadores()[0];
            reorganizarFilas();
            contadorTime1++;
            contadorTime2++;
            ataque(atacante1, atacante2);

            if (atacante1.getNumVida() <= 0) {
                System.out.println("\nLutador " + atacante1.getIdentificador() + " foi morto!");
                time1.inserirMorto(atacante1);
            } else {
                time1.setUltimoLutador(time1.getUltimoLutador() + 1);
                time1.getLutadores()[time1.getUltimoLutador()] = atacante1;
            }

            if (atacante2.getNumVida() <= 0) {
                System.out.println("\nLutador " + atacante2.getIdentificador() + " foi morto!");
                time2.inserirMorto(atacante2);
            } else {
                time2.setUltimoLutador(time2.getUltimoLutador() + 1);
                time2.getLutadores()[time2.getUltimoLutador()] = atacante2;
            }
        }
    }


    //Fase de resultados - retorna "true" caso o jogo tenha acabado e "false" caso precise continuar
    public boolean resultados() {
        boolean resultado = false;
        int scoreTime1 = time2.getUltimoMortos() + 1;
        int scoreTime2 = time1.getUltimoMortos() + 1;

        System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++");
        System.out.println("\n** RESULTADOS **");
        System.out.println("\nScore Time 1 | " + time1.getNomeTime() + ": " + scoreTime1);
        System.out.println("\nScore Time 2 | " + time2.getNomeTime() + ": " + scoreTime2);

        if (time1.getUltimoLutador() > -1 && time2.getUltimoLutador() == -1) {
            System.out.println("\nO time 1 (" + time1.getNomeTime() + ") foi vencedor!");
            resultado = true;
        } else if (time2.getUltimoLutador() > -1 && time1.getUltimoLutador() == -1) {
            System.out.println("\nO time 2 (" + time2.getNomeTime() + ") foi vencedor!");
            resultado = true;
        } else if (time1.getUltimoLutador() > -1 && time2.getUltimoLutador() > -1) {
            if (scoreTime1 >= 20 && scoreTime2 < 20) {
                System.out.println("\nO time 1 (" + time1.getNomeTime() + ") foi vencedor!");
                resultado = true;
            } else if (scoreTime2 >= 20 && scoreTime1 < 20) {
                System.out.println("\nO time 2 (" + time2.getNomeTime() + ") foi vencedor!");
                resultado = true;
            } else if (scoreTime1 >= 20 && scoreTime2 >= 20) {
                if (scoreTime1 > scoreTime2) {
                    System.out.println("\nO time 1 (" + time1.getNomeTime() + ") foi vencedor!");
                    resultado = true;
                } else {
                    System.out.println("\nO time 2 (" + time2.getNomeTime() + ") foi vencedor!");
                    resultado = true;
                }
            }
        } else if (time1.getUltimoLutador() < 0 && time2.getUltimoLutador() < 0) {
            if (scoreTime1 > scoreTime2) {
                System.out.println("\nO time 1 (" + time1.getNomeTime() + ") foi vencedor!");
                resultado = true;
            } else if (scoreTime2 > scoreTime1) {
                System.out.println("\nO time 2 (" + time2.getNomeTime() + ") foi vencedor!");
                resultado = true;
            } else {
                System.out.println("\nEmpate!!");
                resultado = true;
            }

        }

        if (!resultado) {
            System.out.println("\nNovo turno sendo inicializado!");
        }

        System.out.println("\n+++++++++++++++++++++++++++++++++++++++\n");

        return resultado;
    }

}


// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class JogoAcaoTurnos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n**BEM-VINDO AO JOGO DE ACAO**\n");
        System.out.println("Digite o nome do time 1:");
        String nome1 = sc.nextLine();
        Time time1 = new Time(nome1);
        System.out.println("\nDigite o nome do time 2:");
        String nome2 = sc.nextLine();
        Time time2 = new Time(nome2);
        SistemaControle jogo = new SistemaControle(time1, time2);

        boolean fim = false;

        while (!fim) {
            jogo.organizacaoTimes();
            jogo.combate();
            fim = jogo.resultados();
        }
    }
}
