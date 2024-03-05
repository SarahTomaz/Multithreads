public class ThreadAR  implements Runnable{
    
    @Override
    public void run() {
        int threadCont;
        synchronized (this) {
            App.cont++;
            threadCont = App.cont * 2;
        }
        double pot = threadCont * 50;
        double raiz = Math.sqrt(pot);
        System.out.println(raiz);
    }
}
