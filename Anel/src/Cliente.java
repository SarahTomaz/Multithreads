public class Cliente {

    // Dentro do método main, dois arrays são criados: hosts e portas. O array hosts
    // contém os endereços IP dos servidores aos quais o cliente tentará se
    // conectar. O array portas contém as portas nas quais os servidores estão
    // ouvindo.
    public static void main(String[] args) {
        String[] hosts = { "localhost", "192.168.0.2", "10.0.0.2", "172.16.0.2" };
        int[] portas = { 5000, 5001, 5002, 5003 };
        new ImplCliente(hosts, portas);
    }
}