# Taller 3 - Programación Funcional: Manipulación de Trenes

Este proyecto implementa un sistema para realizar maniobras de trenes utilizando programación funcional en Scala. Permite aplicar movimientos a vagones entre diferentes trayectos y definir maniobras complejas para reorganizar trenes.

| #  | Nombre del Integrante | Codigo                    |                     |
|----|-----------------------|-------------------------|----
| 1  | Andrés Felipe Castrillón Martínez | 2380664  |
| 2  | Juan David Turriago Orozco | 2477182           |
| 3  | Bryan Steven Panesso Avila | 2380701            |                    |

## Estructura del Proyecto
.  
├── src  
│ └── main  
│ └── scala  
│ └── taller  
│ ├── package.scala  
│ ├── aplicarMovimiento.scala  
│ ├── aplicarMovimientos.scala  
│ └── definirManiobra.scala  
├── test  
│ └── scala  
│ └── taller  
│ └── ManiobrasTrenesTest.scala  
├── build.gradle  
├── scalastyle_config.xml  
└── settings.gradle


### Clases y Métodos

1.  **`aplicarMovimiento`**

    -   Aplica un movimiento único a un tren (`Uno(n)`  o  `Dos(n)`).

    -   Maneja movimientos positivos (mover vagones al trayecto) y negativos (devolver vagones al tren principal).

2.  **`aplicarMovimientos`**

    -   Ejecuta una lista de movimientos secuencialmente.

    -   Implementa dos versiones: recursiva con  `@tailrec`  y una con expresión  `for`.

3.  **`definirManiobra`**

    -   Genera una secuencia de movimientos para transformar un tren inicial en un tren objetivo.

    -   Utiliza un enfoque recursivo con backtracking para optimizar la ruta.


### Tests

Se incluyen pruebas para validar:

-   Movimientos básicos y secuencias complejas.

-   Escalabilidad con hasta 1000 vagones.

-   Validación de rendimiento y seguridad de pila (_stack safety_).



    test("Maniobra completa con 1000 elementos [performance]") {
      val tren = generarTren(1000)
      val movs = objDefinirManiobra.definirManiobra(tren, tren.reverse)
      val estados = objAplicarMovs.aplicarMovimientosConFor((tren, Nil, Nil), movs)
      assert(estados.last._1.size == 1000)
    }