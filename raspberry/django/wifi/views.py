from django.shortcuts import render, render_to_response
from .forms import WifiConfigurerForm
import shlex, subprocess
import os

# Create your views here.

def conexion(request):
    # return render_to_response('wifi/titulo.html', {})
    respuesta = ""
    if (request.method == "POST"):
        # aquí se debe gestionar la conexión con los datos recogidos
        form = WifiConfigurerForm(request.POST)
        print(form.data['ssid'])
        print(form.data['passwd'])

        ssid = form.data['ssid']
        passw = form.data['passwd']
        comando = ('nmcli d wifi connect '+ssid+' password '+passw+'')
        #nmcli d wifi connect Prueba  password pepe123456 iface wlxc4e984deed69
        respuesta = os.popen(comando).read
        #response = os.popen('iwconfig essid ',form.data['ssid'],' key ',form.data['passwd']).read()
        # enviar a una vista de éxito o de error, según corresponda
        return render(request, 'wifi/configurer_form.html',{'form': form},{'request' : respuesta})
    else :   
        form = WifiConfigurerForm()
        return render(request, 'wifi/configurer_form.html', {'form': form},{'request' : respuesta})

def correcto(request):
    return render(request, 'wifi/correcto.html')
    
