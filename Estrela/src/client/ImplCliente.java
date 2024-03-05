package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ImplCliente implements Runnable {
    private Socket cliente;
    private boolean conexao = true;
    private PrintStream saida;
    private String id;

    public ImplCliente(Socket c, String id) {
        this.cliente = c;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // cria a stream de saída para enviar mensagens ao servidor
            saida = new PrintStream(cliente.getOutputStream());

            // cria a stream de entrada para receber mensagens do servidor
            Scanner entrada = new Scanner(cliente.getInputStream());

            while (conexao) {
                // lê a mensagem enviada pelo servidor
                if (entrada.hasNextLine()) {
                    String mensagem = entrada.nextLine();
                    System.out.println(mensagem);
                }
            }

            // fecha as streams e o socket
            saida.close();
            entrada.close();
            cliente.close();
        } catch (Exception e) {
            System.out.println("Erro ao executar cliente: " + e.getMessage());
        }
    }

    public void enviarMensagem(String mensagem) {
        // envia a mensagem para o servidor
        saida.println(mensagem);
    }

    public void desconectar() {
        // finaliza a conexão com o servidor
        conexao = false;
    }
}
