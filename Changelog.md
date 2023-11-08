# Registre de canvis

## [Versió 4.0.0] - 08/11/2023

### Agregado
- Funcionalidad A: Se añadió todo el código nuevo para el envío de mensajes.
- Funcionalidad B: Se implementaron las dos actividades principales.
- Funcionalidad C: Se introdujo una nueva funcionalidad para la gestión del modelo MVVM.
- Funcionalidad D: Paquete añadido para la gestión del modelo MVVM.
- Funcionalidad E: Se agregó la clase MessagesRepository para gestionar el acceso a los datos de mensajes.
- Funcionalidad F: Se creó la clase MessagesViewModel para gestionar el estado y la vista de mensajes.
- Funcionalidad G: ViewModel incorporado a MessagesWindow para la gestión del estado y proporcionar información de forma reactiva.
- Funcionalidad H: El adaptador MessageAdapter fue modificado para acceder a los datos de mensajes a través del repositorio.
- Funcionalidad I: Añadida la observación de cambios y la actualización automática de la vista para mostrar nuevos mensajes y hacer que el último mensaje sea visible.
- Funcionalidad J: Estructura de paquetes del proyecto modificada para una mejor organización arquitectónica.
- Funcionalidad K: Estructura de recursos actualizada para incluir adaptadores y vistas relacionadas con mensajes.

### Cambiado
- Funcionalidad L: Validación de los datos de entrada en MainActivity.kt para utilizar un ViewModel.
- Funcionalidad M: Validación de los datos de entrada en MainActivity.kt para mostrar mensajes de error correspondientes.
- Funcionalidad N: Validación de los datos de entrada en MessagesWindow.kt para hacer uso de un ViewModel.
- Funcionalidad O: Validación de los datos de entrada en MessagesWindow.kt para hacer uso de un ViewModel para gestionar el estado y la vista de los mensajes.
- Funcionalidad P: Añadido MessagesViewModel.kt para gestionar las operaciones y la vista de los mensajes.
- Funcionalidad Q: Modificado MessageRepository.kt para proporcionar una interfaz para el acceso a los datos de mensajes.
- Funcionalidad R: Modificado MessageAdapter.kt para acceder a los datos de mensajes a través del repositorio.
- Funcionalidad S: Validación de la entrada del usuario en MessagesWindow.kt y añadido del usuario a los mensajes enviados.
- Funcionalidad T: Actualizado el README para reflejar las modificaciones de la aplicación.
- Funcionalidad U: Creado un README adicional que muestra una descripción más detallada de la aplicación y sus características.
