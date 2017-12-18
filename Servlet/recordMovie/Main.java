package RecordMovie;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

@WebServlet("/record/movie")
public class Main extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean record_start = false;
	JNativeHookCapture jnativeObj = null;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(!record_start) {
            this.record_start = true;
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
            System.out.println(this.record_start + "\n\n\n");
			JNativeHookCapture jnativeObj = new JNativeHookCapture();
			this.jnativeObj = jnativeObj;
	        GlobalScreen.addNativeKeyListener(jnativeObj);
        }
		else {
				this.jnativeObj.close();
			}
	}

}
