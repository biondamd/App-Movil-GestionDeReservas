# Andes Aventura – App de reservas turísticas

Aplicación Android nativa en Kotlin para la empresa ficticia **Andes Aventura**, dedicada al turismo de aventura en Cusco. Sirve para gestionar reservas: lista, detalle, registro, cambio de estado y punto de encuentro en Google Maps.

## Requisitos

- Android Studio (versión reciente, con la JBR integrada).
- Android SDK 35. Lo instala Android Studio si falta.
- Gradle 9.2.1, que se descarga solo con el wrapper, y AGP 8.13.
- Kotlin 2.2.
- Un dispositivo o emulador con Android 9 (API 28) o superior y Google Play Services.
- Una API Key de Google Maps SDK for Android.

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/biondamd/App-Movil-GestionDeReservas.git
   ```
2. Abrir la carpeta del proyecto en Android Studio y esperar a que termine la sincronización de Gradle.
3. Agregar la API Key de Google Maps en `local.properties`, en la raíz del proyecto. Este archivo no se sube al repositorio:
   ```properties
   MAPS_API_KEY=TU_API_KEY
   ```

## Ejecución

- Desde Android Studio: elegir el emulador o el dispositivo y pulsar **Run ▶**.
- Desde la terminal:
  ```bash
  ./gradlew installDebug
  ```

Credenciales de prueba:

- **Usuario:** `admin@andesaventura.pe`
- **Contraseña:** `123456`

## Flujo de prueba

1. **Splash** (5 segundos): logo y nombre de la empresa.
2. **Login**:
   - Con campos vacíos o un correo inválido, el formulario muestra errores de validación.
   - Con las credenciales demo se entra a la app.
3. **Inicio** (Navigation Drawer):
   - El menú tiene Inicio, Nosotros, Contactos y Login.
   - **Nosotros:** presentación, misión, visión y valores de la empresa.
   - **Contactos:** datos de contacto y un formulario que se valida y se envía.
   - **Login:** usuario actual y cierre de sesión.
4. **Paquetes turísticos:**
   - La lista usa RecyclerView.
   - Abrir un paquete muestra su detalle.
   - Desde ahí, **Ver punto de encuentro** abre el mapa y **Reservar** abre el formulario.
5. **Reservas:**
   - Lista con el estado de cada reserva (PENDIENTE, CONFIRMADO, FINALIZADO).
   - El FAB **+** registra una reserva nueva. El formulario valida los datos y la fecha se elige con un DatePicker.
   - El detalle de cada reserva permite cambiar su estado y verla en el mapa.
6. **Mapa:**
   - Muestra un marcador en el punto de encuentro.
   - El FAB de ubicación pide el permiso de localización y muestra la ubicación actual.
   - Si no hay GPS, usa una ubicación simulada en Cusco.
7. **Salarios:** se elige un trabajador, se ingresan sus horas extra y se ve el detalle de su salario.

## Estructura

```
app/src/main/java/com/andesaventura/app/
├── model/      Modelos (Reserva, EstadoReserva, Paquete, Trabajador)
├── data/       Repositorios en memoria y SessionManager
├── adapter/    Adapters del RecyclerView
└── ui/         Activities y fragments/ (Navigation Drawer)
```
