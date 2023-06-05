package generic

inline fun <reified T> T.toSuperString() {
  println("${T::class.java.name}: $this")
}

inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
  return this.any { it is T }
}

class TypeErase<T, R, B> {
}

class CageShadow<T : Animal> {
  fun <T : Animal> addAnimal(animal: T) {

  }
}


fun main() {
  val cage = CageShadow<GoldFish>()
  cage.addAnimal(GoldFish("금붕어"))
  cage.addAnimal(Carp("잉어"))
}

open class CageV1<T : Animal> {
  open fun addAnimal(animal: T) {

  }
}

class CageV2<T : Animal> : CageV1<T>() {
  override fun addAnimal(animal: T) {
    super.addAnimal(animal)
  }
}

class GoldFishCageV2 : CageV1<GoldFish>() {
  override fun addAnimal(animal: GoldFish) {
    super.addAnimal(animal)
  }
}
