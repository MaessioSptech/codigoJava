package com.mycompany.exemplobanco;
/**
 *
 * classe responsavel por fazer a conexão com o banco de dados
 */
public class Empresa {
    private String idEmpresa;
    private String cnpj;

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }    
}
