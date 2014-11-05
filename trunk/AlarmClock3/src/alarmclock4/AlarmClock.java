package alarmclock4;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

public class AlarmClock extends JFrame {

    private JLabel timeLabel = new JLabel("12:00:00 AM");
    private JPanel alarmPanel = new JPanel();
    private JTextField hourTextField = new JTextField(5),
            minuteTextField = new JTextField(5);
    private JRadioButton amRadioButton = new JRadioButton("AM", true),
            pmRadioButton = new JRadioButton("PM", false);
    private JButton setAlarmTime = new JButton("Set");
    private ButtonGroup amPmButtonGroup = new ButtonGroup();
    private JTextField statusBar = new JTextField();

    private Time alarmTime = new Time();
    private IClock clock;
    private ActionListener alarmPlayer; 

    public AlarmClock(IClock clock, ActionListener listener) {
        this.clock = clock;
        this.alarmPlayer = listener;
        alarmPanel.add(hourTextField);
        alarmPanel.add(minuteTextField);
        alarmPanel.add(amRadioButton);
        alarmPanel.add(pmRadioButton);
        alarmPanel.add(setAlarmTime);

        amPmButtonGroup.add(amRadioButton);
        amPmButtonGroup.add(pmRadioButton);

        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 60));
        this.add(BorderLayout.NORTH, alarmPanel);
        this.add(BorderLayout.CENTER, timeLabel);
        this.add(BorderLayout.SOUTH, statusBar);

        // event handlers
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlarmTime.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int addHours = pmRadioButton.isSelected() ? 12 : 0;
                    setAlarmTime(Integer.parseInt(hourTextField.getText()) + addHours, Integer.parseInt(minuteTextField.getText()), 0);
                    //alarmTime.setHours(Integer.parseInt(hourTextField.getText()) + addHours)
                           // .setMinutes(Integer.parseInt(minuteTextField.getText()))
                           // .setSeconds(0);
                    statusBar.setText("Alarm set to " + alarmTime);
                } catch (NumberFormatException ex) {
                    statusBar.setText("H M must be integers");
                }
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            private boolean alarmOn = false;
            private boolean alarmAlreadyOn = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                Time now = clock.getTime();
                timeLabel.setText(now.toString());
                 //gives wider range to ensure alarm time not missed
                final int MARGIN_OF_ERROR = 5; 
                Time alarmOff = (Time) alarmTime.clone();
                alarmOff.addSeconds(MARGIN_OF_ERROR);
                if (now.compareTo(alarmTime) >= 0 && alarmOff.compareTo(now) > 0) {
                    alarmPlayer.actionPerformed(e);
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();

        this.setSize(450, 300);
        this.setVisible(true);
    }
    
    public void setAlarmTime(int hours, int minutes, int seconds){
        alarmTime.setHours(hours)
                 .setMinutes(minutes)
                 .setSeconds(seconds);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final int ALARM_DURATION = 5;
        //new AlarmClock(new Clock(),new AlarmPlayer(ALARM_DURATION));
        new AlarmClock(new NISTClock(new NISTServer()), new AlarmPlayer(ALARM_DURATION));
    }
}
