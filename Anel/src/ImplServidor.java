import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ImplServidor {
    private int porta;

    // O construtor ImplServidor(int porta) é definido. Ele inicializa a porta com o
    // valor passado e chama o método rodar().
    public ImplServidor(int porta) {
        this.porta = porta;
        rodar();
    }

    private void rodar() {
        try {
            // Cria um socket servidor na porta especificada
            ServerSocket socketServidor = new ServerSocket(this.porta);
            System.out.println("=== Iniciando servidor TCP ===\n");
            System.out.println("Servidor pronto para receber conexões...\n");

            // O servidor entra em um loop infinito onde ele chama o método accept() do
            // ServerSocket
            // para aceitar uma nova conexão de um cliente. O método accept() bloqueia até
            // que um cliente se conecte.
            while (true) {
                // Aguarda a conexão de um cliente
                Socket socket = socketServidor.accept();
                // imprime o ip do cliente
                System.out.println("Nova conexão com o cliente " +
                        socket.getInetAddress().getHostAddress());
                // O servidor cria uma nova thread para lidar com a comunicação com o cliente
                new Thread(() -> {
                    try {
                        // Cria um DataInputStream para ler dados do cliente.
                        DataInputStream fluxoEntrada = new DataInputStream(socket.getInputStream());
                        // Cria um DataOutputStream para enviar dados para o cliente.
                        DataOutputStream fluxoSaida = new DataOutputStream(socket.getOutputStream());

                        // Entra em um loop infinito onde ele lê uma mensagem do cliente, imprime a
                        // mensagem,
                        // adiciona "Resposta do servidor: " antes da mensagem e envia a mensagem de
                        // volta para o cliente.
                        while (true) {
                            String msg = fluxoEntrada.readUTF(); // Aguarda o recebimento de uma string.
                            System.out.println("--> Mensagem recebida do cliente: " + msg);
                            msg = "Resposta do servidor: " + msg;
                            System.out.println("--> Servidor enviando mensagem: " + msg);
                            fluxoSaida.writeUTF(msg); // Envia uma string.
                        }
                        // Se ocorrer uma exceção IOException durante a execução da thread, a exceção é
                        // capturada e uma mensagem de erro é impressa.
                    } catch (IOException e) {
                        System.err.println("Erro: " + e.getMessage());
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // ignore
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
