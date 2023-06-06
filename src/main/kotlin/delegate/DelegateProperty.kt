package delegate

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// by lazy()
class Person3 {
  val name: String by lazy {
    Thread.sleep(2_000)
    "김수한무"
  }
}

class LazyInitProperty<T>(val init: () -> T) {
  private var _value: T? = null
  private val value: T
    get() {
      if (_value == null) {
        this._value = init()
      }
      return _value!!
    }

  operator fun getValue(thisRef: Any, property: KProperty<*>): T {
    return value
  }
}

class Person4 {
  var age: Int by Delegates.observable(20) { _, oldValue, newValue ->
    println("이전 값 : ${oldValue} 새로운 값 : ${newValue}")
  }
}

fun main() {
  val p = Person4()
  p.age = 30
  p.age = 30
}