package com.mycompany.exemplobanco;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class VerificarLimites {
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    
    public Double limiteAtencaoMemoria(String fkTotem, String fkEmpresa){ 
        Limites limitesAviso = con.queryForObject("select MemoriaRamLimite from Limites where idLimites = 'atencao' and fkTotem = ? and fkEmpresa = ?"
                , new BeanPropertyRowMapper<>(Limites.class), fkTotem, fkEmpresa);
        return limitesAviso.getMemoriaRamLimite();
    }
    
    public Double limiteCriticoMemoria(String fkTotem, String fkEmpresa){ 
        Limites limitesAviso = con.queryForObject("select MemoriaRamLimite from Limites where idLimites = 'critico' and fkTotem = ? and fkEmpresa = ?"
                , new BeanPropertyRowMapper<>(Limites.class), fkTotem, fkEmpresa);
        return limitesAviso.getMemoriaRamLimite();
    }
    
    public Double limiteAtencaoCpu(String fkTotem, String fkEmpresa){ 
        Limites limitesAviso = con.queryForObject("select CPULimite from Limites where idLimites = 'atencao' and fkTotem = ? and fkEmpresa = ?"
                , new BeanPropertyRowMapper<>(Limites.class), fkTotem, fkEmpresa);
        return limitesAviso.getCpuLimite();
    }
    
    public Double limiteCriticoCpu(String fkTotem, String fkEmpresa){ 
        Limites limitesAviso = con.queryForObject("select CPULimite from Limites where idLimites = 'critico' and fkTotem = ? and fkEmpresa = ?"
                , new BeanPropertyRowMapper<>(Limites.class), fkTotem, fkEmpresa);
        return limitesAviso.getCpuLimite();
    }
}