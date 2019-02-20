import RPi.GPIO as GPIO
from gpiozero import Buzzer
import time
import requests

buzzer = Buzzer(13)
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

url = 'https://pillboxapi.herokuapp.com/tomas'
urlAUTH='https://pillboxapi.herokuapp.com/auth/?access_token=ZPgUSMUlq4N7hlbuyRU1BsUn1U457dz6'
token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjM2NjMjcxZjA1ZTc1MjI2NTk4YzI5NyIsImlhdCI6MTU1MDU4OTU1N30.X-wOxkbiaP98gKKUeLreXI8dsoAm5TStLE2azkHOVAs'

tomas = ['09:00','10:05']
hora=str(time.localtime())[str(time.localtime()).find('tm_hour')+8:str(time.localtime()).find('tm_hour')+10]
minutos=str(time.localtime())[str(time.localtime()).find('tm_min')+7:str(time.localtime()).find('tm_min')+9]
date= hora+':'+minutos

ans=True
cont=30
#while True:
#  response = requests.get(url+'/?access_token='+token)
#  cadena= str(response.json())
#  pos_semana= cadena.find('dia_semana')
#  pos_momento= cadena.find('momento_toma')
#  #print(cadena.replace('[{', '').replace('{', '').replace('\'','').replace('}','').replace(':','').replace('\n','').replace('\t','') [pos:pos+10])
#  print(cadena [pos_semana+14:pos_semana+20])
#  print(cadena [pos_momento+16:pos_momento+17])
#  time.sleep(60)
while True:
    hora=str(time.localtime())[str(time.localtime()).find('tm_hour')+8:str(time.localtime()).find('tm_hour')+10]
    minutos=str(time.localtime())[str(time.localtime()).find('tm_min')+7:str(time.localtime()).find('tm_min')+9]
    date= hora+':'+minutos
    print(date)    
    time.sleep(60)
    #ans=raw_input("Elige una opcion: ") 
    if ans=="1":
      for i in range(512):
        for halfstep in range(8):
          for pin in range(4):
            GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
          time.sleep(0.001) 

      print("\n Giro realizado") 

   
    #elif hora+':'+minutos == date:
    elif '10:10' == date:
      for i in range(17):
        for halfstep in range(8):
          for pin in range(4):
            GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
          time.sleep(0.001) 
      cont= cont -1
      print("\n Giro realizado") 
      print(cont)
      buzzer.on()
      sleep(1)
      buzzer.off()
    elif ans=="3":
      for e in range(cont):
        for i in range(17):
          for halfstep in range(8):
            for pin in range(4):
              GPIO.output(control_pins[pin], halfstep_seq[halfstep][pin])
            time.sleep(0.001) 
      cont=30
      print("\n Giro realizado")   

GPIO.cleanup()


