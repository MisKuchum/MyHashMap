fun main() {
    val map = MyHashMap<String, Int>()

    map.put("key1", 1)
    map.put("key2", 2)
    map.put("key3", 3)
    map.put("key4", 4)
    map.put("key5", 5)
    map.put("key6", 6)
    map.put("Test", 7)
    map.put("MyKey", 8)
    map.put("Russia", 9)
    map.put("SomeWord", 10)
    map.put("Misha", 11)
    map.put("Kotlin", 12)
    map.put("Hello world!", 13)
    map.put("SomeKeyV1", 14)
    map.put("key1", 15)

    println("size = ${map.getSize()}")

    println(map.get("Test"))
    map.remove("Test")
    println("size = ${map.getSize()}")
    println(map.get("Test"))

    map.clear()
    println("size = ${map.getSize()}")
}