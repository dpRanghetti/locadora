package com.unialfa.view;

import com.unialfa.model.Filme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropdownExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Exemplo de Dropdown de Filmes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria uma lista de filmes
        Filme[] filmes = {
                new Filme(1, "O Poderoso Chefão", "Francis Ford Coppola"),
                new Filme(2, "O Senhor dos Anéis: A Sociedade do Anel", "Peter Jackson"),
                new Filme(3, "Pulp Fiction: Tempo de Violência", "Quentin Tarantino"),
                new Filme(4, "Forrest Gump: O Contador de Histórias", "Robert Zemeckis")
        };

        // Cria o JComboBox e adiciona os filmes
        JComboBox<Filme> comboBox = new JComboBox<>(filmes);

        // Adiciona um ActionListener ao JComboBox
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

        frame.add(comboBox);
        frame.setSize(400, 70);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
