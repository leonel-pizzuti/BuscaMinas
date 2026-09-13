# BuscaMinas (Minesweeper) 💣

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Swing-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completado-brightgreen?style=for-the-badge)

Implementación clásica del juego **BuscaMinas** (Minesweeper) en entorno de escritorio, desarrollada en **Java** con interfaz gráfica interactiva construida en **Java Swing**[cite: 4, 5].

---

## 📋 Descripción

El proyecto recrea la experiencia clásica de despejar un campo de minas sin detonar ninguna. Aplica conceptos de Programación Orientada a Objetos (POO), manejo de eventos (clic izquierdo y derecho), generación pseudoaleatoria de minas y algoritmos recursivos de expansión para despejar áreas vacías contiguas automáticamente.

---

## ✨ Características

- **Tablero Dinámico (Swing):** Matriz de $15 \times 15$ celdas construida con `GridLayout`.
- **Generación Segura:** Las minas se distribuyen pseudoaleatoriamente tras realizar el primer clic, garantizando que el usuario no pierda en la jugada inicial.
- **Controles con Mouse:**
  - **Clic Izquierdo:** Descubre la celda seleccionada.
  - **Clic Derecho:** Alterna la colocación o remoción de banderas (`🚩`) en celdas sospechosas.
- **Expansión Recursiva:** Si una celda descubierta no tiene minas adyacentes (0 pistas), el método `liberarEspacio` propaga recursivamente la apertura de celdas vecinas.
- **Cálculo de Proximidad:** Determina en tiempo real el número de minas adyacentes a cada celda en un radio de 8 direcciones.
- **Detección de Fin de Partida:**
  - **Derrota:** Revela la ubicación de todas las minas (`💣`) y muestra un mensaje de derrota.
  - **Victoria:** Comprueba si se han descubierto todas las celdas libres del tablero.
- **Reinicio Automático:** Limpieza del tablero y restablecimiento del estado interno al culminar cada partida.

---

## 📂 Estructura del Proyecto

```text
BuscaMinas/
├── src/
│   ├── Principal.java       # Punto de entrada de la aplicación (método main)
│   ├── GUI.java             # Ventana, tablero, eventos de mouse y lógica visual
│   ├── Celda.java           # Modelo de datos para el estado individual de cada celda
│   └── ColeccionMinas.java  # Estructura para gestión y verificación de minas generadas
├── .gitignore               # Exclusión de binarios (.class), temporales y metadatos
└── README.md                # Documentación del proyecto
```

---

## 🛠️ Requisitos

- **Java Development Kit (JDK):** Versión 8 o superior (compatible con OpenJDK 11, 17, 21, 25).

---

## 🚀 Compilación y Ejecución

### Desde la Terminal (Linux / macOS / Windows)

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/leonel-pizzuti/BuscaMinas.git](https://github.com/leonel-pizzuti/BuscaMinas.git)
   cd BuscaMinas
   ```

2. **Compilar el código fuente:**
   ```bash
   javac -d bin src/*.java
   ```

3. **Ejecutar el juego:**
   ```bash
   java -cp bin Principal
   ```

---

## 👤 Autor

- **Leonel Pizzuti**
  - GitHub: [@leonel-pizzuti](https://github.com/leonel-pizzuti)
  - LinkedIn: [in/leonel-pizzuti](https://www.linkedin.com/in/leonel-pizzuti/)
