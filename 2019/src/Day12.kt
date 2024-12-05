import kotlin.math.absoluteValue

object Day12 {

    fun start() {
        val planets = emptyList<Planet>() //read this from file!!
        part1(planets, 1000)
        part2(planets)

    }

    fun part1(planets: List<Planet>, toTime: Int) {
        for (time in 1..toTime) {
            for (p1Idx in 0 until planets.size) {
                for (p2Idx in p1Idx + 1 until planets.size) {
                    planets[p1Idx].updateVelocity(planets[p2Idx])
                }
            }

            println("After $time Steps:")
            for (planet in planets) {
                planet.applyVelocity()
                println(planet)
            }
            println()

        }

        val energySum = planets.map { it.calculateEnergy() }.sum()
        println("Sum of Energy: $energySum")
    }

    fun part2(planets: List<Planet>) {
        var time = 0L
        val cycles = mutableListOf(0L, 0L, 0L)
        val posVelX: MutableList<Pair<Int, Int>> = mutableListOf()
        val posVelY: MutableList<Pair<Int, Int>> = mutableListOf()
        val posVelZ: MutableList<Pair<Int, Int>> = mutableListOf()

        for (planet in planets) {
            posVelX.add(Pair(planet.pX, planet.vX))
            posVelY.add(Pair(planet.pY, planet.vY))
            posVelZ.add(Pair(planet.pZ, planet.vZ))
        }

        while (true) {
            time++
            for (p1Idx in 0 until planets.size) {
                for (p2Idx in p1Idx + 1 until planets.size) {
                    if (cycles[0] == 0L) {
                        planets[p1Idx].updateVelocityX(planets[p2Idx])
                    }
                    if (cycles[1] == 0L) {
                        planets[p1Idx].updateVelocityY(planets[p2Idx])
                    }
                    if (cycles[2] == 0L) {
                        planets[p1Idx].updateVelocityZ(planets[p2Idx])
                    }
                }
            }

            for ((idx, planet) in planets.withIndex()) {
                if (cycles[0] == 0L) {
                    planets[idx].applyVelocityX()
                }
                if (cycles[1] == 0L) {
                    planets[idx].applyVelocityY()
                }
                if (cycles[2] == 0L) {
                    planets[idx].applyVelocityZ()
                }
            }
            if (cycles[0] == 0L && planets.map { Pair(it.pX, it.vX) }.containsAll(posVelX)) {
                println("X: $time")
                cycles[0] = time
            }

            if (cycles[1] == 0L && planets.map { Pair(it.pY, it.vY) }.containsAll(posVelY)) {
                println("Y: $time")
                cycles[1] = time
            }

            if (cycles[2] == 0L && planets.map { Pair(it.pZ, it.vZ) }.containsAll(posVelZ)) {
                println("Z: $time")
                cycles[2] = time
            }

            if (cycles.filter { it == 0L }.isEmpty()) {
                println(cycles)
                val lcm1 = lcm(cycles[0], cycles[1])
                val lcm2 = lcm(lcm1, cycles[2])
                println(lcm2)
                return
            }
            //println()

        }
    }

    fun test1() {
        val planets = listOf(
            Planet(-1, 0, 2),
            Planet(2, -10, -7),
            Planet(4, -8, 8),
            Planet(3, 5, -1)
        )
        part1(planets, 10)

        val planets2 = listOf(
            Planet(-8, -10, 0),
            Planet(5, 5, 10),
            Planet(2, -7, 3),
            Planet(9, -8, -3)
        )

        part1(planets2, 100)

    }

    fun test2() {
        val planets = listOf(
            Planet(-1, 0, 2),
            Planet(2, -10, -7),
            Planet(4, -8, 8),
            Planet(3, 5, -1)
        )
        part2(planets)

        val planets2 = listOf(
            Planet(-8, -10, 0),
            Planet(5, 5, 10),
            Planet(2, -7, 3),
            Planet(9, -8, -3)
        )

        part2(planets2)
    }

    class Planet(var pX: Int, var pY: Int, var pZ: Int) {
        var vX: Int = 0
        var vY: Int = 0
        var vZ: Int = 0

        fun updateVelocity(planetTwo: Planet) {
            updateVelocityX(planetTwo)
            updateVelocityY(planetTwo)
            updateVelocityZ(planetTwo)
        }

        fun updateVelocityX(planetTwo: Planet) {
            if (pX > planetTwo.pX) {
                vX--
                planetTwo.vX++
            } else if (pX < planetTwo.pX) {
                vX++
                planetTwo.vX--
            }
        }

        fun updateVelocityY(planetTwo: Planet) {
            if (pY > planetTwo.pY) {
                vY--
                planetTwo.vY++
            } else if (pY < planetTwo.pY) {
                vY++
                planetTwo.vY--
            }
        }

        fun updateVelocityZ(planetTwo: Planet) {
            if (pZ > planetTwo.pZ) {
                vZ--
                planetTwo.vZ++
            } else if (pZ < planetTwo.pZ) {
                vZ++
                planetTwo.vZ--
            }
        }

        fun applyVelocity() {
            applyVelocityX()
            applyVelocityY()
            applyVelocityZ()
        }

        fun applyVelocityX() {
            pX += vX
        }

        fun applyVelocityY() {
            pY += vY
        }

        fun applyVelocityZ() {
            pZ += vZ
        }

        fun calculateEnergy(): Int {
            val potEnergy = pX.absoluteValue + pY.absoluteValue + pZ.absoluteValue
            val kinEnergy = vX.absoluteValue + vY.absoluteValue + vZ.absoluteValue
            return potEnergy * kinEnergy
        }

        override fun toString(): String {
            return "pos = <x = $pX, y = $pY, z = $pZ> vel = <x = $vX, y = $vY, z = $vZ>"
        }
    }
}


fun main(args: Array<String>) {
    Day12.start()
}