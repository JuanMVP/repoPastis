import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BOARD)
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

tomas = ['09:00','21:05']
hora=str(time.localtime())[str(time.localtime()).find('tm_hour')+8:str(time.localtime()).find('tm_hour')+10]
minutos=str(time.localtime())[str(time.localtime()).find('tm_min')+7:str(time.localtime()).find('tm_min')+9]
date= hora+':'+minutos

ans=True
cont=30
while True:
    hora=str(time.localtime())[str(time.localtime()).find('tm_hour')+8:str(time.localtime()).find('tm_hour')+10]
    minutos=str(time.localtime())[str(time.localtime()).find('tm_min')+7:str(time.localtime()).find('tm_min')+9]
    date= hora+':'+minutos
    print(date)
    print ("""
    1.Girar COMPLETO
    2.Girar derecha
    3.Inicio
    4.Salir
    """)
    time.sleep(15)
    #ans=raw_input("Elige una opcion: ") 
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
    #elif hora+':'+minutos == date:
    elif '21:14' == date:
      print('entroooooooooooooooooooooooooo')
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

GPIO.cleanup()


