package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class TVertice {
    int id;
    int d; //distandia minima para se chegar nesse vertice partindo do vertice fonte
    int pai;

    public TVertice(int id) {
        this.id = id;
        this.d = Integer.MAX_VALUE;
        this.pai = -1;
    }
}

class ListaPrioridade {
    TVertice[] vertices;
    int tamanho;
    int ultimo;

    public ListaPrioridade(int tamanho) {
        this.tamanho = tamanho;
        this.vertices = new TVertice[tamanho];
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

        if (l <= ultimo && (vertices[l].d < vertices[menor].d ||
                (vertices[l].d == vertices[menor].d && vertices[l].id < vertices[menor].id))) {
            menor = l;
        }
        if (r <= ultimo && (vertices[r].d < vertices[menor].d ||
                (vertices[r].d == vertices[menor].d && vertices[r].id < vertices[menor].id))) {
            menor = r;
        }

        if (menor != i) {
            trocar(i, menor);
            minHeapify(menor);
        }
    }

    public void inserir(TVertice v) {
        if (ultimo < tamanho - 1) {
            ultimo++;
            vertices[ultimo] = v;
            diminuirChave(ultimo);
        } else {
            System.out.println("Overflow");
        }
    }

    public void diminuirChave(int i) {
        while (i > 0 && (vertices[i].d < vertices[(i - 1) / 2].d ||
                (vertices[i].d == vertices[(i - 1) / 2].d && vertices[i].id < vertices[(i - 1) / 2].id))) {
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
        TVertice temp = vertices[i];
        vertices[i] = vertices[j];
        vertices[j] = temp;
    }

    public TVertice extrairMinimo() {
        if (ultimo == -1) return null;
        TVertice minVertice = vertices[0];
        trocar(0, ultimo);
        ultimo--;
        minHeapify(0);
        return minVertice;
    }

    public boolean isEmpty() {
        return ultimo == -1;
    }
}

class TGrafo {
    TVertice[] vertices;
    int[][] matrizAdjacencia;
    int quantidadeVertices;

    public TGrafo(int quantidadeVertices) {
        this.quantidadeVertices = quantidadeVertices;
        this.vertices = new TVertice[quantidadeVertices];
        this.matrizAdjacencia = new int[quantidadeVertices][quantidadeVertices];

        // Inicializar vertices e matriz de adjacencia com inf em todas as posicoes
        for (int i = 0; i < quantidadeVertices; i++) {
            vertices[i] = new TVertice(i);
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

class AlgoritmoDijkstra {
    private static final int Inf = Integer.MAX_VALUE;

    public static void Dijkstra(TGrafo G, int r) {
        int n = G.quantidadeVertices;
        int[][] w = G.matrizAdjacencia;
        boolean[] verticeJaVisitado = new boolean[n];
        ListaPrioridade filaPrioridade = new ListaPrioridade(n);

        // garantir que todos os valores foram inicializados corretamente e inserir vertices em
        for (int u = 0; u < n; u++) {
            G.vertices[u].d = Inf;
            G.vertices[u].pai = -1;
            verticeJaVisitado[u] = false;
            filaPrioridade.inserir(G.vertices[u]);
        }

        G.vertices[r].d = 0;
        filaPrioridade.atualizarValorChave(r);

        while (!filaPrioridade.isEmpty()) {
            TVertice u = filaPrioridade.extrairMinimo();
            if (u != null) {
                verticeJaVisitado[u.id] = true;

                for (int v = 0; v < n; v++) {
                    if (w[u.id][v] > 0 && !verticeJaVisitado[v] && w[u.id][v] < G.vertices[v].d) {
                        relaxarNo(u, G.vertices[v], w[u.id][v]);
                        filaPrioridade.atualizarValorChave(v);
                    }
                }
            }
        }

        exibirResultado(G.vertices, n, r);
    }

    public static void relaxarNo(TVertice u, TVertice v, int distancia) {
        if (v.d > u.d + distancia) {
            v.d = u.d + distancia;
            v.pai = u.id;
        }
    }

    // exibe o resultado final obtido pelo algoritmo
    public static void exibirResultado(TVertice[] vertices, int n, int r) {
        System.out.println("Distancia minima de cada vertice ao vertice " + r + ":");
        for (int v = 0; v < n; v++) {
            System.out.printf("%d --> %d = %d\n", r, vertices[v].id, vertices[v].d);
        }

        System.out.println("\nMenor caminho a partir do vértice " + r + ":");
        System.out.println("pai --> destino (distancia de " + r + " ao destino)");
        for (int v = 0; v < n; v++) {
            if (vertices[v].id != r && vertices[v].pai != -1) {
                System.out.printf("%d --> %d (dist.: %d)\n", vertices[v].pai, vertices[v].id, vertices[v].d);
            }
        }
    }
}

public class AlgoritmoDijkstraImplementacao {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TGrafo grafo;

        System.out.println("** ALGORITMO DE DIJKSTRA EM UM GRAFO **");

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
            // Ler o número de vértices
            if (scanner.hasNextInt()) {
                int numVertices = scanner.nextInt();
                grafo = new TGrafo(numVertices);

                // Preencher matriz de adjacência corretamente
                for (int i = 0; i < grafo.quantidadeVertices; i++) {
                    for (int j = 0; j < grafo.quantidadeVertices; j++) {
                        if (scanner.hasNextInt()) {
                            int peso = scanner.nextInt();
                            grafo.adicionarAresta(i, j, peso);
                        } else {
                            System.out.println("Erro: Dados insuficientes na matriz.");
                            return;
                        }
                    }
                }

                grafo.exibirGrafo();
                int v = 0;
                boolean valorValido = false;
                while (!valorValido) {
                    System.out.println("\nQual vertice sera a fonte unica para o algoritmo Dijkstra? (valores de 0 a " + (grafo.quantidadeVertices - 1) + ")");
                    v = sc.nextInt();
                    if (v < 0 || v > (grafo.quantidadeVertices - 1)) {
                        System.out.println("Valor invalido.");
                    } else {
                        valorValido = true;
                    }
                }

                System.out.println("\n\n");
                AlgoritmoDijkstra.Dijkstra(grafo, v);
            } else {
                System.out.println("Erro: Arquivo vazio ou formato inválido.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }

    }
}
