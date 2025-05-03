package taller

import scala.annotation.tailrec

class aplicarMovimientos {

  val mover = new aplicarMovimiento
  import mover._

  def aplicarMovimientos(e: Estado, mows: List[Movimiento]): List[Estado] = {
    @tailrec
    def aplicarMovimientosAux(mows: List[Movimiento], acc: List[Estado], estadoActual: Estado): List[Estado] = {
      mows match {
        case Nil => acc.reverse
        case m :: ms =>
          val nextEstado = aplicarMovimiento(estadoActual, m)
          aplicarMovimientosAux(ms, nextEstado :: acc, nextEstado)
      }
    }
    aplicarMovimientosAux(mows, List(e), e)  // Iniciamos con el estado inicial
  }

  // Método con expresión for para iterar y filtrar
  import scala.collection.mutable.ListBuffer

  def aplicarMovimientosConFor(e: Estado, movs: List[Movimiento]): List[Estado] = {
    val bufferEstados = ListBuffer(e) // Acumulador mutable interno

    for (mov <- movs) {
      if (esMovimientoValido(bufferEstados.head, mov)) {
        val nuevoEstado = aplicarMovimiento(bufferEstados.head, mov)
        bufferEstados.prepend(nuevoEstado) // Agregamos al inicio (equivale a ::)
      }
    }

    bufferEstados.toList.reverse // Convertimos a List inmutable y ordenamos
  }

  // Método auxiliar para validar si el movimiento es válido
  def esMovimientoValido(e: Estado, m: Movimiento): Boolean = m match {
    case Uno(n) if n > 0  => e._1.nonEmpty    // Requiere vagones en principal para mover a uno
    case Uno(n) if n < 0  => e._2.nonEmpty    // Requiere vagones en uno para mover a principal
    case Dos(n) if n > 0  => e._1.nonEmpty    // Requiere vagones en principal para mover a dos
    case Dos(n) if n < 0  => e._3.nonEmpty    // Requiere vagones en dos para mover a principal
    case _ => false                           // n=0 o movimiento desconocido
  }
}
