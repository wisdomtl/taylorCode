package test.taylor.com.taylorcode.kotlin.delegate

class Gold(val accessory: Accessory) : Accessory by accessory {
    override fun name(): String = "Gold" + accessory.name()
    override fun cost(): Int = 300 + accessory.cost()
}