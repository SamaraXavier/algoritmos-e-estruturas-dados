Deseja-se implementar, em uma Linguagem de Programação, as Estruturas de Dados e algoritmos necessários, para uma árvore AVL binária dinâmica, que após a inserção de n nós mantenha-se balanceada. Os Conjuntos de Entradas para essa questão serão fornecidos através de Arquivos. Deve-se gerar um arquivo de saída, de acordo com o padrão especificado em detalhes na sequência. Recomenda-se o uso da ferramenta Notepad++ para a correta visualização do formato dos arquivos de entrada e do arquivo exemplo de saída.

**Entrada**: O arquivo de entrada consistirá de vários conjuntos de dados. Cada linha do arquivo conterá n elementos, representando valores de chaves dos nós que formarão a estrutura de dados. Cada valor deverá ser inserido na árvore de acordo com a sua ordem no conjunto de dados. Todos os elementos em cada conjunto de dados terão valores de chave diferentes.

**Saída**: Para cada conjunto de dados, o arquivo de saída deve conter os resultados de acordo com o que se pede abaixo.

---

Para cada conjunto de dados, a cada elemento inserido deve-se exibir uma linha contendo uma das mensagens especificadas na tabela abaixo, de acordo com o resultado da inserção.

### Mensagem

1. “arvore ja balanceada.”
2. “rotacao direita.”
3. “rotacao esquerda.”
4. “rotacao direita dupla.”
5. “rotacao esquerda dupla.”

### Causa
1. Quando, após a inserção do novo nó, a árvore permanece AVL.
2. Quando, após a inserção do novo nó, é necessária a realização de uma rotação direita para que a árvore vire uma AVL novamente.
3. Quando, após a inserção do novo nó, é necessária a realização de uma rotação esquerda para que a árvore vire uma AVL novamente.
4. Quando, após a inserção do novo nó, é necessária a realização de uma rotação direita dupla para que a árvore vire uma AVL novamente.
5. Quando, após a inserção do novo nó, é necessária a realização de uma rotação esquerda dupla para que a árvore vire uma AVL novamente.

---

Caso seja necessária a realização de alguma rotação, antes da impressão de uma das mensagens acima, deve-se imprimir a mensagem “no responsavel: #” (onde “#” representa a chave do nó responsável pela infração), além de cada elemento da árvore, em ordem, com o valor de seu fator de balanceamento entre parênteses. Os elementos devem ser impressos separados por um espaço em branco, e a linha com os elementos deve terminar com um espaço em branco.

Na linha seguinte à impressão da mensagem, deve-se imprimir cada elemento da árvore (nesse ponto, os elementos já deverão estar com os valores finais corrigidos, quando alguma correção for necessária), em ordem, com o valor de seu fator de balanceamento entre parênteses. Os elementos devem ser impressos separados por um espaço em branco e cada linha com os elementos deve terminar com um espaço em branco. A terceira linha após uma inserção deve conter o valor da altura total da árvore.

Entre cada conjunto de dados, uma linha em branco deve ser impressa. As últimas duas linhas do arquivo de saída devem estar em branco.

Para a resolução da questão, considerar que o fator de balanceamento de um nó é dado pela fórmula abaixo:

fb(v)=h(v.dir)-h(v.esq)

onde v é o nó avaliado, v.dir é  a subárvore direita do nó v e v.esq é a subárvore esquerda de v.
Verifique os arquivos de entrada e saída exemplos (L6Q1_in.txt e L6Q1_out.txt, respectivamente).

---
# Arquivo de Entrada

45 30 18 60 81 36 101 5 8 3\
17 90 87 30 23 6 84 60 3\
10 9 8 7 6 5 4 3 2 1

# Arquivo de Saída

arvore ja balanceada.\
45(0)\
1\
arvore ja balanceada.\
30(0) 45(-1)\
2\
no responsavel: 45\
18(0) 30(-1) 45(-2)\
rotacao direita.\
18(0) 30(0) 45(0)\
2\
arvore ja balanceada.\
18(0) 30(+1) 45(+1) 60(0)\
3\
no responsavel: 45\
18(0) 30(+2) 45(+2) 60(+1) 81(0)\
rotacao esquerda.\
18(0) 30(+1) 45(0) 60(0) 81(0)\
3\
no responsavel: 30\
18(0) 30(+2) 36(0) 45(-1) 60(-1) 81(0)\
rotacao esquerda dupla.\
18(0) 30(0) 36(0) 45(0) 60(+1) 81(0)\
3\
no responsavel: 60\
18(0) 30(0) 36(0) 45(+1) 60(+2) 81(+1) 101(0)\
rotacao esquerda.\
18(0) 30(0) 36(0) 45(0) 60(0) 81(0) 101(0)\
3\
arvore ja balanceada.\
5(0) 18(-1) 30(-1) 36(0) 45(-1) 60(0) 81(0) 101(0)\
4\
no responsavel: 18\
5(+1) 8(0) 18(-2) 30(-2) 36(0) 45(-2) 60(0) 81(0) 101(0)\
rotacao direita dupla.\
5(0) 8(0) 18(0) 30(-1) 36(0) 45(-1) 60(0) 81(0) 101(0)\
4\
no responsavel: 30\
3(0) 5(-1) 8(-1) 18(0) 30(-2) 36(0) 45(-2) 60(0) 81(0) 101(0)\
rotacao direita.\
3(0) 5(-1) 8(0) 18(0) 30(0) 36(0) 45(-1) 60(0) 81(0) 101(0)\
4\
\
arvore ja balanceada.\
17(0)\
1\
arvore ja balanceada.\
17(+1) 90(0)\
2\
no responsavel: 17\
17(+2) 87(0) 90(-1)\
rotacao esquerda dupla.\
17(0) 87(0) 90(0)\
2\
arvore ja balanceada.\
17(+1) 30(0) 87(-1) 90(0)\
3\
no responsavel: 17\
17(+2) 23(0) 30(-1) 87(-2) 90(0)\
rotacao esquerda dupla.\
17(0) 23(0) 30(0) 87(-1) 90(0)\
3\
no responsavel: 87\
6(0) 17(-1) 23(-1) 30(0) 87(-2) 90(0)\
rotacao direita.\
6(0) 17(-1) 23(0) 30(0) 87(0) 90(0)\
3\
arvore ja balanceada.\
6(0) 17(-1) 23(+1) 30(+1) 84(0) 87(-1) 90(0)\
4\
no responsavel: 30\
6(0) 17(-1) 23(+2) 30(+2) 60(0) 84(-1) 87(-2) 90(0)\
rotacao esquerda dupla.\
6(0) 17(-1) 23(+1) 30(0) 60(0) 84(0) 87(-1) 90(0)\
4\
no responsavel: 17\
3(0) 6(-1) 17(-2) 23(0) 30(0) 60(0) 84(0) 87(-1) 90(0)\
rotacao direita.\
3(0) 6(0) 17(0) 23(+1) 30(0) 60(0) 84(0) 87(-1) 90(0)\
4\
\
arvore ja balanceada.\
10(0)\
1\
arvore ja balanceada.\
9(0) 10(-1)\
2\
no responsavel: 10\
8(0) 9(-1) 10(-2)\
rotacao direita.\
8(0) 9(0) 10(0)\
2\
arvore ja balanceada.\
7(0) 8(-1) 9(-1) 10(0)\
3\
no responsavel: 8\
6(0) 7(-1) 8(-2) 9(-2) 10(0)\
rotacao direita.\
6(0) 7(0) 8(0) 9(-1) 10(0)\
3\
no responsavel: 9\
5(0) 6(-1) 7(-1) 8(0) 9(-2) 10(0)\
rotacao direita.\
5(0) 6(-1) 7(0) 8(0) 9(0) 10(0)\
3\
no responsavel: 6\
4(0) 5(-1) 6(-2) 7(-1) 8(0) 9(0) 10(0)\
rotacao direita.\
4(0) 5(0) 6(0) 7(0) 8(0) 9(0) 10(0)\
3\
arvore ja balanceada.\
3(0) 4(-1) 5(-1) 6(0) 7(-1) 8(0) 9(0) 10(0)\
4\
no responsavel: 4\
2(0) 3(-1) 4(-2) 5(-2) 6(0) 7(-2) 8(0) 9(0) 10(0)\
rotacao direita.\
2(0) 3(0) 4(0) 5(-1) 6(0) 7(-1) 8(0) 9(0) 10(0)\
4\
no responsavel: 5\
1(0) 2(-1) 3(-1) 4(0) 5(-2) 6(0) 7(-2) 8(0) 9(0) 10(0)\
rotacao direita.\
1(0) 2(-1) 3(0) 4(0) 5(0) 6(0) 7(-1) 8(0) 9(0) 10(0)\
4