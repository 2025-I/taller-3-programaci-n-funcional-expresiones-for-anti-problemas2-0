
# Informe de Casos de Prueba

## **1. Pruebas para `aplicarMovimiento/s`**
### **Caso 1: Movimiento Simple**
- **Nombre**: `Aplicar movimiento Uno(2) a tren de 4 elementos`
- **Objetivo**: Verificar que se mueven 2 vagones de Main a Uno.
- **Entrada**:
  ```scala  
  Estado inicial: (Main = ['a', 'b', 'c', 'd'], Uno = [], Dos = [])  
  Movimiento: Uno(2)  
  ```  
- **Resultado Esperado**:
  ```scala  
  (Main = ['a', 'b'], Uno = ['c', 'd'], Dos = [])  
  ```  
- **Resultado Obtenido**: ✅ Pasó en **0.001s**.

---

### **Caso 2: Secuencia de Movimientos**
- **Nombre**: `Aplicar 3 movimientos secuenciales`
- **Objetivo**: Validar la ejecución de múltiples movimientos.
- **Entrada**:
  ```scala  
  Estado inicial: (Main = [1, 2, 3], Uno = [], Dos = [])  
  Movimientos: [Uno(1), Dos(2), Uno(-1)]  
  ```  
- **Resultado Esperado**: 4 estados generados (inicial + 3 cambios).
- **Resultado Obtenido**: ✅ Pasó en **0.002s**.

---

### **Caso 3: Escala Pequeña**
- **Nombre**: `Aplicar 10 movimientos sobre 10 vagones`
- **Objetivo**: Probar la capacidad de manejar entradas pequeñas.
- **Entrada**:
  ```scala  
  Tren inicial: List('a'..'j') (10 vagones)  
  Movimientos: 10 aleatorios (Uno/Dos con n ±1, ±2, ±3).  
  ```  
- **Resultado Esperado**: 11 estados generados.
- **Resultado Obtenido**: ✅ Pasó en **0s**.

---

### **Caso 4: Escala Media**
- **Nombre**: `Aplicar 500 movimientos sobre 500 vagones`
- **Objetivo**: Evaluar rendimiento en operaciones medianas.
- **Entrada**:
  ```scala  
  Tren inicial: List.range(1, 501).map(_.toChar)  
  Movimientos: 500 aleatorios (alternando Uno/Dos).  
  ```  
- **Resultado Esperado**: 501 estados generados.
- **Resultado Obtenido**: ✅ Pasó en **0.006s**.

---

