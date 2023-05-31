package com.mycompany.exemplobanco;

import com.mycompany.jar.infinity.solutions.TelaDeLogin;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class VerificacaoCredenciais {
/**
 * Retorna a conexão com o banco de dados.
 *
 * @return Se existe totem.
 */
    public Boolean verCreTotem(String numeroDeIdentificacaoDoTotem, String login) {
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();
        Boolean verificarTotem = false;

        Totem totem = null;
        try {
            totem = con.queryForObject("SELECT * FROM Totem WHERE fkEmpresa = ? AND NumIdenti = ?",
                    new BeanPropertyRowMapper<>(Totem.class), login, numeroDeIdentificacaoDoTotem);

            if (totem != null) {
                System.out.println("Totem encontrado: " + numeroDeIdentificacaoDoTotem);
                verificarTotem = true;
            }
        } catch (EmptyResultDataAccessException ex) {
            System.out.printf("Totem %s não existe, informe um totem válido", numeroDeIdentificacaoDoTotem);
            
        }
        return verificarTotem;
    }

    /**
 * Retorna a conexão com o banco de dados.
 *
 * @return se existe usuario
 */
    public Boolean verCre(String login, String senha, String numeroDeIdentificacaoDoTotem) {
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        Empresa user = null;
        Boolean verificarUsuario = false;
        Boolean verificarTotem = false;
        Boolean verificacaoFinal = false;
        
        System.out.println("Login Informado: " + login);
        System.out.println("Senha Informado: " + senha);
        System.out.println("Totem Informado: " + numeroDeIdentificacaoDoTotem);
        try {
            user = con.queryForObject("select * from Funcionario  where FkEmpresa = ? and Senha = ?",
                    new BeanPropertyRowMapper<>(Empresa.class), login, senha);

            if (user != null) {
                System.out.println("Login e senha encontrados");
                verificarUsuario = true;
            }
           verificarTotem = verCreTotem(numeroDeIdentificacaoDoTotem, login);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Login ou senha invalidos");
        }
    
        return verificarUsuario;
    }
}
