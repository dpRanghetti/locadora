package com.unialfa.view;

import com.unialfa.model.Filme;
import com.unialfa.service.FilmeService;

import javax.swing.*;
import java.awt.*;

public class FilmeForm extends JFrame {

    private FilmeService service;
    private JLabel labelNomeFilme;
    private JTextField campoNomeFilme;
    private JLabel labelDiretor;
    private JTextField campoDiretor;
    private JButton botaoSalvar;
    private JList<Filme> listaDeFilmes;

    public FilmeForm() {
        service = new FilmeService();

        setTitle("Filme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        labelNomeFilme = new JLabel("Nome do Filme:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelNomeFilme, constraints);

        campoNomeFilme = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoNomeFilme, constraints);

        labelDiretor = new JLabel("Diretor do Filme:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelDiretor, constraints);

        campoDiretor = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoDiretor, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        painelEntrada.add(botaoSalvar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        listaDeFilmes = new JList<>(carregarDadosLocadoras());
        listaDeFilmes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listaDeFilmes);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);

        //pack();
        setLocationRelativeTo(null);
    }

    private DefaultListModel<Filme> carregarDadosLocadoras() {
        DefaultListModel<Filme> model = new DefaultListModel<>();
        service.listarFilmes().forEach(model::addElement);
        return model;
    }

    private void executarAcaoDoBotao() {
        service.salvar(new Filme(campoNomeFilme.getText(), campoDiretor.getText()));
        campoNomeFilme.setText("");
        listaDeFilmes.setModel(carregarDadosLocadoras());

    }
}
