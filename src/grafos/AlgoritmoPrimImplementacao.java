package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Vertice {
    int id;
    int chave;
    int pai;

    public Vertice(int id) {
        this.id = id;
        this.chave = Integer.MAX_VALUE;
        this.pai = -1;
    }
}

class ListaPrioridadeMinima {
    Vertice[] vertices;
    int tamanho;
    int ultimo;

    public ListaPrioridadeMinima(int tamanho) {
        this.tamanho = tamanho;
        this.vertices = new Vertice[tamanho];
        this.ultimo = -1;
    }

    public int retornarIndiceFilhoEsquerda(int i) {
        return (2 * i) + 1;
    }

    public int retornarIndiceFilhoDireita(int i) {
        return (2 * i) + 2;
    }

    public void minHeapify(int i) {
        int menor = i;
        int l = retornarIndiceFilhoEsquerda(i);
        int r = retornarIndiceFilhoDireita(i);

        if (l <= ultimo && (vertices[l].chave < vertices[menor].chave ||
                (vertices[l].chave == vertices[menor].chave && vertices[l].id < vertices[menor].id))) {
            menor = l;
        }
        if (r <= ultimo && (vertices[r].chave < vertices[menor].chave ||
                (vertices[r].chave == vertices[menor].chave && vertices[r].id < vertices[menor].id))) {
            menor = r;
        }

        if (menor != i) {
            trocar(i, menor);
            minHeapify(menor);
        }
    }

    public void inserir(Vertice v) {
        if (ultimo < tamanho - 1) {
            ultimo++;
            vertices[ultimo] = v;
            diminuirChave(ultimo);
        } else {
            System.out.println("Overflow");
        }
    }

    public void diminuirChave(int i) {
        while (i > 0 && (vertices[i].chave < vertices[(i - 1) / 2].chave ||
                (vertices[i].chave == vertices[(i - 1) / 2].chave && vertices[i].id < vertices[(i - 1) / 2].id))) {
            trocar(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    public void atualizarValorChave(int v) {
        for (int i = 0; i <= ultimo; i++) {
            if (vertices[i].id == v) {
                diminuirChave(i);
                return;
            }
        }
    }

    public void trocar(int i, int j) {
        Vertice temp = vertices[i];
        vertices[i] = vertices[j];
        vertices[j] = temp;
    }

    public Vertice extrairMinimo() {
        if (ultimo == -1) return null;
        Vertice minVertice = vertices[0];
        trocar(0, ultimo);
        ultimo--;
        minHeapify(0);
        return minVertice;
    }

    public boolean isEmpty() {
        return ultimo == -1;
    }
}

class Grafo {
    Vertice[] vertices;
    int[][] matrizAdjacencia;
    int quantidadeVertices;

    public Grafo(int quantidadeVertices) {
        this.quantidadeVertices = quantidadeVertices;
        this.vertices = new Vertice[quantidadeVertices];
        this.matrizAdjacencia = new int[quantidadeVertices][quantidadeVertices];

        // Inicializar vertices e matriz de adjacencia com inf em todas as posicoes
        for (int i = 0; i < quantidadeVertices; i++) {
            vertices[i] = new Vertice(i);
            for (int j = 0; j < quantidadeVertices; j++) {
                matrizAdjacencia[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public void adicionarAresta(int origem, int destino, int peso) {
        int i = indiceVertice(origem);
        int j = indiceVertice(destino);

        if (i != -1 && j != -1) {
            matrizAdjacencia[i][j] = peso;
            matrizAdjacencia[j][i] = peso;
        } else {
            System.out.println("Vertice nao encontrado");
        }
    }

    public int indiceVertice(int vertice) {
        for (int i = 0; i < quantidadeVertices; i++) {
            if (vertices[i].id == vertice) {
                return i;
            }
        }
        return -1;
    }

    public void exibirGrafo() {
        System.out.println("Matriz de Adjacência:");
        System.out.print("V  ");
        for (int i = 0; i < quantidadeVertices; i++) {
            System.out.print(vertices[i].id + "  ");
        }
        System.out.println();

        for (int i = 0; i < quantidadeVertices; i++) {
            System.out.print(vertices[i].id + " ");
            for (int j = 0; j < quantidadeVertices; j++) {
                if (matrizAdjacencia[i][j] == Integer.MAX_VALUE) {
                    System.out.print(" 0 ");
                } else {
                    System.out.print(" " + matrizAdjacencia[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

class AlgoritmoPrim {
    private static final int Inf = Integer.MAX_VALUE;

    public static void encontrarPrimMST(Grafo G, int r) {
        int n = G.quantidadeVertices;
        int[][] w = G.matrizAdjacencia;
        boolean[] verticeJaVisitado = new boolean[n];
        ListaPrioridadeMinima filaPrioridade = new ListaPrioridadeMinima(n);

        for (int u = 0; u < n; u++) {
            G.vertices[u].chave = Inf;
            G.vertices[u].pai = -1;
            verticeJaVisitado[u] = false;
            filaPrioridade.inserir(G.vertices[u]);
        }

        G.vertices[r].chave = 0;
        filaPrioridade.atualizarValorChave(r);

        while (!filaPrioridade.isEmpty()) {
            Vertice u = filaPrioridade.extrairMinimo();
            if (u != null) {
                verticeJaVisitado[u.id] = true;

                for (int v = 0; v < n; v++) {
                    if (w[u.id][v] != 0 && !verticeJaVisitado[v] && w[u.id][v] < G.vertices[v].chave) {
                        G.vertices[v].pai = u.id;
                        G.vertices[v].chave = w[u.id][v];
                        filaPrioridade.atualizarValorChave(v);
                    }
                }
            }
        }
        imprimirMST(G, n, r);
    }

    public static void imprimirMST(Grafo grafo, int n, int r) {
        System.out.println("Arvore Geradora Minima cujo ponto de partida foi o vertice " + r + ":");
        int custoTotal = 0;
        for (int v = 0; v < n; v++) {
            if (grafo.vertices[v].id != r && grafo.vertices[v].pai != -1) {
                System.out.printf("%d -- %d (Peso: %d)\n", grafo.vertices[v].pai, grafo.vertices[v].id, grafo.vertices[v].chave);
                custoTotal += grafo.vertices[v].chave;
            }
        }
        System.out.println("Custo Total: " + custoTotal);
    }
}

public class AlgoritmoPrimImplementacao {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Grafo grafo;
        System.out.println("** ALGORITMO DE PRIM EM UM GRAFO **");

        System.out.println("Exemplo de como os dados devem estar no arquivo de entrada:");
        System.out.println("3");
        System.out.println("0 3 4");
        System.out.println("3 4 5");
        System.out.println("4 5 0");

        System.out.println("Informe o caminho de um arquivo txt (Ex.: \\Users\\nome\\Desktop\\Algoritmos\\nomeArquivo.txt): ");
        System.out.println("Nao esquecer de colocar .txt ao fim do nome do arquivo!");
        System.out.print("> ");
        String caminho = sc.nextLine();

        if (caminho == null) {
            System.out.println("Invalido");
            return;
        }

        File fileEntrada = new File(caminho);

        try (Scanner scanner = new Scanner(fileEntrada)) {
            // ler numero de vértices
            if (scanner.hasNextInt()) {
                int numVertices = scanner.nextInt();
                grafo = new Grafo(numVertices);

                // adicionar arestas ao grafo
                for (int i = 0; i < grafo.quantidadeVertices; i++) {
                    for (int j = 0; j < grafo.quantidadeVertices; j++) {
                        if (scanner.hasNextInt()) {
                            int peso = scanner.nextInt();
                            grafo.adicionarAresta(i, j, peso);
                        } else {
                            System.out.println("Erro: Matriz em formato invalido.");
                            return;
                        }
                    }
                }

                grafo.exibirGrafo();
                int v = 0;
                boolean valorValido = false;
                while (!valorValido) {
                    System.out.println("\nQual vertice sera o ponto de partida para o algoritmo Prim? (valores de 0 a " + (grafo.quantidadeVertices - 1) + ")");
                    v = sc.nextInt();
                    if (v < 0 || v > (grafo.quantidadeVertices - 1)) {
                        System.out.println("Valor invalido.");
                    } else {
                        valorValido = true;
                    }
                }

                System.out.println("\n\n");
                AlgoritmoPrim.encontrarPrimMST(grafo, v);

            } else {
                System.out.println("Erro: Arquivo vazio ou formato invalido.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo nao encontrado: " + e.getMessage());
        }

    }
}
