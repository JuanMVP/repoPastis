import l293d.driver as l293d
motor1 = l293d.motor(22,18,16)
motor2 = l293d.motor(15,13,11)
for i in range(0,10):
  motor1.clockwise()
  motor2.clockwise()
l293d.cleanup()

