Riley Bloomfield
2015

Rover radar communicates with an arduino equiped with an HCSO4 or similar through the default serial port. 
The arduino passes a sweep angle and distance reading in the format distance,angle where distance is a reading from the ultrasonic sensor in centimeters and angle is the angle of a servo pointing the sensor.
Dots are plotted from readings obtained from the last sweep. The green radar sweep line will move accoring to the received sweep angle from the arduino.
The zoom slider will adjust the centimeters per visible ring scale.
