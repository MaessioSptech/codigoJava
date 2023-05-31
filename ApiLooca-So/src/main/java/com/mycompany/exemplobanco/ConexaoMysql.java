
package com.mycompany.exemplobanco;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * classe responsavel por fazer a conexão com o banco de dados no mysql
 */
public class ConexaoMysql {
    private JdbcTemplate conexaoDoBancoMysql;
     public ConexaoMysql() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/SistemaOperacional");
        dataSource.setUsername("root");
        dataSource.setPassword("urubu100");
        
        this.conexaoDoBancoMysql = new JdbcTemplate(dataSource);
    }
/**
 * Retorna a conexão com o banco de dados.
 *
 * @return A conexão com o banco de dados.
 */
    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBancoMysql;
    } 
}
