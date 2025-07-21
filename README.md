# CryptoExchanges

Este projeto é um aplicativo Android desenvolvido em Kotlin com Jetpack Compose, focado na listagem e visualização de detalhes de corretoras de criptomoedas.

## Estrutura do Projeto

O projeto segue uma estrutura modular e organizada, com as seguintes pastas principais:

-   `app/`: Contém o código-fonte principal do aplicativo.
    -   `src/main/java/br/com/seucaio/cryptoexchanges/`: Código-fonte Kotlin.
        -   `core/`: Contém extensões, utilitários e interfaces genéricas.
            -   `extension/`: Funções de extensão para tipos primitivos.
            -   `mapper/`: Interfaces para mapeamento de dados entre camadas.
            -   `utils/`: Utilitários gerais, incluindo formatadores de data/número e verificador de conectividade.
        -   `data/`: Camada de dados, responsável por buscar e armazenar informações.
            -   `error/`: Definições de erros personalizados.
            -   `local/`: Componentes de persistência local (Room).
                -   `dao/`: Data Access Objects para o Room.
                -   `database/`: Configuração do banco de dados Room.
                -   `entity/`: Entidades do banco de dados.
                -   `source/`: Fonte de dados local.
            -   `remote/`: Componentes de comunicação com a API remota.
                -   `dto/`: Data Transfer Objects (modelos de resposta da API).
                -   `service/`: Interfaces Retrofit e configuração de rede.
                -   `source/`: Fonte de dados remota.
            -   `mapper/`: Mapeadores entre DTOs, entidades e modelos de domínio.
            -   `repository/`: Implementação do repositório de dados.
        -   `di/`: Módulos de injeção de dependência (Koin).
        -   `domain/`: Camada de domínio, contendo a lógica de negócios.
            -   `model/`: Modelos de domínio.
            -   `repository/`: Interfaces do repositório de domínio.
            -   `usecase/`: Casos de uso da aplicação.
        -   `ui/`: Camada de interface do usuário (Jetpack Compose).
            -   `component/`: Componentes reutilizáveis da UI.
            -   `model/`: Modelos de dados específicos da UI.
            -   `navigation/`: Configuração de navegação.
            -   `screen/`: Telas do aplicativo (listagem e detalhes).
                -   `details/`: Tela de detalhes da exchange.
                -   `list/`: Tela de listagem de corretoras.
        -   `MyApplication.kt`: Classe de aplicação para inicialização global.
    -   `src/androidTest/`: Testes de instrumentação.
    -   `src/test/`: Testes unitários.
-   `gradle/`: Contém arquivos de configuração do Gradle.
    -   `libs.versions.toml`: Gerenciamento centralizado de dependências.

## Como Funciona

O aplicativo exibe uma lista de corretoras de criptomoedas. Ao clicar em uma exchange, o usuário é levado a uma tela de detalhes com informações mais aprofundadas.

1.  **Inicialização:** Ao iniciar, o aplicativo tenta buscar a lista de corretoras da API.
2.  **Cache:** Os dados são armazenados em um cache local (Room). Se houver dados no cache, eles são exibidos imediatamente enquanto uma atualização em segundo plano é tentada.
3.  **Atualização:** O aplicativo verifica a necessidade de atualizar os dados do cache. Se a rede estiver disponível, ele busca os dados mais recentes da API.
4.  **Tratamento de Erros:** Erros de rede ou API são tratados e exibidos ao usuário, com opções para tentar novamente.
5.  **Detalhes:** A tela de detalhes exibe informações como volume de negociação, rank, status de integração e datas importantes.

## Bibliotecas e Arquitetura

O projeto utiliza as seguintes bibliotecas e padrões arquiteturais:

-   **Jetpack Compose:** Toolkit moderno para construir UI nativa no Android.
-   **Koin:** Framework de injeção de dependência leve para Kotlin.
-   **Retrofit:** Cliente HTTP Type-safe para Android.
-   **Kotlin Serialization:** Biblioteca para serialização/desserialização de objetos Kotlin.
-   **Room Persistence Library:** Camada de abstração sobre SQLite para persistência de dados local.
-   **Timber:** Biblioteca de logging para Android.
-   **Chucker:** Interceptor HTTP para depuração de requisições de rede.
-   **LeakCanary:** Biblioteca de detecção de vazamento de memória.
-   **Arquitetura:** Segue princípios de Clean Architecture, com camadas de **Dados**, **Domínio** e **UI**, promovendo separação de responsabilidades e testabilidade.
-   **MVVM (Model-View-ViewModel):** Padrão de arquitetura utilizado na camada de UI para separar a lógica de negócios da interface do usuário.

## Screenshots

Aqui estão algumas capturas de tela do aplicativo em funcionamento:

| Tela de Listagem de corretoras | Tela de Detalhes da Exchange | Exemplo de Tratamento de Erro |
| :---------------------------: | :--------------------------: | :---------------------------: |
| ![Lista](https://github.com/user-attachments/assets/844e6983-6075-40c6-994f-90f4785b1694) | ![Detalhes](https://github.com/user-attachments/assets/b035a41f-240d-4fcb-9852-7f39016b56e0) | ![Erro](https://github.com/user-attachments/assets/e3871da2-fb8d-4356-90ab-d99b1e29ee66) |

<details>
<summary><h2>Roadmap</h2></summary>

Este roadmap descreve as funcionalidades e melhorias planejadas para o projeto, com base nas ideias e tarefas futuras.

### Qualidade de Código e Robustez

-   [x] **Monitoramento de Conectividade:** Criar uma classe ou utilitário para verificar o estado da conexão com a internet. A UI deve reagir a mudanças, informando ao usuário quando estiver offline.

-   [ ] **Cobertura de Testes com JaCoCo:**
    - Integrar o plugin do JaCoCo ao projeto para gerar relatórios de cobertura de testes, garantindo a qualidade e a testabilidade do código.

-   [ ] **Análise Estática com Detekt:**
    - Adicionar o Detekt para realizar análise estática de código Kotlin, identificando e prevenindo *code smells* e mantendo um alto padrão de código.

-   [x] **Tratamento Avançado de Erros:**
    - Implementar um tratamento de erros mais detalhado nas telas. Em vez de uma mensagem genérica, mostrar erros específicos (ex: "Servidor indisponível", "Nenhum resultado encontrado", "Você está offline") com possíveis ações para o usuário.

### Performance e Funcionalidades

-   [ ] **Paginação na Lista:**
    - Implementar paginação na lista de exchanges usando a biblioteca Paging 3 do Jetpack. Isso evitará o carregamento de todos os itens de uma vez, melhorando a performance e o uso de memória.

-   [x] **Cache de Dados com Room:**
    - Integrar o Room para criar um banco de dados local. Implementar uma estratégia de cache (ex: *cache-first* ou *network-first*) para que os dados da API sejam salvos localmente, permitindo o uso offline do app e reduzindo o número de chamadas à rede.

### Automação e DevOps

-   [ ] **CI/CD com GitHub Actions:**
    - Criar workflows (`.github/workflows`) para automação:
        - **CI (Continuous Integration):** Um script que roda a cada push/pull request para compilar o projeto, rodar o Detekt e executar os testes unitários.
        - **CD (Continuous Deployment):** Um script para automatizar a geração de builds de release e, opcionalmente, publicá-las na Google Play Store.

</details>

## Configuração da API Key

Este projeto requer uma chave de API para acessar os dados das exchanges. Siga os passos abaixo para configurá-la:

1.  Crie um arquivo chamado `local.properties` na raiz do seu projeto (no mesmo nível de `build.gradle.kts` principal).
2.  Adicione sua chave de API a este arquivo no seguinte formato:

    ```properties
    API_KEY="YOUR_API_KEY_HERE"
    ```
    Substitua `"YOUR_API_KEY_HERE"` pela sua chave de API real.

    **Exemplo:**
    ```properties
    API_KEY="ABCDEF1234567890"
    ```

Após configurar a chave, sincronize o projeto Gradle para que a chave seja injetada no `BuildConfig`.

