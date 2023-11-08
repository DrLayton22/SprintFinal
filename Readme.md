# Sprint Final - Rubén Bonilla

## Descripción
Este proyecto es una aplicación de chat para Android que permite a los usuarios conectarse a un servidor utilizando un nombre de usuario y una dirección IP válida. La última actualización (Sprint Final) incluye cambios significativos que mejoran la organización del código y las funcionalidades de la aplicación.

## Características Clave
- **Inicio de Sesión**: Los usuarios pueden ingresar un nombre de usuario y una dirección IP para conectarse al servidor.
- **Validación de Datos**: La aplicación valida que el nombre de usuario no esté vacío y que la dirección IP sea válida.
- **Interfaz de Usuario Mejorada**: Se ha mejorado la interfaz de usuario para una experiencia más atractiva y sencilla.
- **Ventana de Chat**: Una vez conectado, se muestra una ventana de chat para enviar y recibir mensajes.

## Uso
1. Abre la aplicación.
2. Ingresa tu nombre de usuario y la dirección IP del servidor.
3. Presiona el botón "Conectar".
4. Una vez conectado, se abrirá la ventana de chat para enviar y recibir mensajes.

## Cambios en el Sprint Final

### 1. Implementación de Arquitectura MVVM
- **Descripción**: En este Sprint, se ha aplicado la arquitectura MVVM (Model-View-ViewModel) para una mejor organización del código y la separación de responsabilidades.
- **Detalles**:
  - Se ha creado una clase `MessagesViewModel` que actúa como el ViewModel para la actividad `MessagesWindow`.
  - Se utiliza LiveData para proporcionar una conexión entre el ViewModel y la actividad.
  - El ViewModel se encarga de manejar la lógica de la ventana de chat y proporciona métodos para agregar mensajes, obtener el nombre de usuario y el servidor.

### 2. Validación Mejorada en MainActivity
- **Descripción**: Se ha mejorado la validación de datos en la actividad `MainActivity`.
- **Detalles**:
  - Se verifica que el nombre de usuario no esté vacío.
  - Se ha agregado una validación de dirección IP en un hilo secundario para garantizar que la dirección IP sea válida antes de continuar.
  - Se utiliza un hilo secundario para realizar la validación de IP y no bloquear la interfaz de usuario.

### 3. Ventana de Chat Mejorada (MessagesWindow)
- **Descripción**: Se ha introducido una nueva actividad llamada "MessagesWindow" que muestra la ventana de chat.
- **Detalles**:
  - Se ha creado una nueva actividad que muestra la ventana de chat.
  - La actividad recibe datos del nombre de usuario y dirección IP a través de un Intent.
  - Se ha mejorado la interfaz de usuario para mostrar información de conexión y permitir a los usuarios enviar mensajes.

### 4. Lógica de Mensajes en MessagesViewModel
- **Descripción**: Se ha trasladado la lógica de mensajes al ViewModel para mejorar la organización del código.
- **Detalles**:
  - El ViewModel se encarga de agregar mensajes y proporciona métodos para interactuar con la lista de mensajes.
  - La lógica de envío y recepción de mensajes se realiza a través del ViewModel.
