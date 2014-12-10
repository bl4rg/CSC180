package labs.six;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sean on 12/9/2014.
 */
public class GUI {
    private JFrame frame = new JFrame();
    private JPanel controls = new JPanel();
    private JPanel content = new JPanel();
    private JPanel auction = new JPanel();
    private JPanel loginPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();

    public GUI() {}

    public void init() {
        JTextField searchField = new JTextField(50);
        JButton searchButton = new JButton("search");
        controls.setLayout(new FlowLayout());
        controls.add(searchField);
        controls.add(searchButton);

        auction.setLayout(new BorderLayout());
        auction.add(controls, BorderLayout.NORTH);
        auction.add(content, BorderLayout.SOUTH);

        JPanel contents = new JPanel();
        JTextField field = new JTextField(25);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if(field.getText() == null || field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "field is empty");
            }else {
                hideLogin();
            }
        });
        contents.add(field);
        contents.add(loginButton);
        loginPanel.setLayout(new BorderLayout());
        loginPanel.add(field, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH);

        frame.setLayout(cardLayout);
        frame.add(loginPanel, "login");
        frame.add(auction, "main");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(new Dimension(800, 600));
        frame.setVisible(true);
        cardLayout.first(loginPanel);
    }

    public void hideLogin() {
        cardLayout.next(auction);
    }

    public void addToControls(JComponent component) {
        controls.add(component);
        frame.repaint();
    }

    public void addToContent(JComponent component) {
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(component);
        frame.repaint();
    }
}
