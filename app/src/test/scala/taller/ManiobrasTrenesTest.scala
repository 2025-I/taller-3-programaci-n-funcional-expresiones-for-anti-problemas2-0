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
}