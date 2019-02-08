import l293d.driver as l293d
# Motor 2 uses Pin 15, Pin 13, Pin 11
motor2 = l293d.motor(15,13,11)
# Run the motors so visible
for i in range(0,1500):
  motor2.clockwise()
l293d.cleanup()

