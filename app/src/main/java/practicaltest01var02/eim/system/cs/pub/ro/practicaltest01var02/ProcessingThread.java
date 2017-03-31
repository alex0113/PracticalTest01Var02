package practicaltest01var02.eim.system.cs.pub.ro.practicaltest01var02;

/**
 * Created by student on 31.03.2017.
 */


        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;

        import java.util.Date;
        import java.util.Random;


public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private double suma;
    private double diferenta;
    private int count = 0;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        suma = (firstNumber + secondNumber) ;
        diferenta = firstNumber - secondNumber;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
            count += 1;
            if (count == 2)
                isRunning = false;
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + suma + " " + diferenta);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
