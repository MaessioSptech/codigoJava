/*
 * The MIT License
 *
 * Copyright 2023 dssilva21.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.jar.infinity.solutions;

import com.github.britooo.looca.api.teste.TesteApi;
import com.mycompany.exemplobanco.ConexaoMysql;
import com.mycompany.exemplobanco.VerificacaoCredenciais;
import com.mycompany.exemplobanco.VerificarLimites;
import java.awt.GraphicsEnvironment;
import java.util.Scanner;
import log.Logger;

/**
 *
 * @author dssilva21
 */
public class Inicio {
    public static void main(String[] args) {
            if (GraphicsEnvironment.isHeadless()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Informe seu CNPJ");
            String login = scanner.nextLine();
            
            System.out.println("Informe sua senha");
            String senha = scanner.nextLine();
            
            System.out.println("Informe o numero de Identificacao do totem");
            String numeroDeIdentificacaoDoTotem = scanner.nextLine(); // Preencha com o valor adequado

            VerificacaoCredenciais verCredencial = new VerificacaoCredenciais();
            Boolean validCredencial = verCredencial.verCre(login, senha, numeroDeIdentificacaoDoTotem);
            Boolean validTotem = verCredencial.verCreTotem(numeroDeIdentificacaoDoTotem, login);

            System.out.println("Autenticando. Aguarde...");

            if (validCredencial) {
                System.out.println("Iniciando dados");
                System.out.println("Usuário autenticado. Aguarde...");
                if (validTotem) {
                    System.out.println("autenticou");
                                   Logger log = new Logger();
                    log.main(login, senha, numeroDeIdentificacaoDoTotem);
                    TesteApi.main(login, senha, numeroDeIdentificacaoDoTotem);
                } else {
                    System.out.println("Totem informado inválido. Informe um Totem válido.");
                }
            } else {
                System.out.println("Erro de autenticação!");
                System.out.println("CNPJ ou Senha inválidos.");
            }
        } 
        else {
                System.out.println("Possui ambiente grafico");
            TelaDeLogin tela = new TelaDeLogin();
            tela.main(args);
        }
    }


}
