from django.urls import path
from . import views

urlpatterns = [
#    path('', views.wifi_list, name='wifi_list'),
#    path('conexion/', views.conexion, name='conexion')
    path('', views.conexion, name='conexion')
]
