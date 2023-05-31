package com.github.britooo.looca.api.teste;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import com.github.britooo.looca.api.util.Conversor;
import com.mycompany.exemplobanco.Conexao;
import com.mycompany.exemplobanco.ConexaoMysql;
import com.mycompany.exemplobanco.Empresa;
import com.mycompany.exemplobanco.MonitoramentoDeRecurso;
import com.mycompany.exemplobanco.SlackIntegration;
import com.mycompany.exemplobanco.Totem;
import com.mycompany.exemplobanco.VerificarLimites;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class TesteApi {
    
    public TesteApi() {
        initComponents();
    }
    
    private static void AtualizaDados(String login, String senha, String numeroDeIdentificacaoDoTotem) {
        Conexao conexao = new Conexao();
        ConexaoMysql conexao2 = new ConexaoMysql();
        JdbcTemplate con = conexao.getConexaoDoBanco();
        JdbcTemplate con2 = conexao2.getConexaoDoBanco();
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                
                Looca looca = new Looca();
                
                JSONObject json = new JSONObject();
                JSONObject json2 = new JSONObject();
                Sistema sistema = new Sistema();
                Memoria memoria = new Memoria();
                Temperatura temperatura = new Temperatura();
                Processador processador = new Processador();
                Empresa empresa = new Empresa();
                MonitoramentoDeRecurso monitor = new MonitoramentoDeRecurso();
                
                json.put("text", "Seu Totem " +numeroDeIdentificacaoDoTotem+ " foi reiniciado por conta do uso crítico de CPU.");
                json2.put("text", "Seu Totem " +numeroDeIdentificacaoDoTotem+ " foi reiniciado por conta do uso crítico de memória Ram.");

                // Inseri no banco os dados da ApiLooca
                con.update("insert into MonitoramentoDeRecursos (UsoDeCpu, UsoDeMemoriaRam, vidaUtil, DataHora, fkTotem, fkEmpresa, UsoDeDisco) values (?, ?, ?, GETDATE(), ?,?, ? )", processador.getUso(), memoria.getEmUso(), Conversor.formatarSegundosDecorridos(sistema.getTempoDeAtividade()), numeroDeIdentificacaoDoTotem, login, looca.getGrupoDeDiscos().getVolumes().get(0).getUsoAtual());
                con2.update("insert into monitoramentoderecursos (UsoDeCpu, UsoDeMemoriaRam, DataHora) values (?, ?, now())", processador.getUso(), memoria.getEmUso());
                System.out.println("Dados inseridos no banco...");
                
                // Exibe os dados da maquina no console
                System.out.println("Sistema");
                System.out.println("-------------------------------------------------------------------");
                
                System.out.println(sistema);
                System.out.println("-------------------------------------------------------------------");
                
                System.out.println("Memoria");
                System.out.println("-------------------------------------------------------------------");
                System.out.println(memoria);
                System.out.println("-------------------------------------------------------------------");
                
                System.out.println("Processador");
                System.out.println("-------------------------------------------------------------------");
                System.out.println(processador.getUso());
                System.out.println("-------------------------------------------------------------------");

//              Codigo para reinicar totem
                Timer timerReinicio = new Timer();
                TimerTask taskProcess = new TimerTask() {
                    
                    @Override
                    public void run() {
                        System.out.println("***** REINICIANDO TOTEM *****");
                        
                        SlackIntegration slack = new SlackIntegration();
                        
                        try {
                            SlackIntegration.sendMessage(json);
                        } catch (IOException ex) {
                            Logger.getLogger(TesteApi.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TesteApi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        MonitoramentoDeRecurso monitor = new MonitoramentoDeRecurso();
                        
                        monitor = con.queryForObject("SELECT TOP 1 idCapacidade FROM MonitoramentoDeRecursos WHERE fkEmpresa = ? AND fkTotem = ? ORDER BY idCapacidade DESC", new BeanPropertyRowMapper<>(MonitoramentoDeRecurso.class), login, numeroDeIdentificacaoDoTotem);
                        
                        con.update("insert into Reinicializacoes (MonitoramentoDeRecursos_idCapacidade, Data , MonitoramentoDeRecursos_FkNumIdenti, MonitoramentoDeRecursos_fkEmpresa, fkLimites, FkTotem, FkEmpresa) values (?, getDate(), ?, ?, ?, ?, ?)",monitor.getIdCapacidade(), numeroDeIdentificacaoDoTotem, login, "critico", numeroDeIdentificacaoDoTotem, login);

//                      Executar Reiniciacao
                        try {
                            // Comando que você deseja executar
                            String comando = "sudo reboot";

                            // Cria o ProcessBuilder com o comando
                            ProcessBuilder pb = new ProcessBuilder("bash", "-c", comando);

                            // Redireciona a saída de erro para a saída padrão
                            pb.redirectErrorStream(true);

                            // Inicia o processo
                            Process processo = pb.start();

                            // Obtém a saída do processo
                            BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                            String linha;
                            while ((linha = reader.readLine()) != null) {
                                System.out.println(linha);
                            }

                            // Aguarda a finalização do processo
                            int status = processo.waitFor();
                            System.out.println("O comando foi executado com sucesso. Código de saída: " + status);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                
                TimerTask taskMemor = new TimerTask() {
                    
                    @Override
                    public void run() {
                        System.out.println("***** REINICIANDO TOTEM *****");
                        
                        SlackIntegration slack = new SlackIntegration();
                        
                        try {
                            SlackIntegration.sendMessage(json2);
                        } catch (IOException ex) {
                            Logger.getLogger(TesteApi.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TesteApi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        MonitoramentoDeRecurso monitor = new MonitoramentoDeRecurso();
                        
                        monitor = con.queryForObject("SELECT TOP 1 idCapacidade FROM MonitoramentoDeRecursos WHERE fkEmpresa = ? AND fkTotem = ? ORDER BY idCapacidade DESC", new BeanPropertyRowMapper<>(MonitoramentoDeRecurso.class), login, numeroDeIdentificacaoDoTotem);
                        
                        con.update("insert into Reinicializacoes (MonitoramentoDeRecursos_idCapacidade, Data, MonitoramentoDeRecursos_FkNumIdenti, MonitoramentoDeRecursos_fkEmpresa, fkLimites, FkTotem, FkEmpresa) values (?, getDate(), ?, ?, ?, ?, ?)",monitor.getIdCapacidade(), numeroDeIdentificacaoDoTotem, login, "critico", numeroDeIdentificacaoDoTotem, login);

//                      Executar Reiniciacao
                        try {
                            // Comando que você deseja executar
                            String comando = "sudo reboot";

                            // Cria o ProcessBuilder com o comando
                            ProcessBuilder pb = new ProcessBuilder("bash", "-c", comando);

                            // Redireciona a saída de erro para a saída padrão
                            pb.redirectErrorStream(true);

                            // Inicia o processo
                            Process processo = pb.start();

                            // Obtém a saída do processo
                            BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                            String linha;
                            while ((linha = reader.readLine()) != null) {
                                System.out.println(linha);
                            }

                            // Aguarda a finalização do processo
                            int status = processo.waitFor();
                            System.out.println("O comando foi executado com sucesso. Código de saída: " + status);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                
                VerificarLimites ver = new VerificarLimites();

                // Verificar 
                if (processador.getUso() < ver.limiteCriticoCpu(numeroDeIdentificacaoDoTotem, login)) {
                    taskProcess.cancel();
                } else {
                    timerReinicio.scheduleAtFixedRate(taskProcess, 30000, 30000);
                }
                
                if (memoria.getPorcentagemEmUso() < ver.limiteCriticoMemoria(numeroDeIdentificacaoDoTotem, login)) {
                    taskMemor.cancel();
                } else {
                    timerReinicio.scheduleAtFixedRate(taskMemor, 30000, 30000);
                }
            }
        },
                0, 3000);
        
        new Timer()
                .scheduleAtFixedRate(new TimerTask() {
                    Memoria memoria = new Memoria();
                    Processador processador = new Processador();
                    
                    @Override
                    public void run() {
                        memoria.avisoMemoria(numeroDeIdentificacaoDoTotem, login);
                        processador.avisoCpu(numeroDeIdentificacaoDoTotem, login);
                    }
                },
                        0, 600000);
    }
    
    public static void main(String login, String senha, String numeroDeIdentificacaoDoTotem) {
        if (GraphicsEnvironment.isHeadless()) {
            AtualizaDados(login, senha, numeroDeIdentificacaoDoTotem);
        } else {
            new Runnable() {
                public void run() {
                    new TesteApi().setVisible(true);
                }
            };
            AtualizaDados(login, senha, numeroDeIdentificacaoDoTotem);
        }
    }
    
    private void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
