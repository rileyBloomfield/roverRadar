package roverradar;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class RadarModel {
    
    private static RadarModel model = null;
    
    private double radarSweepAngle;
    private int radarSweepDirection, radarPixelsPerCentimeter, radarCentimetersPerRing;
    private int panelWidth, panelHeight;
    private int radarMarkerDiameter;
    private final Map<Double, Point> radarMarkers;
    
    private RadarModel() {
        radarSweepAngle = Math.PI/2;
        radarSweepDirection = 1;
        radarPixelsPerCentimeter = 5;
        radarCentimetersPerRing = 5;
        panelWidth = 720;
        panelHeight = 480;
        radarMarkerDiameter = 10;
        radarMarkers = new HashMap();
    }
    
    public static RadarModel getInstance() {
        if(model == null) {
         model = new RadarModel();
      }
      return model;
    }  
    
    public void setRadarSweepAngle(double angle) {
        radarSweepAngle = angle;
    }
    
    public void setRadarSweepDirection(int dir) {
        radarSweepDirection = dir;
    }
    
    public void setRadarPixelsPerCentimeter(int pixels) {
        radarPixelsPerCentimeter = pixels;
    }
    
    public void setRadarCentimetersPerRing(int cm) {
        radarCentimetersPerRing = cm;
    }
    
    public void setRadarPanelWidth(int width) {
        panelWidth = width;
    }
    
    public void setRadarPanelHeight(int height) {
        panelHeight = height;
    }
    
    public void setRadarMarkerDiameter(int diam) {
        radarMarkerDiameter = diam;
    }
    
    public double getRadarSweepAngle() {
        return radarSweepAngle;
    }
    
    public int getRadarSweepDirection() {
        return radarSweepDirection;
    }
    
    public int getRadarPixelsPerCentimeter() {
        return radarPixelsPerCentimeter;
    }
    
    public int getRadarCentimetersPerRing() {
        return radarCentimetersPerRing;
    }
    
    public Map<Double, Point> getRadarMarkers() {
        return radarMarkers;
    }
    
    public int getPanelWidth() {
        return panelWidth;
    }
    
    public int getPanelHeight() {
        return panelHeight;
    }
    
    public int getRadarMarkerDiameter() {
        return radarMarkerDiameter;
    }
}
