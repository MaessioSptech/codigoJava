package com.github.britooo.looca.api.group.memoria;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import com.mycompany.exemplobanco.SlackIntegration;
import com.mycompany.exemplobanco.VerificarLimites;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class Memoria {

    private final HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    SlackIntegration slack = new SlackIntegration();
    JSONObject json = new JSONObject();
    JSONObject json2 = new JSONObject();
    VerificarLimites ver = new VerificarLimites();

    /**
     * Retorna a quantidade de <b>memória física</b> atualmente <b>disponível</b>, <b>em bytes</b>.
     *
     * @return Quantidade de memória física atualmente disponível, <b>em bytes</b>.
     */
    public Double getDisponivel() {
        return convertBytesToGigabytes(this.hardware.getMemory().getAvailable());
    }

    /**
     * Retorna a quantidade de <b>memória física real</b>, <b>em bytes</b>.
     *
     * @return Quantidade de memória física real, <b>em bytes</b>.
     */
    public Double getTotal() {
        return convertBytesToGigabytes(this.hardware.getMemory().getTotal());
    }

    /**
     * Retorna a quantidade de <b>memória em uso</b>, <b>em bytes</b>.
     *
     * @return Quantidade de memória em uso, <b>em bytes</b>.
     */
public Double getEmUso() {
    double total = getTotal();
    double disponivel = getDisponivel();
    return (total - disponivel);
}

public Double getPorcentagemEmUso() {
        double total = getTotal();
        double emUso = getEmUso();
        return (emUso / total) * 100.0;
    }
    
public String converter(){
      return String.format("%.2f", getPorcentagemEmUso());
  }

public void avisoMemoria(String idTotem, String login){
      json.put("text", "O uso da memória do totem " + idTotem + " ultrapassou o ponto de crítico definido.\n" +
    "A memória ram atual é de " + converter() + "% se o uso continuar em crítico por 2 minutos o totem irá reiniciar automaticamente." +
    "\nAtenciosamente, Infinity Solutions");
      json2.put("text", "O uso da memória do totem " + idTotem + " ultrapassou o ponto de atenção definido.\n" +
    "A memória ram atual é de " + converter() +
    "%\nAtenciosamente, Infinity Solutions");
      
      Double valorAtencao = ver.limiteAtencaoMemoria(idTotem, login);
      Double valorCritico = ver.limiteCriticoMemoria(idTotem, login); 
      if(getPorcentagemEmUso() > valorCritico){
          try {
              SlackIntegration.sendMessage(json);
          } catch (IOException ex) {
              Logger.getLogger(Memoria.class.getName()).log(Level.SEVERE, null, ex);
          } catch (InterruptedException ex) {
              Logger.getLogger(Memoria.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      else if(getPorcentagemEmUso() > valorAtencao){
          try {
              SlackIntegration.sendMessage(json2);
          } catch (IOException ex) {
              Logger.getLogger(Memoria.class.getName()).log(Level.SEVERE, null, ex);
          } catch (InterruptedException ex) {
              Logger.getLogger(Memoria.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
    /**
     * Converte um valor em bytes para gigabytes.
     *
     * @param bytes Valor em bytes a ser convertido.
     * @return Valor convertido para gigabytes.
     */
    private Double convertBytesToGigabytes(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }

    /**
     * Retorna uma <code>String</code> com todas as informações relacionadas a <b>Memória</b>.
     *
     * @return <code>String</code> com todas as informações relacionadas a <b>Memória</b>.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Memoria").append("\n");

        sb.append("Em uso: ")
                .append(String.format("%.2f", getPorcentagemEmUso()))
                .append("\n");

        sb.append("Disponível: ")
                .append(String.format("%.2f", getDisponivel()))
                .append(" GB\n");

        sb.append("Total: ")
                .append(String.format("%.2f", getTotal()))
                .append(" GB\n");

        return sb.toString();
    }
}

