import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;

public class Window extends JFrame {
    JButton b3 = new JButton("CLOSE");

    b3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent  e;
        e)
        {
            System.exit(0);
        }
    };
    ButtonGroupManager buttonGroupManager;
    private String iconPath = "image\\stonksViewerIcon.png";
    Color themeColor = new Color(76, 64, 66);
    int cornerRadius = 10;
    public static int yWindowMoveThreshold = 25;
    private Window instance;
    int mousePressX = 0, mousePressY = 0;
    public Window(String name) {
        buttonGroupManager = new ButtonGroupManager();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setTitle(name);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(iconPath)));
        setStyling(this.getContentPane());
        this.setOpacity(0.90f);
        instance = this;
        initDecorations();
    }
    public void initDecorations() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                mousePressX = me.getX();
                mousePressY = me.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                if (!(mousePressY <= yWindowMoveThreshold)) return;
                instance.setLocation(instance.getLocation().x + (me.getX() - mousePressX), instance.getLocation().y + (me.getY() - mousePressY));
            }
        });

    }
    public void addTextBox(Bound bound) {
        addTextBox("", bound);
    }
    public void setBackground(Component component) {
        component.setBackground(themeColor);
    }
    public boolean shouldFocus(Component component) {
        return (component instanceof JTextField);
    }
    public void setStyling(Component component) {
        setBackground(component);
        component.setForeground(Color.WHITE);
        component.setFont(Font.getFont(Font.MONOSPACED));
        component.setFocusable(shouldFocus(component));
    }
    public void addTextBox(String text, Bound bound) {
        JTextField box = new JTextField(text);
        box.setBounds(bound.toRect());
        setStyling(box);
        this.add(box);
    }
    public void addButton(String text, Bound bound) {
        JButton button = new JButton(text);
        button.setBounds(bound.toRect());
        setStyling(button);
        this.add(button);
    }
    public void init() {
        this.setLayout(null);
        this.setVisible(true);
        this.setShape(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight(), cornerRadius, cornerRadius));
    }
    public ButtonGroupID addRadio(String optionName, Bound bound, ButtonGroupID group) {
        JRadioButton radio = new JRadioButton(optionName);
        radio.setBounds(bound.toRect());
        setStyling(radio);
        group.add(radio);
        this.add(radio);
        return group;
    }

    /**
     *
     * @param optionName
     * Name that displays next to the radio option
     * @param bound
     * Self-explanatory check Bound class for more details
     * @param id
     * Group identifier check ButtonGroupManager and ButtonGroupID for more details
     * @return return the ButtonGroupID to add multiple radios to the same group easily.
     * Can also be found using an instance of the radio through ButtonGroupManager.getGroup(AbstractButton)
     *
     * May return null if the specified id is invalid
     */
    public ButtonGroupID addRadio(String optionName, Bound bound, int id) {
        ButtonGroupID group = getButtonGroupManager().getGroup(id);
        if (group == null) return null;
        return addRadio(optionName, bound, group);
    }
    public ButtonGroupID addRadio(String optionName, Bound bound) {
        ButtonGroupID group = buttonGroupManager.addGroup();
        return addRadio(optionName, bound, group);
    }
    public ButtonGroupManager getButtonGroupManager() {
        return this.buttonGroupManager;
    }
    public void addLabel(String text, Bound bound) {
        JLabel label = new JLabel(text);
        label.setBounds(bound.toRect());
        setStyling(label);
        this.add(label);
    }
    public void addLabelCentered(String text, Bound bound, int size) {
        JLabel label = new JLabel(text);
        setStyling(label);
        label.setFont(new Font("Serif", Font.BOLD, size));
        label.setBounds(bound.getX() - label.getPreferredSize().width / 2, bound.getY() - label.getPreferredSize().height / 2, label.getPreferredSize().width, label.getPreferredSize().height);

        this.add(label);
    }
}
