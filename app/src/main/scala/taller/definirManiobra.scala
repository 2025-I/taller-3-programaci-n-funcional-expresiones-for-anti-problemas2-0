package taller

import scala.annotation.tailrec

class definirManiobra {
  type Maniobra = List[Movimiento]

  def definirManiobra(trenInicial: Tren, trenObjetivo: Tren): Maniobra = {
    if (trenInicial == trenObjetivo) Nil
    else {
      @tailrec
      def procesarMovimientos(
                               trenPrincipal: Tren,
                               trenDestino: Tren,
                               trayectoUno: Tren,
                               trayectoDos: Tren,
                               movimientosAcumulados: List[Movimiento]
                             ): List[Movimiento] = {
        (trenPrincipal, trenDestino) match {
          case (Nil, Nil) => movimientosAcumulados.reverse
          case (vagonPrincipal :: restoPrincipal, vagonDestino :: restoDestino) if vagonPrincipal == vagonDestino =>
            procesarMovimientos(restoPrincipal, restoDestino, trayectoUno, trayectoDos, movimientosAcumulados)
          case _ =>
            val posiblesMovimientos = for {
              (movimiento, nuevoPrincipal, nuevoTrayectoUno, nuevoTrayectoDos) <- List(
                (Uno(1), trenPrincipal.dropRight(1), trenPrincipal.takeRight(1) ++ trayectoUno, trayectoDos),
                (Dos(1), trenPrincipal.dropRight(1), trayectoUno, trenPrincipal.takeRight(1) ++ trayectoDos)
              )
              if trenPrincipal.nonEmpty
            } yield (movimiento, nuevoPrincipal, nuevoTrayectoUno, nuevoTrayectoDos)

            val movimientoValido = posiblesMovimientos.find { case (_, nuevoPrincipal, _, _) =>
              nuevoPrincipal.nonEmpty && (trenDestino.startsWith(nuevoPrincipal.last :: Nil) || trenDestino.startsWith(nuevoPrincipal))
            }

            movimientoValido match {
              case Some((movimiento, nuevoPrincipal, nuevoTrayectoUno, nuevoTrayectoDos)) =>
                procesarMovimientos(nuevoPrincipal, trenDestino, nuevoTrayectoUno, nuevoTrayectoDos, movimiento :: movimientosAcumulados)
              case None =>
                val movimientosRevertidos = for {
                  (trayecto, tipoMovimiento) <- List((trayectoUno, Uno(-1)), (trayectoDos, Dos(-1)))
                  if trayecto.nonEmpty
                  vagonRevertido = trayecto.head
                  if trenDestino.startsWith(vagonRevertido :: Nil)
                } yield (tipoMovimiento, trayecto, trayecto.tail)

                movimientosRevertidos.headOption match {
                  case Some((movimiento, trayecto, nuevoTrayecto)) =>
                    val (nuevoTrayectoUno, nuevoTrayectoDos) = if (movimiento == Uno(-1)) (nuevoTrayecto, trayectoDos) else (trayectoUno, nuevoTrayecto)
                    procesarMovimientos(trenPrincipal :+ trayecto.head, trenDestino.tail, nuevoTrayectoUno, nuevoTrayectoDos, movimiento :: movimientosAcumulados)
                  case None =>
                    movimientosAcumulados.reverse
                }
            }
        }
      }

      procesarMovimientos(trenInicial, trenObjetivo, Nil, Nil, Nil)
    }
  }
}