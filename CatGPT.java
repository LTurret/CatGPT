import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.Random;

public class CatGPT extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JButton sendButton;
    private JTextArea inputTextArea;
    private JTextPane chatHistoryTextPane;

    public CatGPT() {
        frame = new JFrame();
        panel = new JPanel(new GridBagLayout());
        inputTextArea = new JTextArea(3, 20);
        sendButton = new JButton("Send message");
        chatHistoryTextPane = new JTextPane();
        chatHistoryTextPane.setEditable(false);

        sendButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased padding for the panel to the frame

        // Place the chat history at the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Adjusted to 3 to accommodate the toggle button
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(new JScrollPane(chatHistoryTextPane), gbc);

        // Place the input text area below the chat history
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        panel.add(new JScrollPane(inputTextArea), gbc);

        // Place the send button next to the input text area
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.ipadx = 20; // Increase internal padding for the send button
        gbc.ipady = 10; // Increase internal padding for the send button
        panel.add(sendButton, gbc);

        // Place the toggle button below the input text area and send button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CatGPT (Insider Preview)");
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        final int globalSize = 15;
        UIManager.put("Label.font", new Font(Font.DIALOG, Font.PLAIN, globalSize));
        UIManager.put("TextPane.font", new Font(Font.DIALOG, Font.PLAIN, globalSize));
        new CatGPT();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        sendButton.setEnabled(false);
        inputTextArea.setEnabled(false);
        String text = inputTextArea.getText();
        if (!text.trim().isEmpty()) {
            appendToChat("You", text);
            inputTextArea.setText("LLM is processing your prompt...");
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String[] catMessages = {
                            "Meow",
                            "Purr",
                            "I'm just a cat, I don't know what you're talking about.",
                            "Meow Meow Meow Meow.",
                            "Meeeeeeowwww",
                            "Purrrrrrrr",
                            "Feed me hooman!",
                            "I love boxes.",
                            "Can I sit on your keyboard?",
                            "I am the ruler of this household.",
                            "Wake me up when it's dinner time.",
                            "Play with me!",
                            "Pet me or suffer the consequences.",
                            "I'm plotting something mischievous...",
                            "Chasing my tail again!",
                            "Bird watching mode activated.",
                            "Let's nap together.",
                            "Meowdeling is my passion.",
                            "Just loafing around.",
                    };
                    int randomIndex = new Random().nextInt(catMessages.length);
                    String randomMessage = catMessages[randomIndex];
                    appendToChat("CatGPT", randomMessage);
                    sendButton.setEnabled(true);
                    inputTextArea.setText("");
                    inputTextArea.setEnabled(true);
                    inputTextArea.requestFocusInWindow();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            sendButton.setEnabled(true);
            inputTextArea.setEnabled(true);
        }
    }

    private void appendToChat(String nickname, String text) {
        StyledDocument doc = chatHistoryTextPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setBold(style, true);
        try {
            String emoji = "";
            if (nickname.equals("CatGPT")) {
                emoji = "\uD83D\uDC31";
            } else {
                emoji = "\u200D\uD83D\uDCBB";
            }

            doc.insertString(doc.getLength(), emoji + " " + nickname + "\n", style);

            if (nickname.equals("CatGPT")) {
                String newText = text;
                new Timer(25, new ActionListener() {
                    int i = 0;

                    public void actionPerformed(ActionEvent e) {
                        try {
                            doc.insertString(doc.getLength(), String.valueOf(newText.charAt(i)), null);
                            i++;
                            if (i >= newText.length()) {
                                ((Timer) e.getSource()).stop();
                                doc.insertString(doc.getLength(), "\n\n", null);
                            }
                        } catch (BadLocationException exception) {
                            exception.printStackTrace();
                        }
                    }
                }).start();
            } else {
                doc.insertString(doc.getLength(), text + "\n\n", null);
            }
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
    }
}
