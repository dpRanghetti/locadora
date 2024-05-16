package com.unialfa.view;

import com.unialfa.model.Filme;
import com.unialfa.service.FilmeService;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class FilmeForm extends JFrame {

    private FilmeService service;
    private JLabel labelId;
    private JTextField campoId;
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

        labelId = new JLabel("ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(20);
        campoId.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);

        labelNomeFilme = new JLabel("Nome do Filme:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeFilme, constraints);

        campoNomeFilme = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeFilme, constraints);

        labelDiretor = new JLabel("Diretor do Filme:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDiretor, constraints);

        campoDiretor = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoDiretor, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        painelEntrada.add(botaoSalvar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        listaDeFilmes = new JList<>(carregarDadosLocadoras());
        listaDeFilmes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaDeFilmes.addListSelectionListener(e -> selecionarFilme());

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
        service.salvar(construirFilme());
        campoNomeFilme.setText("");
        campoDiretor.setText("");
        campoId.setText("");
        listaDeFilmes.setModel(carregarDadosLocadoras());
    }

    private Filme construirFilme(){
        return campoId.getText().isEmpty()
                ? new Filme(campoNomeFilme.getText(), campoDiretor.getText())
                : new Filme(
                        parseInt(campoId.getText()),
                        campoNomeFilme.getText(),
                        campoDiretor.getText());
    }

    private void selecionarFilme(){
        var filme = listaDeFilmes.getSelectedValue();
        if (filme == null) return;
        campoNomeFilme.setText(filme.getNome());
        campoDiretor.setText(filme.getDiretor());
        campoId.setText(filme.getId().toString());
    }
}
