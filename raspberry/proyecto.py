import RPi.GPIO as GPIO
import time

# GPIO to LCD mapping
LCD_RS = 7 # Pi pin 26
LCD_E = 8 # Pi pin 24
LCD_D4 = 25 # Pi pin 22
LCD_D5 = 24 # Pi pin 18
LCD_D6 = 23 # Pi pin 16
LCD_D7 = 18 # Pi pin 12

# Device constants
LCD_CHR = True # Character mode
LCD_CMD = False # Command mode
LCD_CHARS = 16 # Characters per line (16 max)
LCD_LINE_1 = 0x80 # LCD memory location for 1st line
LCD_LINE_2 = 0xC0 # LCD memory location 2nd line

# Define main program code
def main():
 
 GPIO.setwarnings(False)
 GPIO.setmode(GPIO.BCM) # Use BCM GPIO numbers
 GPIO.setup(LCD_E, GPIO.OUT) # Set GPIO's to output mode
 GPIO.setup(LCD_RS, GPIO.OUT)
 GPIO.setup(LCD_D4, GPIO.OUT)
 GPIO.setup(LCD_D5, GPIO.OUT)
 GPIO.setup(LCD_D6, GPIO.OUT)
 GPIO.setup(LCD_D7, GPIO.OUT)
#--------
 #GPIO.setmode(GPIO.BOARD)
 control_pins = [7,11,13,15]
 for pin in control_pins:
   GPIO.setup(pin, GPIO.OUT)
   GPIO.output(pin, 0)
 halfstep_seq = [
  [1,0,0,0],
  [1,1,0,0],
  [0,1,0,0],
  [0,1,1,0],
  [0,0,1,0],
  [0,0,1,1],
  [0,0,0,1],
  [1,0,0,1]
 ]
# Initialize display
 lcd_init()

# Loop - send text and sleep 3 seconds between texts
# Change text to anything you wish, but must be 16 characters or less
 ans=True
 cont=30
 while ans:
    print ("""
    1.Girar COMPLETO
    2.Girar derecha
    3.Inicio
    4.Salir
    """)
    ans=raw_input("Elige una opcion: ") 
    if ans=="1":
      for i in range(512):
        for halfstep in range(8):
          for pin in range(4):
            GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
          time.sleep(0.001) 

      print("\n Giro realizado") 

    elif ans=="2":
      for i in range(17):
        for halfstep in range(8):
          for pin in range(4):
            GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
          time.sleep(0.001) 
      cont= cont -1
      print("\n Giro realizado") 
      print(cont)
    elif ans=="3":
      for e in range(cont):
        for i in range(17):
          for halfstep in range(8):
            for pin in range(4):
              GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
            time.sleep(0.001) 
      cont=30
      print("\n Giro realizado")
      
    elif ans=="4":
      ans=False
      print("\n Adios") 
    elif ans !="":
      print("\n Respuesta no valida") 


 while True:
     lcd_text("Hola Mundo!",LCD_LINE_1)
     lcd_text("",LCD_LINE_2)

     time.sleep(3) # 3 second delay

     lcd_text("ABCDEFGHIJKLMNOP",LCD_LINE_1)
     lcd_text("1234567890123456",LCD_LINE_2)

     time.sleep(3) # 3 second delay

     lcd_text("Esto es una prueba",LCD_LINE_1)
     lcd_text("Raspberry Pi :)",LCD_LINE_2)

     time.sleep(3)

# End of main program code


# Initialize and clear display
def lcd_init():
 lcd_write(0x33,LCD_CMD) # Initialize
 lcd_write(0x32,LCD_CMD) # Set to 4-bit mode
 lcd_write(0x06,LCD_CMD) # Cursor move direction
 lcd_write(0x0C,LCD_CMD) # Turn cursor off
 lcd_write(0x28,LCD_CMD) # 2 line display
 lcd_write(0x01,LCD_CMD) # Clear display
 time.sleep(0.0005) # Delay to allow commands to process

def lcd_write(bits, mode):
# High bits
 GPIO.output(LCD_RS, mode) # RS

 GPIO.output(LCD_D4, False)
 GPIO.output(LCD_D5, False)
 GPIO.output(LCD_D6, False)
 GPIO.output(LCD_D7, False)
 if bits&0x10==0x10:
     GPIO.output(LCD_D4, True)
 if bits&0x20==0x20:
     GPIO.output(LCD_D5, True)
 if bits&0x40==0x40:
     GPIO.output(LCD_D6, True)
 if bits&0x80==0x80:
     GPIO.output(LCD_D7, True)

# Toggle 'Enable' pin
 lcd_toggle_enable()

# Low bits
 GPIO.output(LCD_D4, False)
 GPIO.output(LCD_D5, False)
 GPIO.output(LCD_D6, False)
 GPIO.output(LCD_D7, False)
 if bits&0x01==0x01:
     GPIO.output(LCD_D4, True)
 if bits&0x02==0x02:
     GPIO.output(LCD_D5, True)
 if bits&0x04==0x04:
     GPIO.output(LCD_D6, True)
 if bits&0x08==0x08:
     GPIO.output(LCD_D7, True)

# Toggle 'Enable' pin
 lcd_toggle_enable()

def lcd_toggle_enable():
 time.sleep(0.0005)
 GPIO.output(LCD_E, True)
 time.sleep(0.0005)
 GPIO.output(LCD_E, False)
 time.sleep(0.0005)

def lcd_text(message,line):
 # Send text to display
 message = message.ljust(LCD_CHARS," ")

 lcd_write(line, LCD_CMD)

 for i in range(LCD_CHARS):
     lcd_write(ord(message[i]),LCD_CHR)


#Begin program
try:
 main()
 
except KeyboardInterrupt:
 pass
 
finally:
 lcd_write(0x01, LCD_CMD)
 lcd_text("    ADIOS",LCD_LINE_1)
 lcd_text("  REPO PASTIS",LCD_LINE_2)
 GPIO.cleanup()