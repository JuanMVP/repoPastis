from RPLCD.gpio import CharLCD
import RPi.GPIO as GPIO
import time

#lcd = CharLCD(cols=16, rows=2, pin_rs=7, pin_e=8, pins_data=[25, 24, 23, 18],numbering_mode=GPIO.BOARD)
lcd = CharLCD(pin_rs=15, pin_rw=18, pin_e=16, pins_data=[21, 26, 23, 24],numbering_mode=GPIO.BCM)
def main():
    
    lcd.write_string("Hora : %s" %time.strftime("%H:%M:%S"))
    
    lcd.cursor_pos = (1, 0)
    lcd.write_string("Fecha: %s" %time.strftime("%d/%m/%y"))
    time.sleep(1)
    
try:
 while True: 
   main()
   lcd.clear()
   
 
except KeyboardInterrupt:
 pass
 
finally:
 lcd.clear()
 GPIO.cleanup()
 