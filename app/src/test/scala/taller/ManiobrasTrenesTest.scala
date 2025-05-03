package taller

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ManiobrasTrenesTest extends AnyFunSuite {

  private val objAplicarMov = new aplicarMovimiento()
  private val objAplicarMovs = new aplicarMovimientos()
  private val objDefinirManiobra = new definirManiobra()

  private def generarTren(tam: Int): Tren = (1 to tam).toList

  // ____________________________________________________________aplicarMovimiento/s____________________________________________________
  test("Aplicar movimiento Uno(2) a tren de 4 elementos") {
    val e1 = (List('a', 'b', 'c', 'd'), Nil, Nil)
    val e2 = objAplicarMov.aplicarMovimiento(e1, Uno(2))
    assert(e2 == (List('a', 'b'), List('c', 'd'), Nil))
  }

  test("Aplicar 3 movimientos secuenciales") {
    val estados = objAplicarMovs.aplicarMovimientosConFor(
      (List(1, 2, 3), Nil, Nil),
      List(Uno(1), Dos(2), Uno(-1))
    )
    assert(estados.length == 4)
  }

  test("Aplicar 10 movimientos secuenciales sobre 10 vagones") {
    val e = (List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'), List(), List()) // Estado inicial con 10 vagones en la primera lista
    val movimientos = List(
      Uno(1), Dos(1), Uno(-2), Dos(2), Uno(1), Dos(1), Uno(-3), Dos(2), Uno(1), Dos(1)
    ) // 10 movimientos

    val estados = objAplicarMovs.aplicarMovimientos(e, movimientos)

    assert(estados.length == 11)
  }

  test("Aplicar 100 movimientos secuenciales sobre 100 vagones") {
    // Estado inicial con 100 vagones en la primera lista
    val e = (List.range(1, 101).map(_.toChar), List(), List()) // Vagones representados por caracteres 'a' a 'j'...

    // Generamos 100 movimientos alternando Uno y Dos con valores aleatorios de 1 y -1
    val movimientos = (1 to 100).map {
      case i if i % 2 == 0 => Uno(scala.util.Random.nextInt(3) - 2) // Alternar entre Uno(1) y Uno(-2)
      case _ => Dos(scala.util.Random.nextInt(3) - 1) // Alternar entre Dos(1) y Dos(2)
    }.toList

    val estados = objAplicarMovs.aplicarMovimientos(e, movimientos)
    assert(estados.length == 101)
    assert(estados.head == (List.range(1, 101).map(_.toChar), List(), List()))

  }

  test("Aplicar 500 movimientos secuenciales sobre 500 vagones") {
    // Estado inicial con 500 vagones en la primera lista
    val e = (List.range(1, 501).map(_.toChar), List(), List()) // Vagones representados por caracteres 'a' a 'z', luego más caracteres...

    // Generamos 500 movimientos alternando Uno y Dos con valores aleatorios de 1 y -1
    val movimientos = (1 to 500).map {
      case i if i % 2 == 0 => Uno(scala.util.Random.nextInt(3) - 2) // Alterna entre Uno(1) y Uno(-2)
      case _ => Dos(scala.util.Random.nextInt(3) - 1) // Alterna entre Dos(1) y Dos(2)
    }.toList

    val estados = objAplicarMovs.aplicarMovimientos(e, movimientos)

    assert(estados.length == 501)

    assert(estados.head == (List.range(1, 501).map(_.toChar), List(), List()))

  }


  test("Aplicar 1000 movimientos secuenciales sobre 1000 vagones") {
    // Estado inicial con 1000 vagones en la primera lista
    val e = (List.range(1, 1001).map(_.toChar), List(), List()) // Vagones representados por caracteres 'a' a 'z', luego más caracteres...

    // Generamos 1000 movimientos alternando Uno y Dos con valores aleatorios de 1 y -1
    val movimientos = (1 to 1000).map {
      case i if i % 2 == 0 => Uno(scala.util.Random.nextInt(3) - 2) // Alterna entre Uno(1) y Uno(-2)
      case _ => Dos(scala.util.Random.nextInt(3) - 1) // Alterna entre Dos(1) y Dos(2)
    }.toList

    val estados = objAplicarMovs.aplicarMovimientos(e, movimientos)

    assert(estados.length == 1001)

    // Verificamos que el primer estado es el estado inicial
    assert(estados.head == (List.range(1, 1001).map(_.toChar), List(), List()))

  }

  test("Mover 10 vagones al trayecto Uno") {
    val tren = generarTren(10)
    val estadoFinal = objAplicarMov.aplicarMovimiento((tren, Nil, Nil), Uno(10))
    assert(estadoFinal._1.isEmpty && estadoFinal._2 == tren)
  }

  test("Mover 100 vagones al trayecto Uno") {
    val tren = generarTren(100)
    val estadoFinal = objAplicarMov.aplicarMovimiento((tren, Nil, Nil), Uno(100))
    assert(estadoFinal._1.isEmpty && estadoFinal._2 == tren)
  }

  test("Maniobra completa con 500 elementos [performance]") {
    val tren = generarTren(500)
    val movs = objDefinirManiobra.definirManiobra(tren, tren.reverse)
    val estados = objAplicarMovs.aplicarMovimientosConFor((tren, Nil, Nil), movs)
    assert(estados.last._1.size == 500)
  }
  test("Maniobra completa con 1000 elementos [performance]") {
    val tren = generarTren(1000)
    val movs = objDefinirManiobra.definirManiobra(tren, tren.reverse)
    val estados = objAplicarMovs.aplicarMovimientosConFor((tren, Nil, Nil), movs)
    assert(estados.last._1.size == 1000)
  }

  test("Mover 1000 elementos entre trayectos [stack safety]") {
    val tren = generarTren(1000)
    val movs = List.fill(10)(Uno(100)) ++ List.fill(10)(Uno(-100))
    val estados = objAplicarMovs.aplicarMovimientosConFor((tren, Nil, Nil), movs)
    assert(estados.last._1 == tren)
  }
  // ____________________________________________definirManiobra____________________________________________________
  test("Prueba de juguete: 10 vagones y 10 movimientos") {
    val trenInicial = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val trenObjetivo = List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val maniobra = new definirManiobra().definirManiobra(trenInicial, trenObjetivo)
    assert(maniobra.size <= 10)
  }

  test("Prueba pequeña: 100 vagones y movimientos") {
    val trenInicial = (1 to 100).toList
    val trenObjetivo = trenInicial.reverse
    val maniobra = new definirManiobra().definirManiobra(trenInicial, trenObjetivo)
    assert(maniobra.size <= 100)
  }

  test("Prueba mediana: 500 vagones y movimientos") {
    val trenInicial = (1 to 500).toList
    val trenObjetivo = trenInicial.reverse
    val maniobra = new definirManiobra().definirManiobra(trenInicial, trenObjetivo)
    assert(maniobra.size <= 500)
  }

  test("Prueba grande: 1000 vagones y movimientos") {
    val trenInicial = (1 to 1000).toList
    val trenObjetivo = trenInicial.reverse
    val maniobra = new definirManiobra().definirManiobra(trenInicial, trenObjetivo)
    assert(maniobra.size <= 1000)
  }
}