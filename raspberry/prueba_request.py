import requests
import time
url = 'https://pillboxapi.herokuapp.com/tomas'
urlAUTH='https://pillboxapi.herokuapp.com/auth/?access_token=ZPgUSMUlq4N7hlbuyRU1BsUn1U457dz6'
token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjM2NjMjcxZjA1ZTc1MjI2NTk4YzI5NyIsImlhdCI6MTU1MDU4OTU1N30.X-wOxkbiaP98gKKUeLreXI8dsoAm5TStLE2azkHOVAs'

response = requests.get(url+'/?access_token='+token)

# Print the status code of the response.
print(response.status_code)
print(response.json())
#response.headers['content-type']
print('-------------------------------------------------')
#response.encoding

#response.text
#response.json()
lista_tomas = []
while True:
  response = requests.get(url+'/?access_token='+token)
  cadena= str(response.json())
  pos_semana= cadena.find('dia_semana')
  pos_momento= cadena.find('momento_toma')
  #print(cadena.replace('[{', '').replace('{', '').replace('\'','').replace('}','').replace(':','').replace('\n','').replace('\t','') [pos:pos+10])
  print(cadena [pos_semana+14:pos_semana+20])
  print(cadena [pos_momento+16:pos_momento+17])
  time.sleep(30)