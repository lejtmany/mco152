package alarmclock4;
/*
Ben Forgy & Michael Davidson
MCO 152
HW 2
9/15/14
*/


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton setAlarmTimeButton = new JButton("Set");
    private ButtonGroup amPmButtonGroup = new ButtonGroup();

    private IAlarmPlayer alarmSound;
    private Time alarmTime;
    private final IClock clock;

    private void setUpGraphics() {
        alarmPanel.add(hourTextField);
        alarmPanel.add(minuteTextField);
        alarmPanel.add(amRadioButton);
        alarmPanel.add(pmRadioButton);
        alarmPanel.add(setAlarmTimeButton);

        amPmButtonGroup.add(amRadioButton);
        amPmButtonGroup.add(pmRadioButton);

        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 60));
        this.add(BorderLayout.NORTH, alarmPanel);
        this.add(BorderLayout.CENTER, timeLabel);

    }

    public AlarmClock(IClock clock_in, IAlarmPlayer alarmSound_in) {

        this.clock = clock_in;
        setUpGraphics();
        alarmTime = new Time(0, 0, 0);
        this.alarmSound = alarmSound_in;
        
        timeLabel.setText(formatTime(clock.getTime()));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //
        setAlarmTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int hour, minute;
                try {
                    hour = Integer.parseInt(hourTextField.getText());
                    minute = Integer.parseInt(minuteTextField.getText());
                    
                    if (pmRadioButton.isSelected()) {
                        hour += 12;
                    }
                    setAlarm(hour, minute);
                } catch (NumberFormatException ex) {
                    System.out.println("ERROR: (will keep running)");
                    ex.printStackTrace();
                }
            }
        });
        
        // Timer to update time lable and check alarm.
        Timer timer = new Timer(1000, (ActionEvent ae) -> {
            ITime time = clock.getTime();
            timeLabel.setText(formatTime(time));
            if(!alarmSound.isPlaying() && Math.abs(time.compareTo(alarmTime)-2) < 3){
                alarmSound.actionPerformed(null);
            }
        });
        timer.start();

        this.setSize(450, 300);
        this.setVisible(true);
    }

    // Takes in a time object and formats it as an AM/PM time.
    private String formatTime(ITime time) {
        int hourAMPM = (time.getHour() + 12) % 24;
        String AMPM;
        if(time.getHour() >= 12){
            AMPM = "PM";
        }
        else{
            AMPM = "AM";
        }
        return String.format("%2d:%02d:%02d " + AMPM, hourAMPM, 
                                    time.getMinute(), time.getSecond());

    }

    public void setAlarm(int hour, int minute) {
        if(hour > 23 || minute > 59){
            throw new IllegalArgumentException("Not a valid time.");
        }
        alarmTime.setHour(hour);
        alarmTime.setMinute(minute);
    }

}
