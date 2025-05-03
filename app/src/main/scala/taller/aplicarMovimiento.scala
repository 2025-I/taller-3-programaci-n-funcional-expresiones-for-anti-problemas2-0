package taller

class aplicarMovimiento {
  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = {
    val (main, uno, dos) = e
    m match {
      case Uno(n) if n > 0 =>
        val splitPoint = main.length - n
        val (newMain, moved) = main.splitAt(splitPoint)
        (newMain, moved ++ uno, dos)

      case Uno(n) if n < 0 =>
        val takeN = math.min(-n, uno.length)
        val (moved, newUno) = uno.splitAt(takeN)
        (main ++ moved, newUno, dos)

      case Dos(n) if n > 0 =>
        val splitPoint = main.length - n
        val (newMain, moved) = main.splitAt(splitPoint)
        (newMain, uno, moved ++ dos)

      case Dos(n) if n < 0 =>
        val takeN = math.min(-n, dos.length)
        val (moved, newDos) = dos.splitAt(takeN)
        (main ++ moved, uno, newDos)

      case _ => e
    }

  }
}
