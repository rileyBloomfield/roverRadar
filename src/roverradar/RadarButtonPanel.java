package roverradar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RadarButtonPanel extends JPanel {
    
    private RadarModel model = RadarModel.getInstance();

    JSlider distScale = new JSlider(JSlider.VERTICAL, 1, 10, 5);
    JLabel zoomLabel = new JLabel("Zoom");

    RadarButtonPanel() {
        distScale.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setRadarPixelsPerCentimeter(distScale.getValue());
            }
        });
        add(distScale);
        add(zoomLabel);
    }
}
