# Programación Concurrente y Paralela

Este proyecto está diseñado para explorar los fundamentos y técnicas avanzadas de la programación concurrente y paralela utilizando Java y C. A través de una serie de entregas, los participantes tendrán la oportunidad de aprender sobre la gestión de hebras (hilos), atomicidad, visibilidad, sincronización, patrones de diseño concurrente y paralelo, y optimización de rendimiento para sistemas multicore y distribuidos.

## Entregas

### Entrega 1
- **Funcionamiento Básico**: Comprender el funcionamiento básico de las distintas hebras.

### Entrega 2
- Resolver un problema de manera repetida utilizando diferentes estrategias de distribución de carga de trabajo entre hebras:
  - **Distribución Cíclica**: Uso de un vector de hilos.
  - **Distribución por Bloques**: Uso de un vector de hilos.

### Entrega 3
- Problemas de **Atomicidad y Visibilidad**: Comprender cuándo utilizar cada estrategia de distribución en función de la cantidad de hebras.
- Estrategias de distribución para 1, 4 y 8 hebras.

### Entrega 4
- **Acumulaciones - Problema de Reducción**: Mejores prácticas para acumular resultados en la hebra y compartir la información de manera eficiente.

### Entrega 5
- Uso de hebras en interfaces gráficas: `InvokeLater` y `InvokeAndWait`.
- Implementación correcta de interfaces gráficas con `LinkedBlockingQueue` y `ArrayList`.

### Entrega 6
- **Colecciones - Sincronizadas y Escalables**: Exploración de diferentes tipos de colecciones y sus propiedades de sincronización.
- Uso de `HashMap`, `Hashtable`, `ConcurrentHashMap` y técnicas de sincronización fina con operaciones atómicas.

### Entrega 7
- Implementación del patrón Productor-Consumidor con gestión propia de hebras.
- Uso de `ThreadPool` para gestionar hebras y futuros de forma eficiente.

### Entrega 8 - MPI
- Comunicaciones punto a punto simples y con vectores.
- Cálculo de ancho de banda en comunicaciones punto a punto.

### Entrega 9
- Implementación paralela con mensajes envenenados utilizando TAGs.
- Distribución de trabajo en un entorno distribuido con procesos coordinadores y trabajadores.

### Entrega 10
- Ejercicios de comparación entre comunicaciones colectivas y punto a punto.
- Diversas estrategias de suma y reducción de datos en un entorno distribuido.

## Conclusión
A lo largo de las entregas, los participantes aprenderán cuándo es preferible un enfoque secuencial frente a uno paralelo o concurrente, así como las mejores prácticas para el uso de clases atómicas y estructuras de datos sincronizadas para maximizar la eficiencia en tareas de programación concurrente y paralela.
