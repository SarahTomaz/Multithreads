package client;

import java.net.InetAddress;
import java.net.Socket;

public class ClienteP3 {
    Socket socket;
    InetAddress inet;
    String ip;
    int porta;

    public ClienteP3(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
        this.rodar();
    }

    private void rodar() {

        try {
            socket = new Socket(ip, porta);
            inet = socket.getInetAddress();
            System.out.println("HostAddress = " + inet.getHostAddress());
            System.out.println("HostName = " + inet.getHostName());
            // Cria um novo objeto Cliente com a conexão socket para que seja executado em
            // um novo processo. Permitindo assim a conexão de vários clientes com servidor.
            ImplCliente c = new ImplCliente(socket, ip);
            Thread t = new Thread(c);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ClienteP3("127.0.0.3", 54322);
    }
}