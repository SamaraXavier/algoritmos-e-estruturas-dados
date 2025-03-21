# Sistema de Controle para Jogo de Ação Baseada em Turnos

## Introdução
Este projeto implementa um **Sistema de Controle para um Jogo de Ação Baseado em Turnos**, seguindo as regras e funcionamento descritos abaixo. O jogo consiste em um combate entre dois times de lutadores, onde cada lutador possui atributos específicos e as batalhas ocorrem de forma ordenada e automatizada.

----

## Requisitos do Sistema

1. **Times e Lutadores**:
    - O sistema suporta **exatamente dois times** de lutadores rivais.
    - Cada time pode ter um **número variável de lutadores**.
    - **Sem limite** para o número de lutadores em cada time.
    - Cada lutador possui:
        1. Identificador único.
        2. Time ao qual pertence.
        3. Valor de dano.
        4. Número de pontos de vida.
        5. Valor base de iniciativa (entre 1 e 100).
    - Um lutador é **considerado vivo** se seus pontos de vida forem **maiores que 0**.

2. **Turnos do Jogo**:
    - O jogo ocorre em turnos, cada um dividido em **três fases**:
        1. **Organização dos Times**
        2. **Combate**
        3. **Resultados**
    - O jogo termina quando **um dos times não possui mais lutadores vivos**.

---

## Funcionamento

### 1. Organização dos Times
Nesta fase, o sistema oferece um menu interativo para as seguintes ações:

1. **Inserção de Lutadores**:

    - O usuário pode criar lutadores e adicioná-los aos times.
    - O sistema impede a criação de lutadores com identificadores duplicados, não podendo se repetir entre times ou em listas de lutadores mortos.
    - Se houver tentativa de inserir um identificador já existente, o sistema deve exibir uma mensagem de erro.

2. **Relatório de Status**:

    - O usuário pode visualizar os lutadores vivos e mortos de um time, exibidos em ordem decrescente de iniciativa.

3. **Fuga de Lutador**:

    - O usuário pode **remover lutadores vivos** do combate fornecendo seu identificador.

Após essas ações, os lutadores vivos de cada time são organizados em **filas** com base em sua iniciativa (ordem decrescente), e o combate começa.


### 2. Combate
O combate ocorre automaticamente:

1. Os primeiros lutadores das filas de cada time batalham simultaneamente.

    - São removidos da fila. Se estiverem aptos, atacam simultaneamente, causando dano ao oponente.
    - O dano de um lutador é subtraído dos pontos de vida do adversário.
    - Se um lutador tiver **0 ou menos pontos de vida**, ele é movido para o **cemitério do time**.
    - Lutadores sobreviventes voltam para a fila de combate.

2. Esse processo se repete até que todos os lutadores tenham atacado uma vez ou algum time fique vazio.


### 3. Resultados

Ao fim de cada turno:

1. O score dos times é calculado com base no número de lutadores do adversário no cemitério.
2. O jogo é encerrado se uma das **Condições de Término** for atingida:
    - Um time não tem mais lutadores vivos.
    - Um time atinge score ≥ 20 enquanto o adversário não.
    - Ambos atingem score > 20 e o time com maior score vence.
    - Ambos ficam vazios, e o time com maior score vence.
    - Se ambos ficarem vazios e tiverem o mesmo score, o jogo termina em empate.
    - Caso nenhuma condição seja alcançada, um **novo turno inicia**.

---

## Como Executar o Sistema
1. **Compilar/Executar o programa** na linguagem de programação escolhida.
2. **Interagir pelo menu** para adicionar lutadores, visualizar status e iniciar turnos.
3. **Acompanhar os turnos** automáticos até o final do jogo.

---

## Implementação
Todas as Estruturas de Dados e Algoritmos devem ser implementados conforme especificado.

Estruturas recomendadas:

- **Filas** para organização dos times.
- **Listas sequenciais e ordenadas** para os cemitérios.

O jogo deve ser capaz de gerenciar dinamicamente os lutadores e seguir as regras acima para garantir um combate justo e funcional.

---

## Considerações Finais
Este jogo foi projetado para simular batalhas por turnos com um sistema justo e interativo. Ele pode ser expandido com mais funcionalidades, como habilidades especiais, novos tipos de combate ou modos de jogo alternativos.
