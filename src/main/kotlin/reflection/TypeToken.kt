package reflection

import generic.*
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.cast

fun main() {
//  val cage = Cage()
//  cage.put(Carp("잉어"))
//  cage.getFirst() as Carp // 위험!
//
//  val typeSafeCage = TypeSafeCage()
//  typeSafeCage.putOne(Carp("잉어"))
//  val one = typeSafeCage.getOne<Carp>()

  val superTypeToken1 = object : SuperTypeToken<List<GoldFish>>() {}
  val superTypeToken2 = object : SuperTypeToken<List<GoldFish>>() {}
  val superTypeToken3 = object : SuperTypeToken<List<Carp>>() {}
//  println(superTypeToken2.equals(superTypeToken1))
//  println(superTypeToken3.equals(superTypeToken1))

  val superTypeSafeCage = SuperTypeSafeCage()
  superTypeSafeCage.putOne(superTypeToken2, listOf(GoldFish("금붕어1"), GoldFish("금붕어2")))
  val result = superTypeSafeCage.getOne(superTypeToken2)
  println(result)
}

class TypeSafeCage {
  private val animals: MutableMap<KClass<*>, Animal> = mutableMapOf()

  fun <T : Animal> getOne(type: KClass<T>): T {
    return type.cast(animals[type])
  }

  fun <T : Animal> putOne(type: KClass<T>, animal: T) {
    animals[type] = type.cast(animal)
  }

  inline fun <reified T : Animal> getOne(): T {
    return this.getOne(T::class)
  }

  inline fun <reified T : Animal> putOne(animal: T) {
    this.putOne(T::class, animal)
  }
}

class SuperTypeSafeCage {
  private val animals: MutableMap<SuperTypeToken<*>, Any> = mutableMapOf()

  fun <T : Any> getOne(token: SuperTypeToken<T>): T {
    return this.animals[token] as T
  }

  fun <T : Any> putOne(token: SuperTypeToken<T>, animal: T) {
    animals[token] = animal
  }
}


// SuperTypeToken을 구현한 클래스가 인스턴스화 되자마자
// T 정보를 내부 변수에 저장해버린다.
// T <- List<Int>
abstract class SuperTypeToken<T> {
  val type: KType = this::class.supertypes[0].arguments[0].type!!

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    other as SuperTypeToken<*>
    if (type != other.type) return false
    return true
  }

  override fun hashCode(): Int {
    return type.hashCode()
  }
}

