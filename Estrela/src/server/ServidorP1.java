package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServidorP1 {
    ServerSocket socketServidor;
    Socket cliente;
    int porta;
    private static HashMap<String, Socket> clientsMap;

    public ServidorP1(int porta) {
        this.porta = porta;
        this.rodar();
    }

    private void rodar() {
        /*
         * Cria um socket na porta 54322
         */
        try {
            // Imprime o endereço e o nome do host do servidor.
            socketServidor = new ServerSocket(porta);
            System.out.println("Servidor rodando na porta " +
                    socketServidor.getLocalPort());
            System.out.println("HostAddress = " +
                    InetAddress.getLocalHost().getHostAddress());
            System.out.println("HostName = " +
                    InetAddress.getLocalHost().getHostName());

            // Entra em um loop infinito no qual aceita conexões de clientes, cria uma nova
            // instância de ImplServidor para cada conexão, cria uma nova thread para cada
            // instância de ImplServidor e inicia a thread.
            System.out.println("Aguardando conexão do cliente...");
            while (true) {
                cliente = socketServidor.accept();
                // Cria uma thread do servidor para tratar a conexão
                ImplServidor servidor = new ImplServidor(cliente);
                Thread t = new Thread(servidor);

                // Inicia a thread para o cliente conectado
                ImplServidor.cont++;
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cliente != null) {
                    cliente.close();
                }
                if (socketServidor != null) {
                    socketServidor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // O método broadcast envia uma mensagem para todos os clientes conectados ao
    // servidor.
    public static synchronized void broadcast(String message) {
        for (Socket socket : clientsMap.values()) {
            try {
                PrintStream output = new PrintStream(socket.getOutputStream());
                output.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // O método unicast envia uma mensagem para um cliente específico.
    public static synchronized void unicast(String message, String receiverID, String senderID) {
        Socket receiver = clientsMap.get(receiverID);
        if (receiver != null) {
            try {
                PrintStream output = new PrintStream(receiver.getOutputStream());
                output.println("UNICAST:" + senderID + ":" + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // O método addClient adiciona um cliente ao HashMap clientsMap.

    public static void main(String[] args) throws Exception {
        new ServidorP1(54322);
    }
}
