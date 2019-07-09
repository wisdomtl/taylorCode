package test.taylor.com.taylorcode.kotlin.delegate

class Feather(val accessory: Accessory) : Accessory by accessory {
    override fun name(): String = "Feather" + accessory.name()
    override fun cost(): Int = 90 + accessory.cost()
}