from gpiozero import Buzzer
from time import sleep

buzzer = Buzzer(26)
while True:
    buzzer.on()
    sleep(1)
    buzzer.off()
    sleep(1)

while True:
    buzzer.beep()
