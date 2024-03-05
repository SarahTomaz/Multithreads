import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ImplCliente {
    private Scanner sc;

    public ImplCliente(String[] hosts, int[] portas) {
        rodar(hosts, portas);
    }

    // O método rodar(String[] hosts, int[] portas) é definido como privado.
    // Ele tenta estabelecer uma conexão com cada servidor especificado nos arrays
    // de hosts e portas.
    private void rodar(String[] hosts, int[] portas) {
        try {
            for (int i = 0; i < hosts.length; i++) {
                // Cria um novo Socket para se conectar ao servidor no host e porta
                // especificados.
                Socket socketCliente = new Socket(hosts[i], portas[i]);
                // Cria um DataInputStream para receber dados do servidor. Este é um canal de
                // entrada.
                DataInputStream fluxoEntrada = new DataInputStream(socketCliente.getInputStream());
                // Cria um DataOutputStream para enviar dados para o servidor. Este é um canal
                // de saída.
                DataOutputStream fluxoSaida = new DataOutputStream(socketCliente.getOutputStream());
                sc = new Scanner(System.in);

                // Solicita ao usuário que insira uma mensagem, que é então enviada para o
                // servidor através do canal de saída.
                System.out.println("Digite uma mensagem: ");
                String msg = sc.nextLine();
                System.out.println("\n<-- Mensagem enviada ao servidor " + i + ": " + msg);
                fluxoSaida.writeUTF(msg);
                msg = fluxoEntrada.readUTF();
                // Recebe uma mensagem do servidor através do canal de entrada e imprime essa
                // mensagem.
                System.out.println("\n--> Mensagem recebida do servidor " + i + ": " + msg + "\n");
                // Fecha os canais de entrada e saída e o socket.
                fluxoEntrada.close();
                fluxoSaida.close();
                socketCliente.close();
            }
            // Se ocorrer uma exceção IOException durante a execução do método rodar, a
            // exceção é capturada e uma mensagem de erro é impressa.
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
