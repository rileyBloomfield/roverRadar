package roverradar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

public final class RadarCanvas extends JPanel{
    
    private final RadarModel model = RadarModel.getInstance();
    
    private AffineTransform transform;

    RadarCanvas() {
        super(true);
        setPreferredSize(new Dimension(model.getPanelWidth(), model.getPanelHeight()));
        transform = new AffineTransform(1, 0, 0, -1, model.getPanelWidth()/2, model.getPanelHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphic = (Graphics2D) g;
        graphic.setTransform(transform);
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(graphic);
        drawCircles(graphic);
        drawDistance(graphic);

        graphic.setColor(Color.green);
        graphic.drawLine(0, 0, (int) (model.getPanelWidth() * Math.cos(model.getRadarSweepAngle())), (int) (model.getPanelWidth() * Math.sin(model.getRadarSweepAngle())));
    }

    void drawCircles(Graphics2D graphic) {
        graphic.setColor(Color.white);
        for(int ringRad = 0; ringRad <= Math.sqrt(model.getPanelHeight() * model.getPanelHeight() + model.getPanelWidth() * model.getPanelWidth() / 4); ringRad += model.getRadarPixelsPerCentimeter() * model.getRadarCentimetersPerRing()) {
            drawOval(graphic, 0, 0, ringRad, ringRad);        
        }
    }
    
    void drawOval(Graphics2D g, int x, int y, int rx, int ry) {
        g.drawOval(x - rx, y - ry, 2 * rx, 2 * ry);
    }

    void drawBackground(Graphics2D graphic) {
        graphic.setColor(Color.black);
        graphic.fillRect(-model.getPanelWidth() / 2, 0, model.getPanelWidth(), model.getPanelHeight());
    }

    void drawDistance(Graphics2D graphic) {
        graphic.setColor(Color.red);
        for(Point p: model.getRadarMarkers().values()) {
           graphic.fillOval(p.x * model.getRadarPixelsPerCentimeter(), p.y * model.getRadarPixelsPerCentimeter(), model.getRadarMarkerDiameter(), model.getRadarMarkerDiameter());
        }
    }
}
