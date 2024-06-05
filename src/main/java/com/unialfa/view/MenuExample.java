package com.unialfa.view;


import javax.swing.*;

public class MenuExample {
    public static void main(String[] args) {
        // Cria a janela principal
        JFrame frame = new JFrame("Exemplo de Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria a barra de menu
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

        // Torna a janela visível
        frame.setVisible(true);
    }

    private static void exit(){
        System.exit(0);
    }
}
