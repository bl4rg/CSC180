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
    private JPanel mainPanel = new JPanel();
    private JPanel loginPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();

    public GUI() {}

    public void init() {
        JTextField searchField = new JTextField(50);
        JButton searchButton = new JButton("search");
        controls.setLayout(new FlowLayout());
        controls.add(searchField);
        controls.add(searchButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(controls, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.SOUTH);

        JPanel contents = new JPanel();
        JTextField field = new JTextField(25);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(field.getText() == null || field.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "field is empty");
                }else {
                    hideLogin();
                }
            }
        });
        contents.add(field);
        contents.add(loginButton);
        loginPanel.setLayout(new BorderLayout());
        loginPanel.add(field, BorderLayout.NORTH);
        loginPanel.add(loginButton, BorderLayout.SOUTH);

        frame.setLayout(cardLayout);
        frame.add(loginPanel, "login");
        frame.add(mainPanel, "main");
        cardLayout.first(frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public void hideLogin() {
        cardLayout.next(frame);
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
