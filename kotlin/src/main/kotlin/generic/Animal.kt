package generic

abstract class Animal(
  val name: String,
)

abstract class Fish(name: String) : Animal(name)

// 금붕어
class GoldFish(name: String) : Fish(name)

// 잉어
class Carp(name: String) : Fish(name)