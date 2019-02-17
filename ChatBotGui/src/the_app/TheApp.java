/**
 * There is essentially nothing in this file for students to edit,
 * especially if you are creating single window apps
 * (which is what you probably should be doing).
 */

package the_app;

import frontend_viewcontroller.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sets up the model-view-controller classes of TheApp.
 * 
 * @author cheng
 */
public class TheApp implements Runnable {

    @Override
    public void run() {
        try {
            BackendModelSetup theBackendModel = new BackendModelSetup();
            MainViewDisplay theMainViewDisplay = new MainViewDisplay(theBackendModel);
            ModelsAndViewsController theMainViewsController = new ModelsAndViewsController(theBackendModel, theMainViewDisplay);
            
            theMainViewDisplay.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(TheApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}