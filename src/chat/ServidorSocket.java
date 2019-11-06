/*
 * Responsavel por iniciar o servidor e criar um socket de comunicação
 */
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author nimb
 */
public class ServidorSocket {

    public static void main(String[] args) {

        ServerSocket servidor = null;
        int porta = 2424;

        try {
            System.out.println("Iniciando servidor");
            servidor = new ServerSocket(porta);
            System.out.println("Servidor iniciado...");

            while (true) {
                // abre um socket
                Socket cliente = servidor.accept();
                // abre o gerenciador de clientes
                new GerenciadorClienteServidor(cliente);
            }

        } catch (IOException ex) {
            System.err.println("Não foi possivel iniciar o servidor ou o servidor está ocupado");
            try {
                if (servidor != null) {
                    servidor.close();
                }
            } catch (IOException e) {
                System.err.println("Bugou geral!");
            }
        }
    }
}