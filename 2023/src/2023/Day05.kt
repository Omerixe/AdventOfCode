package `2023`

import java.io.File

fun main() {
    //val inputs = File(ClassLoader.getSystemResource("2023/dummy05.txt").toURI()).readLines()

    val inputs = File(ClassLoader.getSystemResource("2023/input05.txt").toURI()).readLines()
    Day05.part1(inputs)
    Day05.part2(inputs)
}

object Day05 {
    fun part1(inputs: List<String>) {
        // ATTENTION: USE LONG
        val seeds = inputs.first().replace("seeds: ", "").split(" ").map { it.toLong() }

        val seedToSoil = mutableMapOf<LongRange, Long>()
        val soilToFertilizer = mutableMapOf<LongRange, Long>()
        val fertilizerToWater = mutableMapOf<LongRange, Long>()
        val waterToLight = mutableMapOf<LongRange, Long>()
        val lightToTemperature = mutableMapOf<LongRange, Long>()
        val temperatureToHumidity = mutableMapOf<LongRange, Long>()
        val humidityToLocation = mutableMapOf<LongRange, Long>()
        var currentMap = seedToSoil

        inputs.drop(1).forEach { line ->
            if (line.startsWith("seed-to-soil map:")) currentMap = seedToSoil
            else if (line.startsWith("soil-to-fertilizer map:")) currentMap = soilToFertilizer
            else if (line.startsWith("fertilizer-to-water map:")) currentMap = fertilizerToWater
            else if (line.startsWith("water-to-light map:")) currentMap = waterToLight
            else if (line.startsWith("light-to-temperature map:")) currentMap = lightToTemperature
            else if (line.startsWith("temperature-to-humidity map:")) currentMap = temperatureToHumidity
            else if (line.startsWith("humidity-to-location map:")) currentMap = humidityToLocation
            else if (line.isNotBlank()) {
                val values = line.split(" ").map { it.toLong() }
                val destinationRangeStart = values.first()
                val sourceRangeStart = values[1]
                val rangeLength = values.last()
                currentMap[(sourceRangeStart until (sourceRangeStart + rangeLength))] = destinationRangeStart
            }
        }

        val res = seeds.map { seed ->
            val soil = getDestination(seedToSoil, seed)
            val fertilizer = getDestination(soilToFertilizer, soil)
            val water = getDestination(fertilizerToWater, fertilizer)
            val light = getDestination(waterToLight, water)
            val temperature = getDestination(lightToTemperature, light)
            val humidity = getDestination(temperatureToHumidity, temperature)
            val location = getDestination(humidityToLocation, humidity)
            //println(listOf(seed, soil, fertilizer, water, light, temperature, humidity, location))
            location
        }
        println(res.minOf { it })
        // First find seed numbers
        // Then read maps
        // Then go with each seed through each map
        // Then find the lowest location number
    }

    private fun getDestination(sourceDestinationMap: MutableMap<LongRange, Long>, sourceIndex: Long): Long {
        //This line is the issue
        val sourceRange = sourceDestinationMap.keys.firstOrNull { it.contains(sourceIndex) }
        val destination = if (sourceRange != null) {
            //Maybe that one
            val seedIndex = sourceRange.indexPerformant(sourceIndex)
            sourceDestinationMap[sourceRange]!! + seedIndex
        } else sourceIndex
        return destination
    }

    private fun LongRange.indexPerformant(element: Long): Int {
        return (element - start).toInt()
    }

    fun part2(inputs: List<String>) {
        // ATTENTION: USE LONG
        val seeds = inputs.first().replace("seeds: ", "").split(" ").map { it.toLong() }

        val seedToSoil = mutableMapOf<LongRange, Long>()
        val soilToFertilizer = mutableMapOf<LongRange, Long>()
        val fertilizerToWater = mutableMapOf<LongRange, Long>()
        val waterToLight = mutableMapOf<LongRange, Long>()
        val lightToTemperature = mutableMapOf<LongRange, Long>()
        val temperatureToHumidity = mutableMapOf<LongRange, Long>()
        val humidityToLocation = mutableMapOf<LongRange, Long>()
        var currentMap = seedToSoil

        inputs.drop(1).forEach { line ->
            if (line.startsWith("seed-to-soil map:")) currentMap = seedToSoil
            else if (line.startsWith("soil-to-fertilizer map:")) currentMap = soilToFertilizer
            else if (line.startsWith("fertilizer-to-water map:")) currentMap = fertilizerToWater
            else if (line.startsWith("water-to-light map:")) currentMap = waterToLight
            else if (line.startsWith("light-to-temperature map:")) currentMap = lightToTemperature
            else if (line.startsWith("temperature-to-humidity map:")) currentMap = temperatureToHumidity
            else if (line.startsWith("humidity-to-location map:")) currentMap = humidityToLocation
            else if (line.isNotBlank()) {
                val values = line.split(" ").map { it.toLong() }
                val destinationRangeStart = values.first()
                val sourceRangeStart = values[1]
                val rangeLength = values.last()
                currentMap[(sourceRangeStart until (sourceRangeStart + rangeLength))] = destinationRangeStart
            }
        }
        var min = Long.MAX_VALUE
        seeds.chunked(2).forEach { information ->
            val seed = information.first()
            val range = information.last()

            (seed until (seed + range)).forEach { subSeed ->
                val soil = getDestination(seedToSoil, subSeed)
                val fertilizer = getDestination(soilToFertilizer, soil)
                val water = getDestination(fertilizerToWater, fertilizer)
                val light = getDestination(waterToLight, water)
                val temperature = getDestination(lightToTemperature, light)
                val humidity = getDestination(temperatureToHumidity, temperature)
                val location = getDestination(humidityToLocation, humidity)
                //println(listOf(subSeed, soil, fertilizer, water, light, temperature, humidity, location))
                if (location < min) min = location
            }
        }
        println(min)

    }
}

