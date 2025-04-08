/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

//import bancodados.JDBCUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author fabricio
 */
public class ControleRelatorios {

    Connection connection = null;

    public static final String pasta_relatorios = System.getProperty("user.dir") + "/src/relatorios/";

    public static final File file_relatorio_piloto_fonte = new File(pasta_relatorios, "test.jrxml");
    public static final File relatorio_piloto_pdf = new File(pasta_relatorios, "relatorio_pilotos.pdf");

    public static final File file_relatorio_piloto_parametro_fonte = new File(pasta_relatorios, "relatorio_params_new.jrxml");
    public static final File relatorio_piloto_parametro_pdf = new File(pasta_relatorios, "relatorio_parametro.pdf");

    public static final File file_relatorio_pontos_fonte = new File(pasta_relatorios, "relatorio_pontos.jrxml");

    public ControleRelatorios() {
        try {
            String path = System.getProperty("user.dir");
            File config_file = new File(path, "src/Controller/configuracaobd.properties");
            JDBCUtil.init(config_file);
            connection = JDBCUtil.getConnection();
        } catch (ClassNotFoundException | SQLException | IOException erro) {
            System.out.println("Erro ao criar conexao com o BD.\n");
            System.err.println(erro);
        }
        //System.out.println(System.getProperties());    
    }

    public void RelatorioPiloto(boolean view) {
        JasperPrint impressao;
        try {
            // caso seja necessário, compila o relatório (caso deseja usar o jrxml)
            FileInputStream arquivo = new FileInputStream(file_relatorio_piloto_fonte);//jrxml
            JasperReport relatorio = JasperCompileManager.compileReport(arquivo);

            //ou poderia utilizar diretamente o relatorio compilado
            //FileInputStream relatorio = new FileInputStream(file_relatorio_cliente_compilado);//jasper
            //preenchimento do relatorio com a conexao e parametros
            impressao = JasperFillManager.fillReport(
                    relatorio,//arquivo jasper
                    null,//parametros
                    connection);//conexao
            if (view) {
                //opcao de visualizar o relatorio
                JasperViewer.viewReport(impressao, false);
            } else {
                //opcao de exportar o relatorio diretamente para arquivo
                JasperExportManager.exportReportToPdfFile(impressao, relatorio_piloto_pdf.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "Gerado o arquivo com sucesso: " + relatorio_piloto_pdf.getAbsolutePath());
            }
        } catch (JRException | FileNotFoundException erro) {
            System.err.println("Não foi possível exportar o relatório.\n\n" + erro);
        }
    }

    public void RelatorioPilotoParametro(Map parametros, boolean view) {
        JasperPrint impressao;
        try {
            //caso use o relatorio ja compilado (arquivo .jasper)
            //FileInputStream relatorio = new FileInputStream(file_relatorio_cliente_parametro_compilado);//jasper

            //ou caso seja necessário, compila o relatório (caso deseja usar o jrxml)
            FileInputStream arquivo = new FileInputStream(file_relatorio_piloto_parametro_fonte);//jrxml
            JasperReport relatorio = JasperCompileManager.compileReport(arquivo);//compilar
            impressao = JasperFillManager.fillReport(
                    relatorio,//arquivo .jasper
                    parametros,
                    connection);
            if (view) {
                JasperViewer.viewReport(impressao, false);
            } else {
                JasperExportManager.exportReportToPdfFile(impressao, relatorio_piloto_parametro_pdf.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "Gerado o arquivo " + relatorio_piloto_parametro_pdf.getAbsolutePath());
            }
        } catch (FileNotFoundException | JRException ex) {
            System.err.println("Não foi possível exportar o relatório.\n\n");
            System.err.println(ex);
        }
    }

    public void RelatorioPontos() {
        JasperPrint impressao;
        try {
            //caso use o relatorio ja compilado (arquivo .jasper)
            //FileInputStream relatorio = new FileInputStream(file_relatorio_vendas_compilado);//jasper

            //ou caso seja necessário, compila o relatório (caso deseja usar o jrxml)
            FileInputStream arquivo = new FileInputStream(file_relatorio_pontos_fonte);//jrxml
            JasperReport relatorio = JasperCompileManager.compileReport(arquivo);//compilar
            impressao = JasperFillManager.fillReport(
                    relatorio,//arquivo compilado ou .jasper ou o .jrxml compilado (veja acima)
                    null,
                    connection);

            JasperViewer.viewReport(impressao, false);
        } catch (FileNotFoundException | JRException erro) {
            System.err.println("Não foi possível visualizar o relatório.\n\n" + erro);
        }
    }
}
