import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//A classe Servidor é definida com constantes para o número de processos, a porta do servidor e os nomes dos processos.
public class Servidor {
    private static final int NUM_PROCESSOS = 10;
    private static final int PORTA = 5000;
    private static final String[] PROCESSOS = { "Processo 1", "Processo 2", "Processo 3", "Processo 4", "Processo 5",
            "Processo 6", "Processo 7", "Processo 8", "Processo 9", "Processo 10" };

    public static void main(String[] args) {
        try {
            // Dentro do método main, um ServerSocket é criado na porta especificada e uma
            // mensagem é impressa no console indicando que o servidor está pronto para
            // receber conexões na porta 5000.
            ServerSocket socketServidor = new ServerSocket(PORTA);
            System.out.println("=== Iniciando servidor TCP ===\n");
            System.out.println("Servidor pronto para receber conexões... pela porta " + PORTA + "\n");

            // Um array de Thread é criado para armazenar as conexões dos clientes.
            Thread[] threads = new Thread[NUM_PROCESSOS];

            // O servidor entra em um loop onde aceita conexões de clientes, imprime uma
            // mensagem indicando que uma conexão foi estabelecida, cria uma nova instância
            // da classe interna Processo para lidar com a conexão e inicia uma nova thread
            // com essa instância.
            for (int i = 0; i < NUM_PROCESSOS; i++) {
                Socket socket = socketServidor.accept();
                System.out.println("Conexão estabelecida com " + PROCESSOS[i]);
                threads[i] = new Thread(new Processo(socket, PROCESSOS[i]));
                threads[i].start();
            }

            // Depois que todas as conexões foram aceitas, o servidor espera que todas as
            // threads terminem.
            for (Thread t : threads) {
                t.join();
            }

            // Finalmente, o ServerSocket é fechado e uma mensagem é impressa no console
            // indicando que o servidor foi finalizado.
            socketServidor.close();
            System.out.println("Servidor finalizado.");
            // Se ocorrer uma exceção IOException ou InterruptedException durante a execução
            // do método main, a exceção é capturada e uma mensagem de erro é impressa.
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // A classe interna Processo implementa a interface Runnable e é usada para
    // lidar com as conexões dos clientes em threads separadas. Ela tem dois campos
    // privados: socket e nomeProcesso.
    private static class Processo implements Runnable {
        private Socket socket;
        private String nomeProcesso;

        public Processo(Socket socket, String nomeProcesso) {
            this.socket = socket;
            this.nomeProcesso = nomeProcesso;
        }

        @Override
        public void run() {
            try {
                // A mensagem é lida do DataInputStream e impressa no console.
                DataInputStream fluxoEntrada = new DataInputStream(socket.getInputStream());
                // Uma mensagem de confirmação é escrita no DataOutputStream.
                DataOutputStream fluxoSaida = new DataOutputStream(socket.getOutputStream());

                // A mensagem é lida do DataInputStream e impressa no console.
                String msg = fluxoEntrada.readUTF();
                System.out.println(nomeProcesso + " enviou a mensagem: " + msg);

                // Uma mensagem de confirmação é escrita no DataOutputStream.
                fluxoSaida.writeUTF("Mensagem recebida pelo servidor.");

                // Fecha os canais in e out do socket que estão atendendo ao processo
                fluxoEntrada.close();
                fluxoSaida.close();

                // Os fluxos de entrada e saída e o socket são fechados.
                socket.close();
                System.out.println(nomeProcesso + " finalizou a conexão.");

                // Se ocorrer uma exceção IOException durante a execução do método run, a
                // exceção é capturada e uma mensagem de erro é impressa.
            } catch (IOException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}
