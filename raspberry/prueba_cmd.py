import os
import time
l=0
#l=os.popen('sudo python prueba_Bluetooth.py').read()
os.popen('python lcd.py')
time.sleep(5)
os.popen('python lcd.py')
os.close('python lcd.py')
print(l)