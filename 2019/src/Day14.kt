/*
object Day14 {

    fun part1(inputMap: Map<String, Pair<Int, List<Pair<String, Int>>>>) {
        val fuel = inputMap["FUEL"]
        var ore = 0
        val restList: MutableMap<String, Int> = mutableMapOf()

    }

    fun test1() {
        val input = "10 ORE => 10 A\n" +
                "1 ORE => 1 B\n" +
                "7 A, 1 B => 1 C\n" +
                "7 A, 1 C => 1 D\n" +
                "7 A, 1 D => 1 E\n" +
                "7 A, 1 E => 1 FUEL"

        val map = createInputMap(input)
        println(map)
    }

    private fun createInputMap(input: String): Map<String, Pair<Int, List<Pair<String, Int>>>> {
        val lines = input.split("\n")
        val outputMap: MutableMap<String, Pair<Int, List<Pair<String, Int>>>> = mutableMapOf()
        for (line in lines) {
            val parts = line.split(" => ")
            val ingredients = parts[0].split(", ")
            val ingredientList: MutableList<Pair<String, Int>> = mutableListOf()
            for (ing in ingredients) {
                val pt = ing.split(" ")
                ingredientList.add(Pair(pt[1], pt[0].toInt()))
            }
            val solution = parts[1].split(" ")
            outputMap[solution[1]] = Pair(solution[0].toInt(), ingredientList)
        }
        return outputMap
    }

    class Test(val initialMap: Map<String, Pair<Int, List<Pair<String, Int>>>>) {
        val restMap: MutableMap<String, Int> = mutableMapOf()
        var ore: Int = 0

        fun doIt(material: String, amount: Int) {
            if (material == "ORE") {
                ore += amount
            }
            val reaction = initialMap.getValue(material)
            for (neededMat in reaction.second) {

            }
        }
    }
}

fun main(args: Array<String>) {
    Day14.test1()
}*/
