public class App {
    static int cont = 0;

    // Cinco threads (t0, t1, t2, t3, t4) são criadas usando o mesmo objeto
    // ThreadAR. Isso significa que todas as cinco threads compartilharão o mesmo
    // objeto ThreadAR e, portanto, o mesmo estado.
    public static void main(String[] args) {
        ThreadAR threadAR = new ThreadAR();
        Thread t0 = new Thread(threadAR);
        Thread t1 = new Thread(threadAR);
        Thread t2 = new Thread(threadAR);
        Thread t3 = new Thread(threadAR);
        Thread t4 = new Thread(threadAR);
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}