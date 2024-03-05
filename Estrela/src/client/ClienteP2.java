package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteP2 {
    public static void main(String[] args) {
        try {
            // cria um novo socket de conexão com o servidor na porta 54322
            Socket socket = new Socket("localhost", 54322);

            // cria uma nova instância da classe ImplCliente
            ImplCliente cliente = new ImplCliente(socket, "Cliente P2");

            // cria uma nova thread para executar a instância do cliente
            Thread threadCliente = new Thread(cliente);
            threadCliente.start();

            // lê mensagens digitadas pelo usuário e envia para o servidor
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String mensagem = scanner.nextLine();
                cliente.enviarMensagem(mensagem);
            }

            // finaliza a conexão com o servidor
            cliente.desconectar();
            scanner.close();
        } catch (IOException e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }
}