# CyberSend · WhatsCron

A scheduling prototype for composing messages, managing a local queue and generating `wa.me` links for manual sending.

<p align="center">
  <a href="https://tzayadet.github.io/Cybersend-WhatsCron/"><img src="https://img.shields.io/badge/STATIC_DEMO-OPEN-00a86b?style=flat-square" alt="Static demo"></a>
  <a href="https://github.com/tzayadet/Cybersend-WhatsCron"><img src="https://img.shields.io/badge/SOURCE-GITHUB-161b22?style=flat-square&logo=github" alt="Source"></a>
</p>

[![Java CI](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/java-ci.yml/badge.svg)](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/java-ci.yml) · [![Static deployment](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/static.yml/badge.svg)](https://github.com/tzayadet/Cybersend-WhatsCron/actions/workflows/static.yml)

> Prototype status: it does not send messages automatically through WhatsApp. Scheduled items requiring delivery move to `AWAITING_MANUAL` and generate a link for a person to complete the action.

## Components

- **Static frontend:** browser interface and manual `wa.me` link generation.
- **Spring Boot backend:** local H2 persistence, REST API and WebSocket updates.
- **Messaging integration:** not implemented. No WhatsApp Web or WhatsApp Business Platform transport is connected.

The public Pages demo is a static interface. It does not run the Spring Boot backend or connect to WhatsApp.

## Requirements

- Java 17 or later
- Maven 3.8 or later

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
- The API is a prototype and must not be exposed publicly without authentication, authorization and appropriate request protections.
- Local H2 storage is not a multi-user or high-availability database.

## Deployment

GitHub Pages publishes the static frontend only. A deployed backend would require a separate Java hosting environment and a properly authorised messaging provider integration.

## License

MIT. See [LICENSE](LICENSE).
