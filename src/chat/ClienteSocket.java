/*
 * NOTA: AINDA TEM PROBLEMA NA HORA DE SAIR. O sevidor disse: null
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author nimb
 */
public class ClienteSocket {

    public static void main(String[] args) {

        String ip = "127.0.0.1";
        int porta = 2424;

        try {
            final Socket cliente = new Socket(ip, porta);

            // lendo mensagem do servidor
            new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        while (true) {
                            String mensagem = leitor.readLine();
                            if(mensagem == null || mensagem.isEmpty()){
                                continue;
                            }
                            
                            System.out.println("O servidor disse: " + mensagem);
                        }
                    } catch (IOException ex) {
                        System.err.println("Impossivel ler a mensagem do servidor");
                        // ex.printStackTrace();
                    }
                }
            }.start();

            // escrevendo mensagem no servidor
            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);
            // lendo mensagem escrita no terminal
            BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));

            String mensagemTerminal = "";
            while (true) {
                mensagemTerminal = leitorTerminal.readLine();
                // Se não tiver mensagem a ser lida do terminal, prossiga
                if (mensagemTerminal == null || mensagemTerminal.length() == 0) {                
                    continue;
                }
                escritor.println(mensagemTerminal);
                if (mensagemTerminal.equalsIgnoreCase(":sair")) {
                    System.out.println("Fechando o cliente!");
                    System.exit(0);
                }
            }

        } catch (UnknownHostException ex) {
            System.err.println("O endereço passado é inválido");
            // ex.printStackTrace();
        } catch (IOException e) {
            System.err.println("O servidor pode estar fora do ar");
            // e.printStackTrace();
        }
    }
}
