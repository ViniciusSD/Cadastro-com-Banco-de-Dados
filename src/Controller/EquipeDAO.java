/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Equipe;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author luizg
 */
public class EquipeDAO {
    private Connection connection = null;
    private PreparedStatement pstdados = null;
    private ResultSet rsdados = null;
    private static final String path = System.getProperty("user.dir");
    private static final File config_file = new File(path + "/src/Controller/configuracaobd.properties");
    private static final String sqlconsultaequipes = "SELECT * FROM equipes order by id";
    private static final String sqlinserir = "INSERT INTO equipes (id, nome, chefe, sede, ano, carro, vitorias) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String sqlalterar = "UPDATE equipes SET nome = ?, chefe = ?, sede = ?, ano = ?, carro = ?, vitorias = ? WHERE id = ?";
    private static final String sqlaexcluir = "DELETE FROM equipes WHERE id = ?";

    public EquipeDAO() {
    }

    public boolean CriaConexao() {
        try {
            JDBCUtil.init(config_file);
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);

            DatabaseMetaData dbmt = connection.getMetaData();
            System.out.println("Nome do BD: " + dbmt.getDatabaseProductName());
            System.out.println("Versao do BD: " + dbmt.getDatabaseProductVersion());
            System.out.println("URL: " + dbmt.getURL());
            System.out.println("Driver: " + dbmt.getDriverName());
            System.out.println("Versao Driver: " + dbmt.getDriverVersion());
            System.out.println("Usuario: " + dbmt.getUserName());

            return true;
        } catch (ClassNotFoundException erro) {
            System.out.println("Falha ao carregar o driver JDBC." + erro);
        } catch (IOException erro) {
            System.out.println("Falha ao carregar o arquivo de configuração." + erro);
        } catch (SQLException erro) {
            System.out.println("Falha na conexao, comando sql = " + erro);
        }
        return false;
    }

    public boolean FechaConexao() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (SQLException erro) {
                System.err.println("Erro ao fechar a conexão = " + erro);
                return false;
            }
        } else {
            return false;
        }
    }
    
    public boolean ConsultarTodos() {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlconsultaequipes, tipo, concorrencia);
            rsdados = pstdados.executeQuery();
            return true;
        } catch (SQLException erro) {
            System.out.println("Erro ao executar consulta = " + erro);
        }
        return false;
    }

    public ResultSet getRsdados() {
        return rsdados;
    }
    
    public Equipe getEquipe() {
        Equipe equi = null;
        if (rsdados != null) {
            try {
                int id = rsdados.getInt("id");
                String nome = rsdados.getString("nome");
                String chefe = rsdados.getString("chefe");
                String sede = rsdados.getString("sede");
                int ano = rsdados.getInt("ano");
                String carro = rsdados.getString("carro");
                int vitorias = rsdados.getInt("vitorias");
                equi = new Equipe(id, nome, chefe, sede, ano, carro, vitorias);
            } catch (SQLException erro) {
                System.out.println(erro);
            }
        }
        return equi;
    }
    
    public boolean Inserir(Equipe equi) {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlinserir, tipo, concorrencia);
            pstdados.setInt(1, equi.getId());
            pstdados.setString(2, equi.getNome());
            pstdados.setString(3, equi.getChefe());
            pstdados.setString(4, equi.getSede());
            pstdados.setInt(5, equi.getAno());
            pstdados.setString(6, equi.getCarro());
            pstdados.setInt(7, equi.getVitorias());
            int resposta = pstdados.executeUpdate();
            pstdados.close();
            //DEBUG
            System.out.println("Resposta da inserção = " + resposta);
            //FIM-DEBUG
            if (resposta == 1) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException erro) {
            System.out.println("Erro na execução da inserção = " + erro);
        }
        return false;
    }
    
    public boolean Alterar(Equipe equi) {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlalterar, tipo, concorrencia);
            pstdados.setString(1, equi.getNome());
            pstdados.setString(2, equi.getChefe());
            pstdados.setString(3, equi.getSede());
            pstdados.setInt(4, equi.getAno());
            pstdados.setString(5, equi.getCarro());
            pstdados.setInt(6, equi.getVitorias());
            pstdados.setInt(7, equi.getId());
            int resposta = pstdados.executeUpdate();
            pstdados.close();
            //DEBUG
            System.out.println("Resposta da atualização = " + resposta);
            //FIM-DEBUG
            if (resposta == 1) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException erro) {
            System.out.println("Erro na execução da atualização = " + erro);
        }
        return false;
    }
    
    public boolean Excluir(Equipe equi) {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlaexcluir, tipo, concorrencia);
            pstdados.setInt(1, equi.getId());
            int resposta = pstdados.executeUpdate();
            pstdados.close();
            //DEBUG
            System.out.println("Resposta da exclusão = " + resposta);
            //FIM-DEBUG
            if (resposta == 1) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException erro) {
            System.out.println("Erro na execução da exclusão = " + erro);
        }
        return false;
    }
    
    public Equipe getEquipePorId(int id) {
        Equipe resp = null;
        try {
            ConsultarTodos();
            getRsdados();
            while (getRsdados().next()) {
                resp = getEquipe();
                if (resp.getId() == id) {
                    return resp;
                }
            }
        } catch (SQLException erro) {
            System.out.println("Erro na execução da exclusão = " + erro);
        }
        return null;
    }
    
    public int getUltimoId() {
        Equipe temp = null;
        try {
            ConsultarTodos();
            getRsdados();
            getRsdados().last();
            temp = getEquipe();
            ConsultarTodos();
            getRsdados();
            if (temp == null){
                return 0;
            } else {
                return temp.getId();
            }
        } catch (SQLException erro) {
            System.out.println("Erro na execução da exclusão = " + erro);
        }
        return 0;
    }
}
