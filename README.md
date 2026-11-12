<p align="center">
  <img src="https://img.shields.io/badge/MESSAGING_AUTOMATION-CYBERSEND_WHATSCRON-101010?style=for-the-badge&labelColor=050805&color=00FF41" alt="CyberSend"/>
  <img src="https://img.shields.io/badge/JAVA-SPRING_BOOT-101010?style=for-the-badge&logo=springboot&logoColor=00FF41" alt="Spring Boot"/>
</p>

# CyberSend · WhatsCron

Scheduling prototype for composing messages, managing a local queue and generating `wa.me` links for manual sending.

<p align="center">
  <a href="https://tzayadet.github.io/Cybersend-WhatsCron/"><strong>STATIC DEMO ↗</strong></a> ·
  <a href="https://github.com/tzayadet/Cybersend-WhatsCron"><strong>SOURCE CODE</strong></a>
</p>

[![Java CI](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/java-ci.yml/badge.svg)](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/java-ci.yml) · [![Static deployment](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/static.yml/badge.svg)](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/static.yml)

> It does not send messages automatically through WhatsApp. Scheduled items requiring delivery move to `AWAITING_MANUAL` and generate a link for a person to complete the action.

## Components

- **Static frontend:** browser interface and manual `wa.me` link generation.
- **Spring Boot backend:** local H2 persistence, REST API and WebSocket updates.
- **Messaging integration:** not implemented. No WhatsApp Web or WhatsApp Business Platform transport is connected.

The public Pages demo is a static interface. It does not run the Spring Boot backend or connect to WhatsApp.

## Requirements

- Java 17+
- Maven 3.8+

## Run locally

```bash
mvn spring-boot:run
```

The backend listens on `127.0.0.1:8080` by default.

Run verification:

```bash
mvn --batch-mode verify
```

## Configuration

Relevant environment variables include:

- `PORT`: local server port.
- `SERVER_ADDRESS`: bind address.
- `DATABASE_URL`: H2 JDBC URL.
- `DB_USER` and `DB_PASSWORD`: local database credentials.
- `H2_CONSOLE_ENABLED`: H2 console toggle.
- `CORS_ALLOWED_ORIGINS`: permitted frontend origins.

## Scope and limitations

- No real WhatsApp integration is present.
- Session and QR states are simulated; the QR graphic is not scannable.
- No delivery or read receipt is guaranteed.
- Messages requiring human action remain `AWAITING_MANUAL`.
- The API is a prototype and must not be exposed publicly without authentication, authorisation and appropriate request protections.
- Local H2 storage is intended for development, not production use.

## Deployment

GitHub Pages serves only the static frontend. The Spring Boot backend requires a separate Java-compatible host.

## License

See [LICENSE](LICENSE) for the repository's licensing terms.
