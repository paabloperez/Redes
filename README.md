# 🌐 Redes de Ordenadores - 2º curso, 2º Cuatrimestre

Este repositorio contiene las prácticas de programación y análisis de protocolos realizadas en la asignatura **Redes** del Grado en Enxeñaría Informática (UDC). El enfoque principal es comprender la arquitectura de Internet mediante el modelo **TCP/IP**.

## 🎯 Objetivos de la asignatura
- Entender la división de redes en capas de protocolos (Modelo OSI y TCP/IP).
- Dominar el funcionamiento de protocolos de **Capa de Aplicación** (HTTP, DNS, FTP).
- Analizar y programar mecanismos de la **Capa de Transporte** (TCP y UDP).
- Comprender el enrutamiento IP, subredes, ICMP e IPv6.

## 🚀 Proyecto Destacado: Programación de Comunicaciones en Java
Implementación de aplicaciones cliente-servidor utilizando la API de red de Java, trabajando directamente con los protocolos de transporte.

### Conceptos implementados:
- **TCP Sockets:** Creación de conexiones fiables y orientadas al flujo de datos (`ServerSocket` y `Socket`).
- **UDP Datagrams:** Envío de paquetes no orientados a conexión, gestionando la pérdida de datos y el orden (`DatagramSocket` y `DatagramPacket`).
- **Serialización:** Envío de objetos y estructuras de datos a través de la red.
- **Análisis de Tráfico:** Uso de herramientas de inspección para verificar el intercambio de flags (SYN, ACK, FIN) en el *three-way handshake* de TCP.



## 📂 Contenidos del Repositorio
1. **Capa de Aplicación:** Implementación de servicios y protocolos de nivel superior.
2. **Capa de Transporte:** Diferencias de operativa entre el intercambio de datos fiable (TCP) y el intercambio rápido (UDP).
3. **Capa de Red:** Configuración de subredes, máscaras y análisis de rutas de enrutamiento.
4. **Capa de Enlace:** Tecnologías de nivel físico y enlace de datos.



[Image of TCP/IP protocol suite layers]


## 🛠️ Tecnologías y Herramientas
- **Lenguaje:** Java (Network API).
- **Análisis de Protocolos:** Wireshark / Tcpdump (inspección de tráfico real).
- **Emulación:** Herramientas de simulación de redes y entornos Linux.

## 📖 Bibliografía de Referencia
- *Computer Networking: A Top-Down Approach* - Kurose & Ross (El libro de cabecera en redes).
- *TCP/IP Illustrated* - W. Richard Stevens.

---
> "En la red, el software no está solo; la ingeniería es la que asegura que los datos lleguen a su destino."
