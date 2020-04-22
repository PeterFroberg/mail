import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class Mail extends JFrame {
    /**
     * Create GUI components for the application
     */
    private JTextArea textArea = new JTextArea("");
    private JTextField textFieldServer = new JTextField();
    private JTextField textFieldUSername = new JTextField();
    private JTextField textFieldPassword = new JPasswordField();
    private JTextField textFieldFrom = new JTextField();
    private JTextField textFieldTo = new JTextField();
    private JTextField textFieldSubject = new JTextField();
    private JButton sendButton = new JButton();

    public Mail() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /**
         * Setting up GUI space, by adding GUI components
         */
        JPanel jPanelNorth = new JPanel();
        jPanelNorth.setLayout(new GridLayout(7, 2));
        jPanelNorth.add(new JLabel("Server:"));
        jPanelNorth.add(this.textFieldServer);
        jPanelNorth.add(new JLabel("Username:"));
        jPanelNorth.add(this.textFieldUSername);
        jPanelNorth.add(new JLabel("Password:"));
        jPanelNorth.add(this.textFieldPassword);
        jPanelNorth.add(new JLabel("From:"));
        jPanelNorth.add(this.textFieldFrom);
        jPanelNorth.add(new JLabel("To:"));
        jPanelNorth.add(this.textFieldTo);
        jPanelNorth.add(new JLabel("Subject:"));
        jPanelNorth.add(this.textFieldSubject);
        jPanelNorth.add(new JLabel(""));
        JPanel jPanelSouth = new JPanel();
        jPanelSouth.add(this.sendButton);

        /**
         * creates a ActionListener for the submit button by using lambda expression
         * that use function submitButtonClicked()
         */
        sendButton.addActionListener(e -> {
            sendButtonClicked();
        });
        getContentPane().add("North", jPanelNorth);
        getContentPane().add("Center", new JScrollPane(this.textArea));
        getContentPane().add("South", jPanelSouth);
        setSize(640, 480);
        setVisible(true);

    }

    /**
     * Function to run when actionlistener is activated by the button pressed
     * The function starts a connection to the database and then tries to
     * insert a record in the database by suing the function insertDbPost with
     * information from the textfileds in the gui
     * and finnaly gets new comments from the database
     */
    private void sendButtonClicked() {
        Properties properties = System.getProperties();
        // Send mail
        SendEmail sendemail = new SendEmail(textFieldServer.getText(), textFieldUSername.getText(), textFieldPassword.getText());
        String result = sendemail.send(textFieldFrom.getText(),textFieldTo.getText(),textFieldSubject.getText(), textArea.getText());


        JOptionPane.showMessageDialog(this, result);
        //System.out.println(result);


    }

    public static void main(String[] args){
        new Mail();
    }
}
