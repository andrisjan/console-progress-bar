import java.text.DecimalFormat;

public class Main {
    public static volatile boolean CHECK_STATUS;
    public static volatile int CURRENT_ADDRESS;
    private static int MAX_ADDRESS = 10000;
    private Thread progressThread = new Thread(new ProgressCheck());

    public Main(){
        progressThread.start();
        CURRENT_ADDRESS = 0;
    }

    public static void main(String[] args) {
        Main m = new Main();
        try {
            CURRENT_ADDRESS = 0;
            CHECK_STATUS = true;
            for (int i = 0; i < MAX_ADDRESS; ++i) {
                Thread.sleep(1);
                ++CURRENT_ADDRESS;
            }
            Thread.sleep(1000); // sleeps lielāks par progressa bara apdeitošanas frekvenci lai progrss-bars varētu apdeitot pēdējo vērtību
            System.out.println("");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private class ProgressCheck implements Runnable{

        public void run(){
            while (true){
                try {
                    Thread.sleep(500);
                    if (CHECK_STATUS) {
                        if (CURRENT_ADDRESS == MAX_ADDRESS){
                            CHECK_STATUS = false;
                        }
                        double fract = CURRENT_ADDRESS / Double.parseDouble(String.valueOf(MAX_ADDRESS));
                        final int width = 70;
                        System.out.print("\r[");
                        int i = 0;
                        for (; i <= (int) (fract * width); i++) {
                            System.out.print("=");
                        }
                        for (; i < width; i++) {
                            System.out.print(" ");
                        }
                        DecimalFormat df = new DecimalFormat("#");
                        System.out.print(" " + CURRENT_ADDRESS + "/" + MAX_ADDRESS + " (" + df.format(fract * 100) + "%) ]");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
