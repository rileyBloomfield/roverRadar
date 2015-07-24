package roverradar;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
    
    private final RadarController radarController = new RadarController();
    private final RadarButtonPanel controlPanel = new RadarButtonPanel();
    
    private JPanel gridPanel = new JPanel();
    
    MainWindow() {
        setTitle("Rover Radar");
        setResizable(false);
        setLocationRelativeTo(null);
        
        radarController.initialize();
        
        gridPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        gridPanel.add(radarController.getRadarCanvas());
        gridPanel.add(controlPanel);

        add(gridPanel);
        pack();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                radarController.close();
                System.exit(0);
            }
        });
    }
}