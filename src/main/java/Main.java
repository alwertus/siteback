public class Main {
    public static void main(String[] args) {
        // тут всякое своё

        // запуск jetty сервера
        ServerStarter serverStarter = new ServerStarter(args);
        new Thread(serverStarter).start();
    }
}
