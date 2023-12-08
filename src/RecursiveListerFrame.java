import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveListerFrame extends JFrame {
    JPanel panelMain, panelCenter, panelBottom, panelTop;

    JLabel labletitle;
    JScrollPane pane;
    JTextArea jTextArea;

    JButton buttonquit, buttonSearch;

    public RecursiveListerFrame()
    {
        setTitle("Lister for the Recursive Directory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int heightScreen = screenSize.height;
        int widthScreen = screenSize.width;

        setSize(750, 750);

        panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        add(panelMain);
        createPanelCenter();
        createPanelBottom();
        createPanelTop();
        setVisible(true);
    }


    private void createPanelTop()
    {
        panelTop= new JPanel();
        labletitle = new JLabel("File List Machine");
        labletitle.setVerticalTextPosition(JLabel.BOTTOM);
        labletitle.setHorizontalTextPosition(JLabel.CENTER);
        panelTop.add(labletitle);
        panelMain.add(panelTop, BorderLayout.NORTH);
    }
    private void createPanelCenter() {
        panelCenter = new JPanel();

        jTextArea = new JTextArea(40, 60);
        pane = new JScrollPane(jTextArea);

        jTextArea.setEditable(false);

        panelCenter.add(pane);
        panelMain.add(panelCenter, BorderLayout.CENTER);
    }
    private void createPanelBottom() {
        panelBottom= new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));

        buttonSearch = new JButton("Search Directory");
        buttonquit = new JButton("Quit");

        panelBottom.add(buttonSearch);
        panelBottom.add(buttonquit);

        panelMain.add(panelBottom, BorderLayout.SOUTH);


        buttonquit.addActionListener((ActionEvent ae) -> System.exit(0));

        buttonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select A Directory: ");

                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);


                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File dirChosen = chooser.getSelectedFile();
                    jTextArea.setText("Chosen Directory:   " + dirChosen + "\n\n");
                    jTextArea.append("Directory and Sub Directories: " +"\n\n");

                    listFiles(dirChosen);

                } else
                    jTextArea.append("File not found! Try Again!");
            }
        });

    }
    private void listFiles(File directory) {
        // Get the files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            // Iterate over the files
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles(file);
                } else {
                    // If the file is a file, append its path to the JTextArea
                    jTextArea.append(file.getPath() + "\n");
                }
            }
        }
    }
}