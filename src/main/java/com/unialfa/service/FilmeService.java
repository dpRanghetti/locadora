package com.unialfa.service;

import com.unialfa.model.Filme;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FilmeService {

    private static final String NOME_DIRETORIO = System.getProperty("user.dir");
    private static final String NOME_ARQUIVO = "\\Locadoras.txt";

    private final File locadoraFile = new File(NOME_DIRETORIO, NOME_ARQUIVO);

    public void salvar(Filme filme) {
        writerFile(filme.toConteudo());
    }

    private void writerFile(String conteudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(locadoraFile, true))) {
            writer.write(conteudo);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Filme> listarFilmes() {
        List<Filme> filmes = new ArrayList<>();
        readerFile().forEach(linha -> {
            String[] filmeArray = linha.split(":");
            filmes.add(new Filme(filmeArray[0], filmeArray[1]));
        });
        return filmes;
    }

    private List<String> readerFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(locadoraFile))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
