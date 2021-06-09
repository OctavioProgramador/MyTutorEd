package com.example.gg.utils

import com.example.gg.utils.Constants.OPEN_GOOGLE
import com.example.gg.utils.Constants.OPEN_SEARCH
import java.lang.Exception

object BotResponse {
    //
    fun basicResponse(_message: String): String{
        //
        val random = (0..2).random()
        val respuesta = 1
        val message = _message.toLowerCase()

        return when {
            /*Pregunta de ingenieria de software
            *
            * */

            // Respuesta 1
            message.contains("que es la ingenieria de software?") ->{
                "La Ingeniería de Software es una de las ramas de las ciencias de la computación que " +
                "estudia la creación de software confiable y de calidad, basándose en métodos y "      +
                "técnicas de ingeniería. Brindando soporte operacional y de mantenimiento, el campo "  +
                "de estudio de la ingeniería de software.\u200B"

            }

            //Respuesta 2
            message.contains("que es scrum?") ->{
                "Scrum es un marco de trabajo para desarrollo ágil de software que se ha expandido a "     +
                "otras industrias. Es un proceso en el que se aplican de manera regular un conjunto de "   +
                "buenas prácticas para trabajar colaborativamente, en equipo y obtener el mejor resultado" +
                " posible de proyectos"

            }

            //Respuesta 3
            message.contains("que es el desarrollo ágil?" ) ||
            message.contains("que es el desarrollo agil?" )->{
                "El desarrollo ágil de software envuelve un enfoque para la toma de decisiones en los"     +
                " proyectos de software, que se refiere a métodos de ingeniería del software basados "     +
                "en el desarrollo iterativo e incremental, donde los requisitos y soluciones evolucionan " +
                "con el tiempo según la necesidad del proyecto."
            }

            //Respuesta 4
            message.contains("que es un caso de uso?") ->{
                "Un caso de uso es la descripción de una acción o actividad. Un diagrama de caso de uso es " +
                "una descripción de las actividades que deberá realizar alguien o algo para llevar a "       +
                "cabo algún proceso. Los personajes o entidades que participarán en un diagrama de "         +
                "caso de uso se denominan actores."

            }

            //Respuesta 5
            message.contains("que es uml?") ->{
                "El lenguaje unificado de modelado (UML de sus siglas en ingles) es el lenguaje de modelado" +
                " de sistemas de software más conocido y utilizado en la actualidad; está respaldado "       +
                "por el Object Management Group. Es un lenguaje gráfico para visualizar, especificar, "      +
                "construir y documentar un sistema."

            }

            //Respuesta 6
            message.contains("que es la trazabilidad de requisitos?") ->{
                "La Trazabilidad de requisitos es la asociación de un requisito con otros requisitos y las" +
                " diferentes instancias o artefactos con que se relaciona, así como la habilidad de"        +
                " describir y seguir el ciclo de vida completo de un requisito, desde su origen, "          +
                "pasando por su desarrollo y especificación y finalizando con su despliegue."

            }

            //Respuesta 7
            message.contains("que es un diagrama de clases?") ->{
                "En ingeniería de software, un diagrama de clases en Lenguaje Unificado de Modelado es un " +
                "tipo de diagrama de estructura estática que describe la estructura de un sistema "         +
                "mostrando las clases del sistema, sus atributos, operaciones, y las relaciones entre "     +
                "los objetos. "

            }

            //Respuesta 8
            message.contains("que es un diagrama de secuencia?") ->{
                "El diagrama de secuencia es un tipo de diagrama usado para modelar interacción entre " +
                "objetos en un sistema según UML. En inglés se pueden encontrar como \"sequence"        +
                " diagram\", \"event-trace diagrams\"."

            }

            //Respuesta 9
            message.contains("que es un diagrama de paquetes?") ->{
                "Un diagrama de paquetes en el Lenguaje Unificado de Modelado representa las dependencias"     +
                " entre los paquetes que componen un modelo. Es decir, muestra cómo un sistema está dividido " +
                "en agrupaciones lógicas y las dependencias entre esas agrupaciones."

            }

            //Respuesta 10
            message.contains("que es un diagrama de actividades?") ||
            message.contains("que es un diagrama de flujo?")      ->{
                "El diagrama de flujo o flujograma o diagrama de actividades es la representación gráfica de" +
                " un algoritmo o proceso. Se utiliza en disciplinas como programación, economía, procesos"    +
                " industriales y psicología cognitiva."

            }



            // Hola
            message.contains("hola") ->{
                when (random) {
                    0 -> "Hola, como va todo?"
                    1 -> "Hola guap@"
                    2 -> "Hola, me acabo de despertar, en que te ayudo?"
                    else -> "error"
                }
            }

            // How are you
            message.contains("como estas") ->{
                when (random) {
                    0 -> "Estoy bien, mejor por que estas aqui <3"
                    1 -> "Algo aburrido, pero bien"
                    2 -> "Muy bien y tu"
                    else -> "error"
                }
            }

            // Le diremos que lance una moneda
            message.contains("lanza") && message.contains("moneda") ->{
                var r =(0..1).random()
                // Recuerda no es necesario poner corchetes con una sola instrucción
                val result = if (r == 0) "cara" else "aguila"


                "Yo lance una moneda y cayo en $result"

            }

            // Resolver ecuaciones simples de 2 variables
            message.contains("resuelve") -> {
                // Esto lo que hace es ignorar todoo lo anterior a "resolver" y quedarse con lo que
                // este adelante
                val equation: String? = message.substringAfter("resuelve")

                //
                return try {
                    // especificamos el objeto
                    val asnwer = SolveMath.solveMath(equation ?: "0")
                    asnwer.toString()

                } catch (e: Exception) {
                    "Lo siento es muy compleja para mi calculadora xd"
                }
            }

            // Le diremos que nos de la hora actual
            message.contains("hora") && message.contains("?") ->{
                Time.timeStamp()

            }

            // Le diremos que abra google si escribe google
            message.contains("abre") && message.contains("google") -> {
                OPEN_GOOGLE

            }

            // Le diremos que una busqueda
            message.contains("busca") -> {
                OPEN_SEARCH
            }



            else -> {
                when (random) {
                    0 -> "Ah si eso,no lo sé"
                    1 -> "No estoy programado para responder eso amigo"
                    2 -> "Intenta preguntar algo diferente"
                    else -> "error"
                }
            }
        }
    }
}