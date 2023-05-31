package com.mycompany.exemplobanco;

public class Limites {
    private Integer idLimites;
    private Double memoriaRamLimite;
    private Double cpuLimite;
    private String fkTotem;
    private String fkEmpresa;
    private String tipoDeLimite;

    
    public Integer getIdLimites() {
        return idLimites;
    }

    public void setIdLimites(Integer idLimites) {
        this.idLimites = idLimites;
    }

    public Double getMemoriaRamLimite() {
        return memoriaRamLimite;
    }

    public void setMemoriaRamLimite(Double memoriaRamLimite) {
        this.memoriaRamLimite = memoriaRamLimite;
    }

    public Double getCpuLimite() {
        return cpuLimite;
    }

    public void setCpuLimite(Double cpuLimite) {
        this.cpuLimite = cpuLimite;
    }

    public String getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(String fkTotem) {
        this.fkTotem = fkTotem;
    }

    public String getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(String fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public String getTipoDeLimite() {
        return tipoDeLimite;
    }

    public void setTipoDeLimite(String tipoDeLimite) {
        this.tipoDeLimite = tipoDeLimite;
    }

    @Override
    public String toString() {
        return "Limites{" + "idLimites=" + idLimites + ", memoriaRamLimite=" + memoriaRamLimite + ", cpuLimite=" + cpuLimite + ", fkTotem=" + fkTotem + ", fkEmpresa=" + fkEmpresa + ", tipoDeLimite=" + tipoDeLimite + '}';
    }
       
}
