package roverradar;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class RadarController implements SerialPortEventListener {

    private final RadarModel model = RadarModel.getInstance();
    private final RadarCanvas radarCanvas = new RadarCanvas();

    public RadarCanvas getRadarCanvas() {
        return radarCanvas;
    }

    SerialPort serialPort;
    private static final String PORT_NAMES[] = {
        "/dev/cu.usbmodem1421", // Mac OSX
        "/dev/ttyACM0", // Raspberry Pi
        "/dev/ttyUSB0", // Linux
        "COM3", // Windows
    };

    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }

        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
            System.out.println("Serial connection closed.");
        }
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String[] tokens = input.readLine().split(",");

                int radCm = Integer.parseInt(tokens[0]);
                model.setRadarSweepAngle(Math.toRadians(Integer.parseInt(tokens[1])));
                if (radCm == -1) {
                    model.getRadarMarkers().remove(model.getRadarSweepAngle());
                } else {
                    model.getRadarMarkers().put(
                            model.getRadarSweepAngle(),
                            new Point((int) (radCm * Math.cos(model.getRadarSweepAngle())), (int) (radCm * Math.sin(model.getRadarSweepAngle())))
                    );
                }

                radarCanvas.repaint();
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
    }
}
