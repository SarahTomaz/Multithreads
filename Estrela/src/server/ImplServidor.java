package server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ImplServidor implements Runnable {
    private HashMap<String, Socket> clientMap;
    public Socket socketCliente;
    public static int cont = 0;
    private boolean conexao = true;

    public ImplServidor(Socket cliente) {
        socketCliente = cliente;
    }

    // O método run() é chamado quando a thread é iniciada. Ele lê mensagens do
    // cliente e as processa.
    public void run() {

        // Primeiro, ele imprime o endereço e o nome do host do cliente.
        System.out.println("Conexão " +
                ImplServidor.cont +
                " com o cliente " +
                socketCliente.getInetAddress().getHostAddress() +
                "/" +

                socketCliente.getInetAddress().getHostName());
        // Cria um Scanner para ler a entrada do cliente.
        try {
            Scanner s = null;
            s = new Scanner(socketCliente.getInputStream());
            String mensagemRecebida;
            // Ele entra em um loop que continua enquanto conexao for verdadeiro. Dentro do
            // loop, ele lê uma linha da entrada do cliente e a processa.
            while (conexao) {
                mensagemRecebida = s.nextLine();
                // Se a mensagem for "fim", ele define conexao como falso, o que terminará o
                // loop
                if (mensagemRecebida.equalsIgnoreCase("fim"))
                    conexao = false;
                // Se a mensagem começar com "ID:", ele extrai o ID do cliente da mensagem e
                // adiciona o cliente ao clientMap.
                if (mensagemRecebida.startsWith("ID:")) {
                    String clientID = mensagemRecebida.substring(3);
                    clientMap.put(clientID, socketCliente);
                    // Se a mensagem começar com "BROADCAST:", ele extrai a mensagem de transmissão
                    // e chama o método broadcast da classe ServidorP1 para enviar a mensagem para
                    // todos os clientes.
                } else if (mensagemRecebida.startsWith("BROADCAST:")) {
                    String broadcastMessage = mensagemRecebida.substring(10);
                    ServidorP1.broadcast(broadcastMessage);
                } else if (mensagemRecebida.startsWith("ID:")) {
                    String clientID = mensagemRecebida.substring(3);
                    clientMap.put(clientID, socketCliente);
                }
            }
            // Finalmente, quando o loop termina, ele fecha o Scanner e imprime uma mensagem
            // indicando que o cliente terminou.
            s.close();
            System.out.println("Fim do cliente " +
                    socketCliente.getInetAddress().getHostAddress());
            socketCliente.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
