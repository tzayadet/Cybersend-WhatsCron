# CyberSend | WhatsApp Scheduler

Aplicación experimental para planificar mensajes, gestionar una cola local y generar enlaces `wa.me` para completar manualmente el envío.

## Estado real

**Prototipo local. No realiza envíos automáticos a WhatsApp.** Los mensajes que llegan a su hora pasan a `AWAITING_MANUAL`, se abre un enlace `wa.me` y nunca se contabilizan como enviados sin un proveedor real que confirme la entrega.

El backend Spring Boot mantiene mensajes programados y expone una API REST/WebSocket. La integración de transporte con WhatsApp no está implementada. La sesión y el QR son simulados. El sistema no debe marcar un mensaje como entregado: cuando no existe un proveedor real, conserva el estado pendiente y genera un enlace para que una persona complete el envío.

## Modos

- **Frontend HTML:** interfaz de navegador y generación de enlaces manuales.
- **Backend Spring Boot:** persistencia H2, API y notificaciones WebSocket para el prototipo.
- **Integración real:** pendiente. Requiere implementar un proveedor autorizado, preferiblemente WhatsApp Business Platform, con credenciales y webhooks gestionados fuera del repositorio.

## Requisitos

- Java 17+
- Maven 3.8+

## Ejecución local

Requiere Java 17 y Maven 3.8 o superior. El backend sigue la estructura estándar de Maven (`src/main/java`).

```bash
mvn spring-boot:run
```

Para compilar y ejecutar las pruebas:

```bash
mvn --batch-mode verify
```

Por defecto, el servidor escucha en `127.0.0.1:8080`. No lo expongas a Internet: las rutas de gestión todavía no tienen un sistema completo de autenticación y autorización.

## Configuración

Las variables configurables incluyen:

- `PORT`: puerto local (8080).
- `SERVER_ADDRESS`: dirección de escucha (127.0.0.1).
- `DATABASE_URL`: URL JDBC de H2 local.
- `DB_USER` y `DB_PASSWORD`: credenciales de la base de datos.
- `H2_CONSOLE_ENABLED`: consola H2 (desactivada por defecto).
- `CORS_ALLOWED_ORIGINS`: orígenes permitidos cuando se implemente el frontend separado.

## Limitaciones conocidas

- No hay integración real con WhatsApp Web ni con WhatsApp Business Platform.
- El estado de sesión se marca como `SIMULATED`; el QR de la interfaz es un patrón visual de demostración, no un código escaneable.
- La API no debe desplegarse en redes públicas hasta implementar autenticación, autorización y protección CSRF donde corresponda. CORS y WebSocket aceptan únicamente los orígenes definidos en `CORS_ALLOWED_ORIGINS`.
- El planificador no proporciona garantías de entrega ni confirmación de lectura. Los estados `SENT` solo se reservan para un envío confirmado por un proveedor real.
- Los mensajes que requieren intervención humana pasan a `AWAITING_MANUAL` y dejan de reintentarse automáticamente cada 30 segundos.
- Los datos locales H2 no son una solución multiusuario ni de alta disponibilidad.

## Licencia

MIT. Consulta [LICENSE](LICENSE).
