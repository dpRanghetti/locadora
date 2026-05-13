# Manual de Swing — Projeto Locadora

Este manual documenta os componentes e funcionalidades do **Java Swing** utilizados no projeto `locadora`. Cada seção descreve o componente, sua finalidade, principais métodos usados no código e um exemplo extraído do próprio projeto.

## Sumário

- [1. Estrutura básica de uma aplicação Swing](#1-estrutura-básica-de-uma-aplicação-swing)
- [2. JFrame — Janela principal](#2-jframe--janela-principal)
- [3. SwingUtilities.invokeLater — Event Dispatch Thread](#3-swingutilitiesinvokelater--event-dispatch-thread)
- [4. JPanel e Layouts (BorderLayout, GridBagLayout)](#4-jpanel-e-layouts-borderlayout-gridbaglayout)
- [5. JLabel e JTextField — Rótulos e campos de texto](#5-jlabel-e-jtextfield--rótulos-e-campos-de-texto)
- [6. JButton e ActionListener](#6-jbutton-e-actionlistener)
- [7. JList e DefaultListModel](#7-jlist-e-defaultlistmodel)
- [8. JScrollPane — Barra de rolagem](#8-jscrollpane--barra-de-rolagem)
- [9. JComboBox — Dropdown](#9-jcombobox--dropdown)
- [10. JMenuBar, JMenu e JMenuItem](#10-jmenubar-jmenu-e-jmenuitem)
- [11. JTable e DefaultTableModel](#11-jtable-e-defaulttablemodel)
- [12. Boas práticas observadas no projeto](#12-boas-práticas-observadas-no-projeto)

---

## 1. Estrutura básica de uma aplicação Swing

Toda aplicação Swing parte de uma janela (`JFrame`) que recebe componentes (botões, campos, listas etc.). O ponto de entrada do projeto é o `Main`, que instancia o formulário principal dentro da Event Dispatch Thread (EDT):

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\Main.java:7-13
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var form = new FilmeForm();
            form.setVisible(true);
        });
    }
}
```

---

## 2. JFrame — Janela principal

O `JFrame` é a janela de nível superior da aplicação. Métodos principais utilizados no projeto:

- **`setTitle(String)`**: define o título da janela.
- **`setSize(int, int)`**: define a largura e a altura em pixels.
- **`setDefaultCloseOperation(int)`**: define o comportamento ao fechar (`EXIT_ON_CLOSE` finaliza a aplicação).
- **`setLocationRelativeTo(null)`**: centraliza a janela na tela.
- **`setVisible(true)`**: exibe a janela.
- **`getContentPane().add(...)`**: adiciona componentes ao painel de conteúdo.
- **`pack()`**: ajusta o tamanho da janela ao conteúdo.

Exemplo (herança de `JFrame`):

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:26-29
        setTitle("Filme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
```

---

## 3. SwingUtilities.invokeLater — Event Dispatch Thread

Componentes Swing **não são thread-safe**. Toda criação e manipulação de UI deve ocorrer na EDT. Usa-se `SwingUtilities.invokeLater` para garantir isso:

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\JTableExample.java:10-12
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JDBC to JTable Example with Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

---

## 4. JPanel e Layouts (BorderLayout, GridBagLayout)

`JPanel` é um contêiner usado para agrupar componentes. O layout define como os componentes são posicionados.

- **`BorderLayout`**: divide o painel em 5 regiões (`NORTH`, `SOUTH`, `EAST`, `WEST`, `CENTER`).
- **`GridBagLayout`**: layout em grade flexível, controlado por `GridBagConstraints` (`gridx`, `gridy`, `gridwidth`, `insets`).

Exemplo do `FilmeForm` usando os dois:

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:30-32
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
```

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:72-82
        JPanel painelSaida = new JPanel(new BorderLayout());

        listaDeFilmes = new JList<>(carregarDadosLocadoras());
        listaDeFilmes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaDeFilmes.addListSelectionListener(e -> selecionarFilme());

        JScrollPane scrollPane = new JScrollPane(listaDeFilmes);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);
```

---

## 5. JLabel e JTextField — Rótulos e campos de texto

- **`JLabel`**: exibe um texto não editável.
- **`JTextField(int columns)`**: campo de entrada de texto de uma linha.
  - **`setEnabled(false)`**: desativa edição (usado para o campo `ID`).
  - **`getText()` / `setText(String)`**: lê/escreve o conteúdo.

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:34-43
        labelId = new JLabel("ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(20);
        campoId.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);
```

---

## 6. JButton e ActionListener

`JButton` representa um botão clicável. A reação ao clique é registrada via `addActionListener`, geralmente com uma **expressão lambda**:

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:65-70
        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        painelEntrada.add(botaoSalvar, constraints);
```

Também é possível usar a forma com classe anônima (vista no `DropdownExample`):

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\DropdownExample.java:26-35
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Filme selectedFilme = (Filme) comboBox.getSelectedItem();
                if (selectedFilme != null) {
                    System.out.println("Filme selecionado: " + selectedFilme.getNome() +
                            ", Diretor: " + selectedFilme.getDiretor());
                }
            }
        });
```

---

## 7. JList e DefaultListModel

`JList<T>` apresenta uma lista de itens selecionáveis. O `DefaultListModel<T>` é o modelo mutável que armazena os elementos.

- **`setSelectionMode(int)`**: define o modo de seleção (ex.: `MULTIPLE_INTERVAL_SELECTION`).
- **`addListSelectionListener(...)`**: ouve mudanças de seleção.
- **`getSelectedValue()`**: retorna o item selecionado.
- **`setModel(ListModel)`**: substitui o modelo para refletir mudanças.

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:88-92
    private DefaultListModel<Filme> carregarDadosLocadoras() {
        DefaultListModel<Filme> model = new DefaultListModel<>();
        service.listarFilmes().forEach(model::addElement);
        return model;
    }
```

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:111-117
    private void selecionarFilme(){
        var filme = listaDeFilmes.getSelectedValue();
        if (filme == null) return;
        campoNomeFilme.setText(filme.getNome());
        campoDiretor.setText(filme.getDiretor());
        campoId.setText(filme.getId().toString());
    }
```

---

## 8. JScrollPane — Barra de rolagem

Envolve componentes (como `JList` ou `JTable`) adicionando barras de rolagem automáticas quando o conteúdo excede o espaço disponível:

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\FilmeForm.java:78-79
        JScrollPane scrollPane = new JScrollPane(listaDeFilmes);
        painelSaida.add(scrollPane, BorderLayout.CENTER);
```

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\JTableExample.java:25
            frame.add(new JScrollPane(table));
```

---

## 9. JComboBox — Dropdown

`JComboBox<T>` apresenta uma lista suspensa de itens. Pode ser tipado (`<Filme>`) e exibe a representação textual do objeto (`toString()`).

- **`getSelectedItem()`**: retorna o item selecionado.
- **`addActionListener(...)`**: dispara ao trocar a seleção.

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\DropdownExample.java:22-23
        // Cria o JComboBox e adiciona os filmes
        JComboBox<Filme> comboBox = new JComboBox<>(filmes);
```

---

## 10. JMenuBar, JMenu e JMenuItem

Permitem construir a barra de menus tradicional da janela.

- **`JMenuBar`**: a barra fixada no topo do `JFrame` via `setJMenuBar`.
- **`JMenu`**: um menu (ex.: "Arquivo").
- **`JMenuItem`**: opção dentro do menu.
- **`addSeparator()`**: insere um separador visual.

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\MenuExample.java:14-35
        JMenuBar menuBar = new JMenuBar();

        // Cria o menu "Arquivo"
        JMenu fileMenu = new JMenu("Arquivo");
        menuBar.add(fileMenu);

        // Cria itens de menu para o menu "Arquivo"
        JMenuItem newItem = new JMenuItem("Novo");
        JMenuItem openItem = new JMenuItem("Abrir");
        JMenuItem exitItem = new JMenuItem("Sair");

        // Adiciona itens ao menu "Arquivo"
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); // Adiciona um separador
        fileMenu.add(exitItem);

        // Adiciona um ActionListener ao item "Sair"
        exitItem.addActionListener(e -> exit());

        // Adiciona a barra de menu ao frame
        frame.setJMenuBar(menuBar);
```

---

## 11. JTable e DefaultTableModel

`JTable` exibe dados em forma de grade (linhas e colunas). O `DefaultTableModel` armazena as colunas e linhas dinamicamente.

- **`addColumn(String)`**: cria uma coluna.
- **`addRow(Object[])`**: adiciona uma linha.
- **`setDefaultEditor(Object.class, null)`**: bloqueia a edição das células.
- **`getSelectionModel().addListSelectionListener(...)`**: detecta seleção de linhas.
- **`getValueAt(row, col)`**: obtém o valor de uma célula.
- **`ListSelectionEvent.getValueIsAdjusting()`**: filtra eventos intermediários durante a seleção.

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\JTableExample.java:14-25
            JTable table = new JTable();

            // Preenche a tabela com dados do banco de dados
            fillTable(table);

            // Bloqueia a edição da tabela
            table.setDefaultEditor(Object.class, null);

            // Adiciona um ouvinte de seleção à tabela
            table.getSelectionModel().addListSelectionListener(e -> selecionarFilme(table, e));

            frame.add(new JScrollPane(table));
```

```@E:\workspace-java-alfa\locadora\src\main\java\com\unialfa\view\JTableExample.java:45-49
    private static void fillTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Diretor");
```

---

## 12. Boas práticas observadas no projeto

- **Separação em camadas**: a pasta `view` contém apenas a UI; regras de negócio ficam em `service` e acesso a dados em `dao`.
- **EDT**: criação da UI dentro de `SwingUtilities.invokeLater` (`Main`, `JTableExample`).
- **Lambdas**: uso de expressões lambda em `ActionListener` e `ListSelectionListener` para reduzir boilerplate.
- **Modelos dedicados**: uso de `DefaultListModel` e `DefaultTableModel` para alimentar `JList` e `JTable` dinamicamente.
- **Tipagem genérica**: `JList<Filme>` e `JComboBox<Filme>` evitam casts e melhoram a legibilidade.
- **Reaproveitamento do `toString()`**: a classe `Filme` (no model) fornece a representação exibida em `JList` e `JComboBox`.
- **Layouts compostos**: combinação de `BorderLayout` (estrutura geral) com `GridBagLayout` (formulário) para flexibilidade.

---

> Manual gerado a partir das classes em `src/main/java/com/unialfa/view` e `Main.java`.
