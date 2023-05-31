/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jar.infinity.solutions;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.mycompany.exemplobanco.Conexao;
import com.mycompany.exemplobanco.Empresa;
import com.mycompany.exemplobanco.Funcionario;
import com.mycompany.exemplobanco.VerificacaoCredenciais;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author pamarcato
 */
public class LoggerRede {
    
    private static final int MAX_FILE_SIZE = 1000000; // Tamanho máximo do arquivo em bytes
    private static final String LOG_DIRECTORY = "/home/ubuntu/Desktop/"; // Diretório de logs
    private static final String LOG_FILE_PREFIX = "logDeRede"; // Prefixo do nome do arquivo de log
    private static final String LOG_FILE_EXTENSION = ".txt"; // Extensão do arquivo de log

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Formato da data/hora para o log
    
    // O método Log recebe o nível de Log (LogLevel) e a mensagem a ser registrada:
    // O método Log gera uma linha de log formatada usando o método (GetLogFileName) e a grava no arquivo de log atual
    private static String getLogFileName() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return LOG_DIRECTORY + LOG_FILE_PREFIX + LOG_FILE_EXTENSION;
    }

    private static String getLogLine(String message) {
        String timestamp = dateFormat.format(new Date());
        return message;
    }

    public static void log(String message) {
        String logLine = getLogLine(message);

        // Verifica se o arquivo de log atual está muito grande
        String currentLogFileName = getLogFileName();
        File currentLogFile = new File(currentLogFileName);
        if (currentLogFile.exists() && currentLogFile.length() > MAX_FILE_SIZE) {
            currentLogFileName = getLogFileName();
            currentLogFile = new File(currentLogFileName);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(currentLogFile, true))) {
            out.println(logLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main() {
        // Exemplo de uso
        
        Looca looca = new Looca();
                String timestamp = dateFormat.format(new Date());
                String ipmac = looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac();
        String mensagem = String.format("----------%s----------\n"
                + "IpMac da rede: %s", timestamp, ipmac);
        
        log(mensagem);
    }
    
}
