import os


def list_wifi():
    redes = os.popen('iwlist scan | grep ESSI').read()

    lista_redes = []

    lista_txt_redes = redes.split('ESSID:')
    for linea in lista_txt_redes:
        cadena = linea.replace('"', '').replace('\n','').replace('\t','')
        cadena = cadena.strip()
        lista_redes.append(cadena)

    return list(set(lista_redes))
    # return lista_redes

def choice_wifi():
    print(list_wifi())
    result = []
    for ssid in list_wifi():
        result.append((ssid, ssid))
    
    return result
