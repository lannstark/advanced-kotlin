package extra

import kotlin.system.measureTimeMillis

class Lec24 {
}

fun main() {
  val result: Result<Int> = runCatching { 1 / 0 }
}

fun acceptOnlyTwo(num: Int) {
  require(num == 2) { "2만 허용!" }

}

class Person {
  val status: PersonStatus = PersonStatus.PLAYING

  fun sleep() {
    check(this.status == PersonStatus.PLAYING) { "에러 메시지!" }
  }

  enum class PersonStatus {
    PLAYING, SLEEPING
  }
}