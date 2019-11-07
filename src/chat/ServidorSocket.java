/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {

    public static void main(String[] args) {

        ServerSocket servidor = null;
        int porta = 2424;
        try {
            System.out.println("iniciando o servidor");
            servidor = new ServerSocket(porta);
            System.out.println("servidor iniciado");

            while (true) {
                Socket cliente = servidor.accept();
                new GerenciadorDeClientes(cliente);
            }

        } catch (IOException e) {

            try {
                if (servidor != null) {
                    servidor.close();
                }
            } catch (IOException e1) {
            }

            System.err.println("a porta está ocupada ou o servidor foi fechado");
            e.printStackTrace();
        }

    }
}
