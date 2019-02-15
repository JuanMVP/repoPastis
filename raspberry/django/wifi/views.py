from django.shortcuts import render, render_to_response
from .forms import WifiConfigurerForm
import shlex, subprocess
import os

# Create your views here.

def wifi_list(request):
    return render(request, 'wifi/wifi_list.html', {})

# Create your views here.

def list_wifi(request):
    #lista carpetas
    #ls = os.listdir('/')


    #lanza cualquier comando
    redes = os.popen('iwlist scan | grep ESSI').read()
    #pwd = subprocess.call("pwd", shell=True)
    print(redes)

    """
    lista = []
    palabra = False
    nombre = ""
    cont = 0
    for ltr in redes:
        if ltr == chr(34) and palabra == False:
            palabra = True
            cont = 0
        if ltr == chr(34) and palabra == True and cont == 1:
            palabra = False
            lista.append(nombre)
            nombre = ""
        if palabra == True:
            cont = 1
            nombre += ltr
    print(lista[:])
    """
    return render_to_response('wifi/list_wifi.html', {'nombre_redes': filtro(redes,chr(34)) }) 

def conexion(request):
    # return render_to_response('wifi/titulo.html', {})

    if (request.method == "POST"):
        # aquí se debe gestionar la conexión con los datos recogidos
        form = WifiConfigurerForm(request.POST)
        # form.data['ssid']
        # form.data['passwd']
        
        # enviar a una vista de éxito o de error, según corresponda
            
    else :   
        form = WifiConfigurerForm()
        return render(request, 'wifi/configurer_form.html', {'form': form})

def filtro(e_lista, d):
    rest = []
    palabra = False
    nombre = ""
    cont = 0

    for ltr in e_lista:
        if ltr == d and palabra == False:
            palabra = True
            cont = 0
        if ltr == d and palabra == True and cont == 1:
            palabra = False
            rest.append(nombre)
            nombre = ""
        if palabra == True:
            cont = 1
            nombre += ltr
    return rest
    
