package com.dawist_o.CodeAnalyzer.FileParser

import java.io.File
import java.io.InputStream

class FileParserForChepin(private val file: File) {
//    1. Р – вводимые переменные, содержащие исходную информацию, но не модифицируемые в программе и не являющиеся управляющими переменными;
//    2. М – модифицируемые переменные и создаваемые внутри программы константы и переменные, не являющиеся управляющими переменными;
//    3. С – переменные, участвующие в управлении работой программного модуля (управляющие переменные);
//    4. Т – не используемые в программе («паразитные») переменные, например, вычисленные переменные, значения которых не выводятся и не участвуют в дальнейших вычислениях.
    private val operands = mutableListOf<Operand>()

    fun parseFile(): Pair<ChapinResult, ChapinResult> {
        val lines = readLinesFromFile()

        for (line in lines) {
            if (line.contains("//")
                    || line.isBlank() || line.contains("/*") || line.contains("*/") || line.contains("function ")) {
                continue
            }

            val newOperand = if (line.contains("var") || line.contains("let") || line.contains("const")) {
                addNewOperand(line)
            } else {
                null
            }
            parseLine(line, newOperand)
        }

        operands.forEach {
            if(it.count <= 0) {
                it.type = Type.T
            }
        }

        return Pair(ChapinResult(operands), ChapinResult(operands.filter { it.isIOVariable } as MutableList<Operand>))
    }

    private fun parseLine(line: String, newOperand: Operand?) {
        operands
                .filter { line.containsOperand(it.name) && it.name != newOperand?.name}
                .forEach {
                    it.count++
                    when {
                        line.equalsSignIndex() > 0 -> {
                            if (line.indexOf(it.name) < line.equalsSignIndex() && it.type != Type.C) {
                                it.type = Type.M
                            }
                        }
                        line.containsControl() -> {
                            it.type = Type.C
                        }
                        line.containsWrite() -> {
                            it.isIOVariable = true
                        }
                    }
                }
    }

    private fun addNewOperand(line: String): Operand? {
        var type = Type.M
        val spaceIndex = when {
            line.contains("var") -> line.indexOf(' ', line.indexOf("var"))
            line.contains("let") -> line.indexOf(' ', line.indexOf("let"))
            line.contains("const") -> {
                type = Type.M
                line.indexOf(' ', line.indexOf("const"))
            }
            else -> -1
        }

        val operandName = line.substring(spaceIndex + 1).takeWhile { it.isLetter() }
        var isIOVariable = false
        if (line.isReadLine()) {
            type = Type.P
            isIOVariable = true
        }

        if (operands.none { it.name == operandName }) {
            val operand = Operand(operandName, 0, type, isIOVariable)
            operands.add(operand)
            return operand
        }
        return null
    }

    private fun readLinesFromFile(): List<String> {
        val inputStream: InputStream = file.inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        return inputString.lines();
    }

    private fun String.containsOperand(operand: String): Boolean {
        val previousCharIndex = this.indexOf(operand) - 1
        val nextCharIndex = this.indexOf(operand) + operand.length
        //If operand is surrounded with two non-letter characters to prevent counting
        //One operator in another, i.e. "counter" in "counterDiv"
        return if (previousCharIndex >= 0 && nextCharIndex < this.length) {
            val nextChar = this[nextCharIndex]
            val previousChar = this[previousCharIndex]
            !previousChar.isLetter() && !nextChar.isLetter()
        } else {
            false
        }
    }

    private fun String.isReadLine(): Boolean {
        return this.contains("readLine(")
    }

    private fun String.equalsSignIndex(): Int {
        return this.indexOf(" = ")
    }

    private fun String.containsControl(): Boolean {
        return this.contains("if")
                || this.contains("for")
                || this.contentEquals("while")
                || this.contains("switch")
    }

    private fun String.containsWrite(): Boolean {
        return this.contains("console.log(") || this.contains("document.write(")
    }

    class ChapinResult(val operands: MutableList<Operand>) {
        val P = operands.filter { it.type == Type.P }
        val M = operands.filter { it.type == Type.M }
        val C = operands.filter { it.type == Type.C }
        val T = operands.filter { it.type == Type.T }


        val PVariables: String
            get() = P.map{it.name}.joinToString(",")

        val MVariables: String
            get() = M.map{it.name}.joinToString(",")

        val CVariables: String
            get() = C.map{it.name}.joinToString(",")

        val TVariables: String
            get() = T.map{it.name}.joinToString(",")

        val PCount: String
            get() = "p = ${P.size}"

        val MCount: String
            get() = "m = ${M.size}"

        val CCount: String
            get() = "c = ${C.size}"

        val TCount: String
            get() = "t = ${T.size}"

        val ChepinMetric: String
            get() = "Q = 1 * ${P.size} + 2 * ${M.size} + 3 * ${C.size} + 0.5 * ${T.size} = ${1 * P.size + 2 * M.size + 3 * C.size + 0.5 * T.size}"
    }

    data class Operand(val name: String, var count: Int, var type: Type, var isIOVariable: Boolean = false)

    enum class Type {
        P, M, C, T;
    }
}

val text = """
    const name = "AppName"
    let unused = "This is unused"
    var myVar = 0
    function myFun() {
        myVar = 123;
        if(myVar == 123) {
            let input = readLine()
            let copy = myVar;
            let toPrint = 10 * input;
            for(int i = 0; i < copy; i++) {
                console.log(toPrint)
            }
        }
    }
"""