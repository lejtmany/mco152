package alarmclockV4;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

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
    private AlarmPlayer alarmPlayer = new AlarmPlayer(60);

    private Time alarmTime = new Time();
    private IClock clock ;

    public AlarmClock(IClock ic, ActionListener actionListener) {
        clock = ic;
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
                    alarmTime.setHours(Integer.parseInt(hourTextField.getText()) + addHours)
                            .setMinutes(Integer.parseInt(minuteTextField.getText()))
                            .setSeconds(0);
                    statusBar.setText("Alarm set to " + alarmTime);
                } catch (NumberFormatException ex) {
                    statusBar.setText("H M must be integers");
                }
            }
    }        );

        Timer timer = new Timer(1000, new ActionListener() {
            private boolean alarmOn = false;
            private boolean alarmAlreadyOn = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                Time now = clock.getTime();
                timeLabel.setText(now.toString());

                final int ALARM_DURATION = 5;
                Time alarmOff = (Time) alarmTime.clone();
                alarmOff.addSeconds(ALARM_DURATION);
                if (now.compareTo(alarmTime) >= 0 && alarmOff.compareTo(now) > 0) {
                    actionListener.actionPerformed(e);
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();

        this.setSize(450, 300);
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        new AlarmClock(new InternationalClock("America/New_York"), new AlarmPlayer(60));
    }
}
