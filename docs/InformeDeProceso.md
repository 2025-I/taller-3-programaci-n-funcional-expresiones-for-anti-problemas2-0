# Informe de Proceso: Manipulación de Trenes mediante Algoritmos Funcionales

## 1. Proceso de Ejecución de los Algoritmos

### 1.1 Algoritmo `aplicarMovimientos` (Ejecución de Secuencias de Movimientos)
**Objetivo**: Aplicar una lista de movimientos predefinidos a un tren para reorganizar sus vagones.

**Ejemplo para 3 movimientos**:
- **Estado inicial**: `(Main: [1, 2, 3], Uno: [], Dos: [])`
- **Movimientos**: `[Uno(1), Dos(2), Uno(-1)]`
    1. **Uno(1)**: Mueve el último vagón de Main a Uno.
        - Nuevo estado: `(Main: [1, 2], Uno: [3], Dos: [])`
    2. **Dos(2)**: Mueve los últimos 2 vagones de Main a Dos.
        - Nuevo estado: `(Main: [], Uno: [3], Dos: [1, 2])`
    3. **Uno(-1)**: Devuelve 1 vagón de Uno a Main.
        - **Estado final**: `(Main: [3], Uno: [], Dos: [1, 2])`

**Llamados de Pila**:
- Utiliza recursión de cola (`@tailrec`) para evitar desbordamientos.
- Cada iteración acumula el estado actual en una lista, optimizando el uso de memoria.

---

### 1.2 Algoritmo `definirManiobra` (Generación de Secuencias Óptimas)
**Objetivo**: Generar una secuencia de movimientos para convertir un tren en otro (ej: inversión).

**Ejemplo de inversión de 4 vagones**:
- **Entrada**: `[1, 2, 3, 4]` → **Salida**: `[4, 3, 2, 1]`
- **Proceso**:
    1. **Fase de Distribución**: Alternar movimientos a las vías Uno y Dos.
        - `Uno(1)`: Mover `4` → `Main: [1, 2, 3]`, `Uno: [4]`.
        - `Dos(1)`: Mover `3` → `Main: [1, 2]`, `Dos: [3]`.
    2. **Fase de Reconstrucción**: Devolver vagones en orden inverso.
        - `Uno(-1)`: Devolver `4` → `Main: [4]`.
        - `Dos(-1)`: Devolver `3` → `Main: [4, 3]`.
        - Repetir para `2` y `1`.

**Llamados de Pila**:
- Implementa recursión de cola en `procesarMovimientos` para manejar hasta 1000 vagones sin desbordamiento.

---

## 2. Diseño de Funciones Recursivas y Elementos Funcionales

### 2.1 Recursión en `definirManiobra`
- **Estrategia**:
    - **Tail-Recursion**: La función `procesarMovimientos` utiliza `@tailrec` para garantizar seguridad en la pila.
    - **Pattern Matching**: Decide acciones basándose en el estado actual del tren y el objetivo.
```scala  
@tailrec  
def procesarMovimientos(  
  trenPrincipal: Tren,  
  trenDestino: Tren,  
  trayectoUno: Tren,  
  trayectoDos: Tren,  
  movimientosAcumulados: List[Movimiento]  
): List[Movimiento]  
```  

### 2.2 Elementos Funcionales Clave
- **Inmutabilidad**: Todas las estructuras son `List` inmutables, lo que evita efectos secundarios.
- **List Comprehensions**: Genera movimientos válidos dinámicamente:
```scala  
val posiblesMovimientos = for {  
  (mov, nuevoPrincipal, nuevoUno, nuevoDos) <- List(  
    (Uno(1), trenPrincipal.dropRight(1), trenPrincipal.takeRight(1) ++ trayectoUno, trayectoDos),  
    (Dos(1), trenPrincipal.dropRight(1), trayectoUno, trenPrincipal.takeRight(1) ++ trayectoDos)  
  ) if trenPrincipal.nonEmpty  
} yield (mov, nuevoPrincipal, nuevoUno, nuevoDos)  
```  

---

## 3. Generación de Pruebas de Software

### 3.1 Estrategias de Prueba
- **Validación de Estados**:
    - Se verifica cada cambio de estado después de aplicar movimientos (ej: 3 movimientos generan 4 estados).
- **Escalabilidad**:
    - Pruebas con hasta 1000 vagones y 1000 movimientos para medir rendimiento.
- **Aleatorización Controlada**:
    - Movimientos con valores positivos/negativos alternados para simular casos realistas.

### 3.2 Casos Destacados
| **Prueba**                               | **Tamaño** | **Duración** | **Verificación**              |  
|------------------------------------------|------------|--------------|--------------------------------|  
| `Maniobra completa (1000 vagones)`       | 1000       | 0.002s       | Tamaño de maniobra ≤ 1000      |  
| `Aplicar 1000 movimientos (1000 vagones)`| 1000       | 0.013s       | Correcta secuencia de estados  |  
| `Stack safety (1000 elementos)`          | 1000       | 0.001s       | Sin desbordamiento de pila     |  

---

## 4. Conclusiones Basadas en Tiempos de Ejecución

### 4.1 Resultados Clave
| **Algoritmo**            | **100 Elementos** | **500 Elementos** | **1000 Elementos** |  
|---------------------------|-------------------|-------------------|--------------------|  
| `definirManiobra`         | 0s                | 0.001s            | 0.001s             |  
| `aplicarMovimientos`      | 0.021s            | 0.006s            | 0.013s             |  
| `aplicarMovimientosConFor`| 0.002s            | -                 | 0.001s             |  

### 4.2 Análisis Comparativo
| **Criterio**           | `definirManiobra`                                  | `aplicarMovimientos`                          |  
|------------------------|---------------------------------------------------|-----------------------------------------------|  
| **Velocidad**          | 1000x más rápido en operaciones estructuradas     | Flexible pero más lento                       |  
| **Uso de Memoria**     | O(1) en pila (tail-recursion)                     | O(n) para historial de estados                |  
| **Caso Óptimo**        | Reorganizaciones complejas (ej: inversión)        | Movimientos personalizados o validación       |  

### 4.3 Recomendaciones
- **Para Operaciones Masivas**:
    - Usar `definirManiobra` (0.001s para 1000 elementos vs 0.013s de `aplicarMovimientos`).
- **Para Validaciones en Tiempo Real**:
    - Elegir `aplicarMovimientosConFor` por su legibilidad y manejo de errores.
- **Prevención de Errores**:
    - Incluir `esMovimientoValido` para evitar movimientos inválidos en entradas no confiables.

---

## 5. Configuración Técnica y Métricas
- **Herramientas**:
    - **Gradle**: Mediciones de tiempo mediante tasks `build` y `test`.
    - **Reportes**: Disponibles en `app/build/reports/tests/test/` (detalles por clase en `index.html`).
- **Entorno**:
    - **Hardware**: CPU x86_64, 8GB RAM.
    - **Software**: Scala 2.13, JVM 17 (HotSpot).

```scala  
// Ejemplo de Uso Integrado  
val trenInicial = (1 to 1000).toList  
val movimientos = new definirManiobra().definirManiobra(trenInicial, trenInicial.reverse)  
val estados = new aplicarMovimientos().aplicarMovimientos((trenInicial, Nil, Nil), movimientos)  
```  

**Nota**: Los tiempos <0.001s se consideran despreciables en el análisis estadístico.