/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Circuito;
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
public class CircuitoDAO {
    private Connection connection = null;
    private PreparedStatement pstdados = null;
    private ResultSet rsdados = null;
    private static final String path = System.getProperty("user.dir");
    private static final File config_file = new File(path + "/src/Controller/configuracaobd.properties");
    private static final String sqlconsultacircuitos = "SELECT * FROM circuitos order by id";
    private static final String sqlinserir = "INSERT INTO circuitos (id, pista, pais, cidade, extensao, voltas, record) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String sqlalterar = "UPDATE circuitos SET pista = ?, pais = ?, cidade = ?, extensao = ?, voltas = ?, record = ? WHERE id = ?";
    private static final String sqlaexcluir = "DELETE FROM circuitos WHERE id = ?";

    public CircuitoDAO() {
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
            pstdados = connection.prepareStatement(sqlconsultacircuitos, tipo, concorrencia);
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

    public Circuito getCircuito() {
        Circuito circ = null;
        if (rsdados != null) {
            try {
                int id = rsdados.getInt("id");
                String pista = rsdados.getString("pista");
                String pais = rsdados.getString("pais");
                String cidade = rsdados.getString("cidade");
                int extensao = rsdados.getInt("extensao");
                int voltas = rsdados.getInt("voltas");
                String record = rsdados.getString("record");
                circ = new Circuito(id, pista, pais, cidade, extensao, voltas, record);
            } catch (SQLException erro) {
                System.out.println(erro);
            }
        }
        return circ;
    }

    public boolean Inserir(Circuito circ) {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlinserir, tipo, concorrencia);
            pstdados.setInt(1, circ.getId());
            pstdados.setString(2, circ.getPista());
            pstdados.setString(3, circ.getPais());
            pstdados.setString(4, circ.getCidade());
            pstdados.setInt(5, circ.getExtensao());
            pstdados.setInt(6, circ.getVoltas());
            pstdados.setString(7, circ.getRecord());
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

    public boolean Alterar(Circuito circ) {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlalterar, tipo, concorrencia);
            pstdados.setString(1, circ.getPista());
            pstdados.setString(2, circ.getPais());
            pstdados.setString(3, circ.getCidade());
            pstdados.setInt(4, circ.getExtensao());
            pstdados.setInt(5, circ.getVoltas());
            pstdados.setString(6, circ.getRecord());
            pstdados.setInt(7, circ.getId());
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

    public boolean Excluir(Circuito circ) {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(sqlaexcluir, tipo, concorrencia);
            pstdados.setInt(1, circ.getId());
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

    public Circuito getCircuitoPorId(int id) {
        Circuito resp = null;
        try {
            ConsultarTodos();
            getRsdados();
            while (getRsdados().next()) {
                resp = getCircuito();
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
        Circuito temp = null;
        try {
            ConsultarTodos();
            getRsdados();
            getRsdados().last();
            temp = getCircuito();
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
