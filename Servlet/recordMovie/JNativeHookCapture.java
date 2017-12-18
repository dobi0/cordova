package RecordMovie;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author javaQuery
 * Global Keyboard Listener
 */


public class JNativeHookCapture implements NativeKeyListener{

	private SampleTask2 sampletask2 = null;
	private long beforeTime = 10;
	private long currentTime = 0;

	public JNativeHookCapture(){
		    this.mkdir();
	}

	private static final long serialVersionUID = 1L;

	public void mkdir(){
		String folder_name = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(Calendar.getInstance().getTime());
		this.sampletask2 = new SampleTask2(folder_name);
		this.sampletask2.trun();
		this.sampletask2.run();
	}

    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        /* Terminate program when one press ESCAPE */
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {

	        	this.sampletask2.run();
	        	try {
					this.sampletask2.fileClose();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
	            try {
					GlobalScreen.unregisterNativeHook();
				} catch (NativeHookException e1) {

					e1.printStackTrace();
				}
	            System.exit(77) ;
	    }

        if (e.getKeyCode() == NativeKeyEvent.VC_ENTER || e.getKeyCode() == NativeKeyEvent.VC_UP || e.getKeyCode() == NativeKeyEvent.VC_DOWN || e.getKeyCode() == NativeKeyEvent.VC_RIGHT || e.getKeyCode() == NativeKeyEvent.VC_LEFT) {


	        	 long currentTime = System.currentTimeMillis();
	        	 this.currentTime = currentTime;
	        	 if(this.currentTime - this.beforeTime > 3 * 1000){
	        		 this.sampletask2.run();
	        	 }

	        	 System.out.println("currentTime" + currentTime + "秒beforeTime" + beforeTime);
	        	 this.beforeTime = currentTime;
	    }
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public SampleTask2 getSampleTask2(){
    		return this.sampletask2;
    }

    public  void close() {
	    	this.sampletask2.run();
	    	try {
				this.sampletask2.fileClose();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	        try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
	        System.exit(77) ;
    }


    public static void main(String[] args) {
        try {
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            /* Its error */
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        /* Construct the example object and initialze native hook. */
        GlobalScreen.addNativeKeyListener(new JNativeHookCapture());
    }
}