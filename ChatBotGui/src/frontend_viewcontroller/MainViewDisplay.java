package frontend_viewcontroller;

import backend_models.*;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * This class is responsible for displaying the data from the backend, and
 * directly putting data on the screen for users to see.
 *
 * Responsibilities of this class include:
 *
 * (1) Construct the graphical user interface (GUI) on the screen
 *
 * (2) Pull data from the backend to display in the GUI
 *
 * There should be no code here for handling user's keyboard or mouse
 * interaction! That belongs in the ModelsAndViewsController class.
 *
 * There should also be no code here directly about modeling the problem or
 * situation your program solves.
 *
 * All problem or situation modeling related code must go in the backend classes
 *
 * The FOUR (4) main steps to creating GUI widgets are labeled below. There are
 * many smaller steps you should get familiar with as well.
 *
 * @author cerva
 */
public class MainViewDisplay extends JFrame {

    /*
     *
     * MainViewDisplay needs to have a instance variable to reference the
     * backend's models because the frontend's MainViewDisplay is responsible
     * for displaying data from the backend.
     *
     * Since the backend models is initially set up by an instance of the
     * BackendModelSetup class, we just need this one instance variable here:
     */
    BackendModelSetup theBackendModel;

    /*
     *
     * Step 1 of 4 for creating GUI widgets: declare them
     * --------------------------------------------------
     *
     * GUI widgets to be displayed to the user on the screen is declared here
     * (but will be constructed and initialized in the initComponents method).
     * The user will see data and can later interact with these widgets.
     */
 /*
     *
     *
     */
    public MainViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    /*
     *
     * initComponents is all about fulfilling Responsibility #1 of this class:
     * (1) Construct the graphical user interface (GUI) on the screen
     */
    JTextField inputField;
    JButton send;
    JList chatLog;
    JLabel pic;

    private void initComponents() {
        this.setTitle("INAP");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, 200));
        ImageIcon img = new ImageIcon("icon.png");
        this.setIconImage(img.getImage());
        
        pic = new JLabel();
        pic.setIcon(new ImageIcon("icon.png"));
        inputField = new JTextField();
        send = new JButton("Send");
        chatLog = new JList();
        JScrollPane listScroller = new JScrollPane(chatLog);
        listScroller.setPreferredSize(new Dimension(250, 80));
        chatLog.setListData(theBackendModel.theChat.getChatLog());
        this.getRootPane().setDefaultButton(send);
        /*
         *
         * Step 2 of 4 for creating GUI widgets: construct them
         * ----------------------------------------------------
         *
         * Construct GUI widget components here, and add them into the
         * mainDisplayPane later
         */
 /*
         * Choose your LayoutManager for the mainDisplayPane here. See:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
         *
         * I suggest GridBagLayout. For more details, see:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
         */
        this.setMinimumSize(new Dimension(700, 600));
        Container mainDisplayPane = this.getContentPane();
        mainDisplayPane.setLayout(new GridBagLayout());
        this.setResizable(false);

        /*
         * you should construct a new GridBagConstraints object each time you
         * use it, in order to avoid subtle bugs...
         */
        GridBagConstraints c;


        /*
         *
         * Step 3 of 4 for creating GUI widgets: add them to the pane
         * ----------------------------------------------------------
         *
         * For each GUI widget you constructed earlier, you will now specify a
         * GridBagConstraints for it, then add the widget into the
         * mainDisplayPane
         */
        // construct a new GridBagConstraints each time you use it, to avoid subtle bugs...
        //c.fill = GridBagConstraints.BOTH; // many options! See online tutorial
        //c.ipady = 0;
        //c.weightx = 0;
        //c.weighty = 0;
        //c.anchor = GridBagConstraints.CENTER;
        //c.insets = new Insets(0, 0, 0, 0);
        /*
        open
        encrypt
        decrypt
        save
         */
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.SOUTHEAST;
        c.gridx = 2;
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 0;
//        c.ipadx = 25;
//        c.ipady = 25;
        mainDisplayPane.add(this.send, c);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.ipadx = 555;
        c.ipady = 10;
        mainDisplayPane.add(this.inputField, c);


        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 200;
        c.ipady = 300;
        c.weighty = 1;
        c.weightx = 1;
        mainDisplayPane.add( new JScrollPane(chatLog), c);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 3;
        c.gridy = 0;
        c.weighty = 1;
        c.weightx = 1;
        mainDisplayPane.add( pic, c);

        this.pack(); // leave this line last in this method.
        // must pack this JFrame before it can be displayed on screen
    }

    /*
     *
     * Step 4 of 4 for creating GUI widgets: write methods to update them
     * -------------------------------------------------------------------
     *
     * The methods below are all about fulfilling Responsibility #2 of this
     * class: (2) Pull data from the backend to display in the GUI
     *
     * Write below all the methods for displaying data into the GUI using this
     * MainViewDisplay object
     */
    void updateChatLog() {
        chatLog.setListData(theBackendModel.theChat.getChatLog());
        chatLog.ensureIndexIsVisible(chatLog.getModel().getSize() - 1);
    }

//    String showOpenDialog() {
//        JFileChooser jfc = new JFileChooser();
//        int status = jfc.showOpenDialog(this);
//        if (status == JFileChooser.APPROVE_OPTION) {
//            File theFile = jfc.getSelectedFile();
//            String thePath = theFile.getAbsolutePath();
//            return thePath;
//        }
//
//        return null;
//    }
}
