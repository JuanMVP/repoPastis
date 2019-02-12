import RPi.GPIO as GPIO
import time

servo_pin = 19
duty_cycle = 7.5     # Should be the centre for a SG90

# Configure the Pi to use pin names (i.e. BCM) and allocate I/O
GPIO.setmode(GPIO.BCM)
GPIO.setup(servo_pin, GPIO.OUT)

# Create PWM channel on the servo pin with a frequency of 50Hz
pwm_servo = GPIO.PWM(servo_pin, 50)
pwm_servo.start(duty_cycle)

try:
    while True:
        duty_cycle = float(input("Enter Duty Cycle (Left = 5 to Right = 10):"))
        pwm_servo.ChangeDutyCycle(duty_cycle)
            
except KeyboardInterrupt:
    print("CTRL-C: Terminating program.")
finally:
    print("Cleaning up GPIO...")
    GPIO.cleanup()
