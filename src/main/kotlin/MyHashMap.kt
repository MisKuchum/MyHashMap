class MyHashMap<K, V> (
    private var capacity : Int = 16,
    private val loadFactor : Double = 0.75
){
    private class Node<K, V> (
        private val hash : Int,
        private val key : K?,
        private val value : V,
        private var next : Node<K, V>? = null
    ) {
        fun getHash() : Int = this.hash
        fun getKey() : K? = this.key
        fun getValue() : V = this.value
        fun getNext() : Node<K, V>? = this.next
        fun setNext(node : Node<K, V>?) {
            this.next = node
        }
    }

    private var table = arrayOfNulls<Node<K, V>?>(capacity)

    private var size = 0

    private fun resize() {
        val prevTable = table
        var nextNode : Node<K, V>
        capacity = table.size * 2
        size = 0
        table = arrayOfNulls<Node<K, V>?>(capacity)

        for (node in prevTable) {
            if (node != null) {
                put(node.getKey(), node.getValue())
                nextNode = node.getNext() ?: continue
                put(nextNode.getKey(), nextNode.getValue())
                while (nextNode.getNext() != null) {
                    put(nextNode.getKey(), nextNode.getValue())
                    nextNode = nextNode.getNext()!!
                }
            }
        }
    }

    fun getSize() : Int = this.size

    fun put(key : K?, value : V) {
        if (getSize().toDouble() / capacity.toDouble() >= loadFactor)
            resize()

        var index = 0
        val keyHash = key.hashCode()
        var isNullOrEqual = false
        var nextNode : Node<K, V>? = null

        if (key != null)
            index = keyHash.and(table.size - 1)

        var currentNode = table[index]

        if (currentNode == null) {
            isNullOrEqual = true
            size++
        } else {
            while (true) {
                if (keyHash == currentNode!!.getHash()) {
                    if (key?.equals(currentNode.getKey()) == true) {
                        isNullOrEqual = true
                        nextNode = currentNode.getNext()
                        break
                    }
                }

                if (currentNode.getNext() != null)
                    currentNode = currentNode.getNext()
                else
                    break
            }
        }

        if (isNullOrEqual) {
            table[index] = Node<K, V>(hash = keyHash, key = key, value = value, next = nextNode)
        }
        else {
            currentNode?.setNext(Node<K, V>(hash = keyHash, key = key, value = value))
            size++
        }

        // println("key: $key | hash: $keyHash | index: $index")
    }

    fun get(key : K) : V? {
        val keyHash = key.hashCode()
        val index = keyHash.and(table.size - 1)
        var currentNode = table[index] ?: return null

        while (true) {
            if (keyHash == currentNode.getHash())
                if (key?.equals(currentNode.getKey()) == true)
                    return currentNode.getValue()

            if (currentNode.getNext() != null)
                currentNode = currentNode.getNext()!!
            else
                break
        }

        return null
    }

    fun remove(key : K) {
        val keyHash = key.hashCode()
        val index = keyHash.and(table.size - 1)
        var currentNode = table[index] ?: return
        var prevNode = currentNode

        while (true) {
            if (keyHash == currentNode.getHash())
                if (key?.equals(currentNode.getKey()) == true) {
                    if (currentNode == prevNode)
                        table[index] = currentNode.getNext()
                    else {
                        if (currentNode.getNext() != null)
                            prevNode.setNext(currentNode.getNext()!!)
                        else
                            prevNode.setNext(null)
                    }
                    size--
                    break
                }

            if (currentNode.getNext() != null) {
                prevNode = currentNode
                currentNode = currentNode.getNext()!!
            } else
                break
        }
    }

//    fun size() : Int {
//        var count = 0
//        var nextNode : Node<K, V>?
//        for (node in table) {
//            if (node != null) {
//                count++
//                nextNode = node.getNext() ?: continue
//                while (nextNode?.getNext() != null) {
//                    nextNode = nextNode.getNext()!!
//                    count++
//                }
//            }
//        }
//
//        return count
//    }

    fun clear() {
        for (i in table.indices)
            table[i] = null
        size = 0
    }
}