# Resumo da Avaliação - Equipe Natal (OAT 1)

## 1. Arquivos Revisados
- `src/main/java/br/com/mecaniqa/Peca.java`
- `src/main/java/br/com/mecaniqa/Servico.java`
- `src/main/java/br/com/mecaniqa/Gerenciador.java`
- `src/main/java/br/com/mecaniqa/MecaniQAApplication.java`

*(Commit avaliado: `4b175db` — último commit antes de 22/08/2026 23:59)*

## 2. Grade de Avaliação (Barema)

| Critério | Peso | Porcentagem Atingida | Nota Obtida | Resumo da Avaliação |
| :--- | :---: | :---: | :---: | :--- |
| **Aspectos dos Códigos** — Nome significativos de Classes, atributos e métodos | 0,5 | 100% | 0,50 | Nomes claros, consistentes e altamente expressivos em todo o projeto (`Peca`, `Servico`, `Gerenciador`, `proximoCodigoPeca`, `totalPecas`, etc.). |
| **Aspectos dos Códigos** — Nomenclatura de identificadores de acordo com o estilo da linguagem | 0,5 | 100% | 0,50 | Padrões do Java (PascalCase para classes e camelCase para atributos e métodos) seguidos perfeitamente. |
| **TADs e Gerenciador** — Estrutura Base: Definição das classes Peça e Serviço (TADs) estritamente como structs, contendo apenas atributos públicos e nenhum método interno. | 1,0 | 100% | 1,00 | `Peca` e `Servico` foram definidas estritamente como structs: apenas atributos `public`, sem construtores e sem getters/setters. |
| **TADs e Gerenciador** — Memória: Declaração e inicialização correta dos arrays unidimensionais de tamanho fixo para armazenar os dados. | 1,0 | 100% | 1,00 | Arrays estáticos unidimensionais (`Peca[100]` e `Servico[50]`) declarados e inicializados corretamente, sem uso de Collections de `java.util`. |
| **TADs e Gerenciador** — Isolamento: Toda a lógica de negócio (CRUD) foi isolada e implementada através de métodos estáticos na classe de Gerenciador | 1,0 | 100% | 1,00 | Toda a lógica de negócio (CRUD completo, busca linear e geração automática/sequencial de códigos) está isolada em métodos estáticos no `Gerenciador`, com o menu completamente integrado no `MecaniQAApplication`. |
| **Listas Estáticas** — Algoritmo (Busca): Implementação correta do método estático de Busca Linear, retornando o índice do registro ou um indicativo de erro. | 1,0 | 100% | 1,00 | `buscarPeca` e `buscarServico` implementam a busca linear corretamente, percorrendo até `totalPecas`/`totalServicos` e retornando o índice ou `-1`. |
| **Listas Estáticas** — Os métodos de atualização chamam obrigatoriamente os métodos de Busca Linear para descobrir o índice do objeto antes de acessar os atributos diretamente para modificá-los. | 1,0 | 100% | 1,00 | `atualizarPeca` e `atualizarServico` chamam obrigatoriamente `buscarPeca`/`buscarServico`, validam `-1` com *early return* e alteram os atributos públicos diretamente. |
| **Listas Estáticas** — Inserção de Peça: O método isolado adiciona a peça corretamente no primeiro espaço vazio e incrementa a variável de controle específica do array de peças. | 1,0 | 100% | 1,00 | Verifica capacidade máxima (`totalPecas >= pecas.length`), gera código sequencial único (`proximoCodigoPeca++`), insere em `pecas[totalPecas]` e incrementa `totalPecas`. |
| **Listas Estáticas** — Remoção de Peça: Algoritmo (Busca) e reorganização dos elementos contíguos do array de peças, atualizando o índice de controle sem deixar "buracos". | 1,0 | 100% | 1,00 | Chama `buscarPeca`, realiza shift-left corretamente sem deixar buracos, limpa a última posição com `null` e decrementa `totalPecas`. |
| **Listas Estáticas** — Inserção de Serviço: O método isolado adiciona o serviço no primeiro espaço vazio e incrementa a variável de controle específica do array de serviços. | 1,0 | 100% | 1,00 | Verifica capacidade máxima (`totalServicos >= servicos.length`), gera código sequencial único (`proximoCodigoServico++`), insere em `servicos[totalServicos]` e incrementa `totalServicos`. |
| **Listas Estáticas** — Remoção de Serviço: O método aplica exclusão lógica ou reorganiza os elementos contíguos do array de serviços, atualizando o índice de controle sem deixar "buracos". | 1,0 | 100% | 1,00 | Chama `buscarServico`, reorganiza os elementos contíguos via shift-left, atribui `null` à última posição e decrementa `totalServicos`. |

## 3. Resultado Final
- **Aspectos dos Códigos:** 1,00 / 1,00
- **TADs e Gerenciador:** 3,00 / 3,00
- **Listas Estáticas:** 6,00 / 6,00
- **Nota Final:** **10,00 / 10,00**

**Considerações Gerais:** Trabalho exemplar! A equipe atendeu rigorosamente a todos os critérios do barema e da especificação: TADs modelados estritamente como structs com atributos públicos, uso exclusivo de arrays estáticos sem `java.util`, separação clara entre `Gerenciador` (lógica estática) e `MecaniQAApplication` (menu completo e validado), geração de códigos sequenciais independentes do contador de tamanho, busca linear reutilizada em atualizações e remoções, e deslocamento (*shift-left*) sem deixar buracos. Parabéns pelo excelente resultado!
