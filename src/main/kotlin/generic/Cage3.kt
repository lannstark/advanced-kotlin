package generic

fun main() {
  val fishCage = Cage3<Fish>()
  val animalCage: Cage3<Animal> = fishCage
}

class Cage3<out T> {
  private val animals: MutableList<T> = mutableListOf()

  fun getFirst(): T {
    return this.animals.first()
  }

  fun getAll(): List<T> {
    return this.animals
  }
}