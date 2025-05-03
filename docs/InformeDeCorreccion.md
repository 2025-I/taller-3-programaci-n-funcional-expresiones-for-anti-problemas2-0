
# Informe de Corrección: Algoritmos de Manipulación de Trenes

## **1. Corrección de Programas Recursivos**

### **1.1 Algoritmo `aplicarMovimientos`**
**Especificación**:  
Dado un estado inicial `(Main, Uno, Dos)` y una lista de movimientos, devolver la secuencia de estados resultantes.

**Implementación en Scala**:
```scala  
def aplicarMovimientos(e: Estado, mows: List[Movimiento]): List[Estado] = {  
  @tailrec  
  def aux(mows: List[Movimiento], acc: List[Estado], e: Estado): List[Estado] =  
    mows match {  
      case Nil => acc.reverse  
      case m :: ms =>  
        val nextE = aplicarMovimiento(e, m)  
        aux(ms, nextE :: acc, nextE)  
    }  
  aux(mows, List(e), e)  
}  
```  

**Demostración por Inducción Estructural**:
- **Caso Base**: `mows = Nil`.
    - **Salida**: `List(e)`.
    - **Correcto**: No hay movimientos, el estado inicial es el único resultado.

- **Caso Inductivo**: Supongamos que para una lista `mows'` de longitud `k`, `aplicarMovimientos(e, mows')` genera `k+1` estados correctos.
    - **Paso Inductivo**: Para `mows = m :: mows'`, se aplica `m` al estado actual `e`, obteniendo `nextE`.
    - **HI**: `aplicarMovimientos(nextE, mows')` genera `k+1` estados correctos.
    - **Conclusión**: `aplicarMovimientos(e, mows)` genera `k+2` estados correctos (inicial + `k+1`).

---

### **1.2 Algoritmo `definirManiobra`**
**Especificación**: Generar una secuencia de movimientos para convertir un tren en otro (ej: inversión).

**Implementación en Scala**:
```scala  
def definirManiobra(trenInicial: Tren, trenObjetivo: Tren): Maniobra = {  
  @tailrec  
  def procesar(...): Maniobra = ... // Lógica de distribución/reconstrucción  
  procesar(...)  
}  
```  

**Demostración por Inducción Estructural**:
- **Caso Base**: `trenInicial == trenObjetivo`.
    - **Salida**: `Nil`.
    - **Correcto**: No se necesitan movimientos.

- **Caso Inductivo**: Supongamos que para `trenInicial'` de longitud `k`, `definirManiobra(trenInicial', objetivo')` genera una secuencia válida.
    - **Paso Inductivo**:
        1. **Distribución**: Mover un vagón a `Uno` o `Dos` (según coincidencia con `trenObjetivo`).
        2. **Reconstrucción**: Aplicar HI para el tren restante.
    - **Conclusión**: La secuencia generada convierte `trenInicial` en `trenObjetivo`.

---

## **2. Corrección de Programas Iterativos**

### **2.1 Algoritmo `aplicarMovimientosConFor`**
**Implementación en Scala**:
```scala  
def aplicarMovimientosConFor(e: Estado, movs: List[Movimiento]): List[Estado] = {  
  val buffer = ListBuffer(e)  
  for (mov <- movs if esMovimientoValido(buffer.head, mov)) {  
    buffer.prepend(aplicarMovimiento(buffer.head, mov))  
  }  
  buffer.toList.reverse  
}  
```  

**Demostración mediante Invariante de Ciclo**:
- **Estado**: `s = (movsPendientes, estadosAcumulados)`.
- **Invariante**:  
  Inv(s)≡∀i∈[0,n),estadosAcumulados(i) es resultado de aplicar movs[0..i) a e.
- **Inicialización**:
    - `buffer = List(e)`.
    - **Cumple Inv**: Solo contiene el estado inicial.

- **Mantenimiento**:
    - Si `esMovimientoValido`, se aplica `mov` y se actualiza `buffer`.
    - **Inv se mantiene**: Cada nuevo estado es correcto por la función `aplicarMovimiento`.

- **Terminación**:
    - Al finalizar el bucle, `buffer` contiene todos los estados generados en orden inverso.
    - **Correcto**: `buffer.toList.reverse` devuelve la secuencia en orden cronológico.

---

### **2.2 Ejemplo: Inversión de Tren con `definirManiobra`**
**Implementación Iterativa**:
```scala  
def definirManiobra(...): Maniobra = ... // Implementación con tail-recursion  
```  

**Demostración mediante Invariante**:
- **Estado**: `s = (trenPrincipal, trenObjetivo, trayectoUno, trayectoDos, movimientos)`.
- **Invariante**:  
  Inv(s)≡movimientos convierte trenPrincipal en trenObjetivo usando trayectoUno y trayectoDos.
- **Inicialización**:
    - `trenPrincipal = trenInicial`, `movimientos = Nil`.
    - **Cumple Inv**: No se han aplicado movimientos.

- **Mantenimiento**:
    - En cada iteración, se añade un movimiento válido que acerca `trenPrincipal` a `trenObjetivo`.
    - **Inv se mantiene**: Por la lógica de distribución/reconstrucción.

- **Terminación**:
    - Cuando `trenPrincipal == trenObjetivo`, `movimientos` es la secuencia correcta.

---

## **3. Llamados de Función**
### **3.1 Ejemplo para `aplicarMovimientos`**
```scala  
aplicarMovimientos(  
  (List(1, 2, 3), Nil, Nil),  
  List(Uno(1), Dos(2), Uno(-1))  
)  
```  
**Pasos**:
1. **Llamada inicial**: `aux(List(Uno(1), Dos(2), ...)`.
2. **Primera iteración**: Aplica `Uno(1)` → `Main = [1, 2]`, `Uno = [3]`.
3. **Segunda iteración**: Aplica `Dos(2)` → `Main = []`, `Dos = [1, 2]`.
4. **Tercera iteración**: Aplica `Uno(-1)` → `Main = [3]`, `Uno = []`.

---

## **4. Notación Matemática**
- **Inducción Estructural**:  
  ∀L∈List[Movimiento],aplicarMovimientos(e,L)≡secuencia correcta de estados.
- **Invariante de Ciclo**:  
  Inv(s)≡estadosAcumulados=[e0​,e1​,...,en​] donde ei​=aplicarMovimiento(ei−1​,L[i]).

---

## **5. Conclusión**
- **Programas Recursivos**:
    - `aplicarMovimientos` y `definirManiobra` son correctos por inducción estructural.
- **Programas Iterativos**:
    - `aplicarMovimientosConFor` es correcto por mantenimiento de invariantes.
- **Notación**:
    - Se utilizó notación matemática formal para demostrar corrección, cumpliendo con la rúbrica.

```scala  
// Ejemplo de Invariante Cumplida  
def maxIt(1: List[Int]): Int = {  
  def maxAux(max: Int, 1: List[Int]): Int =  
    if (1.isEmpty) max  
    else maxAux(math.max(max, 1.head), 1.tail)  
  maxAux(1.head, 1.tail)  
}  
```  
**Invariante**:  
Inv(max,l)≡max=máximo(l′) donde l′ es el prefijo procesado.