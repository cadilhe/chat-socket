/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nimb
 */
public class GerenciadorClienteServidor extends Thread {

    private final Socket cliente;
    private String nomeCliente;
    private BufferedReader leitor;
    private PrintWriter escritor;
    private static final Map<String, GerenciadorClienteServidor> clientes = new HashMap<String, GerenciadorClienteServidor>();

    public GerenciadorClienteServidor(Socket cliente) {
        this.cliente = cliente;
        start();
    }

    @Override
    public void run() {
        try {
            // o leitor vai ler as mensagens do socket
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            // o escritor vai escrever as mensagens no socket. O parametro true permite enviar as msg imediatamente para leitura
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            // o socket recebe o novo cliente
            escritor.println("login:");
            // o nome é lido pelo socket e armazenado como String
            String msg = leitor.readLine();
            // o que foi digitado é o nome do novo cliente
            this.nomeCliente = msg;
            // resposta do servidor
            escritor.println("Olá " + this.nomeCliente);
            // pega o cliente e coloca na lista (mapa) pelo nome para que posa ser achado por outros
            clientes.put(this.nomeCliente, this);

            while (true) {
                msg = leitor.readLine();
                // protocolo: comando para sair:: sair    
                if (msg.equalsIgnoreCase("SAIR")) {
                    this.cliente.close();
                    System.out.println("Fechando a conexão!");
                } else if (msg.toLowerCase().startsWith(":msg")) {
                    String nomeDestinatario = msg.substring(4, msg.length());
                    System.out.println("enviando para " + nomeDestinatario);
                    GerenciadorClienteServidor destinatario = clientes.get(nomeDestinatario);
                    if(destinatario == null){
                        escritor.println("O cliente informado não existe");
                    } else{
                        escritor.println("transmitir para: " + destinatario.getNomeCliente());
                        destinatario.getEscritor().println(this.nomeCliente + " disse: " + leitor.readLine());
                    }
                } else {
                    escritor.println(this.nomeCliente + ", voce disse: " + msg);
                }
            }

        } catch (IOException ex) {
            System.err.println("O cliente fechou a conexão");
        }
    }
    
    // proteção de acesso

    public String getNomeCliente() {
        return nomeCliente;
    }
    
    
    public PrintWriter getEscritor() {
        return escritor;
    }
/*
    public BufferedReader getLeitor() {
        return leitor;
    }
*/
}