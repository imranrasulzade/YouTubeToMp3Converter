import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YouTubeToMp3Converter {

    public static void main(String[] args) {
        JFrame frame = new JFrame("YouTube to MP3 Converter");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel linkLabel = new JLabel("YouTube Link:");
        linkLabel.setBounds(10, 20, 100, 25);
        panel.add(linkLabel);

        JTextField linkText = new JTextField(20);
        linkText.setBounds(120, 20, 350, 25);
        panel.add(linkText);

        JButton convertButton = new JButton("Convert and Download");
        convertButton.setBounds(150, 70, 200, 25);
        panel.add(convertButton);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String videoUrl = linkText.getText();
                if (!videoUrl.isEmpty()) {
                    downloadAndConvertVideo(videoUrl);
                } else {
                    JOptionPane.showMessageDialog(panel, "Enter YouTube link", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static void downloadAndConvertVideo(String videoUrl) {
        try {
            // Videonu endir
            String downloadCommand = String.format("youtube-dl -o video.%%(ext)s %s", videoUrl);
            Process downloadProcess = Runtime.getRuntime().exec(downloadCommand);
            downloadProcess.waitFor();

            // Video-nu mp3-e convert
            String convertCommand = "ffmpeg -i video.mp4 -q:a 0 -map a output.mp3";
            Process convertProcess = Runtime.getRuntime().exec(convertCommand);
            convertProcess.waitFor();

            JOptionPane.showMessageDialog(null, "MP3 file successfully downloaded!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong. Please, try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
