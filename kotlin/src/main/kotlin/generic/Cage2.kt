package generic

class Cage2<T> {
  private val animals: MutableList<T> = mutableListOf()

  fun getFirst(): T {
    return animals.first()
  }

  fun put(animal: T) {
    this.animals.add(animal)
  }

  fun moveFrom(cage: Cage2<T>) {
    this.animals.addAll(cage.animals)
  }
}