package delegate

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


class DelegateProperty {
}