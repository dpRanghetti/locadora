package com.unialfa.dao;

import com.unialfa.model.Filme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDao {
    private Connection connection;

    public FilmeDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javadb?useTimezone=true&serverTimezone=UTC", "root", "root");
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void inserir(Filme Filme) throws SQLException {
        ResultSet rs = connection.prepareStatement("select max(idFilme) from Filme").executeQuery();
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1) + 1;

            String sql = "insert into Filme values(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, Filme.getNome());
            ps.execute();
        }
        rs.close();
    }

    public void atualizar(Filme filme) throws SQLException {
        String sql = "update Filme set nome = ? where idFilme = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, filme.getNome());
        //ps.setInt(2, filme.getIdFilme());
        ps.execute();
    }

    public void deletar(int id) throws SQLException {
        String sql = "delete from Filme where idFilme = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public Filme consultarPorId(int id) throws SQLException {
        Filme filme = new Filme("", "");

        String sql = "select * from Filme where idFilme = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            filme.setNome(rs.getString("nome"));
        }
        rs.close();

        return filme;
    }

    public List<Filme> listarContenhaNome(String nome) throws SQLException {
        List<Filme> Filmes = new ArrayList<Filme>();

        String sql = "select * from Filme where nome like ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + nome + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Filme Filme = new Filme(rs.getString("nome"), rs.getString("diretor"));
            Filmes.add(Filme);
        }
        rs.close();

        return Filmes;
    }

    public List<Filme> listarTodos() throws SQLException {
        List<Filme> Filmes = new ArrayList<Filme>();

        ResultSet rs = connection.prepareStatement("select * from Filme").executeQuery();
        while (rs.next()) {
            Filmes.add(new Filme(rs.getString("nome"), rs.getString("diretor")));
        }
        rs.close();

        return Filmes;
    }
}
