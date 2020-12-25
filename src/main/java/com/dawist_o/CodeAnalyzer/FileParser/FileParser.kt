package com.dawist_o.CodeAnalyzer.FileParser

import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

fun main() {
    val fileParser = FileParser(File("C:\\Users\\Asus\\Desktop\\lab2.js"))
    val result = fileParser.parseFile()
    print("CL = " + result.CL)
    print("\n")
    print("CLI = " + result.CLI)
}

class FileParser(private val file: File) {

    private var operators = basicOperators.associate { Pair(it, 0) }.toMutableMap()
    private var operands = HashMap<String, Int>()

    private var CLI = 0 // max
    private var CLI_stack = Stack<String>() // max
    private var CL = 0  // total
    private var casesCount = -1

    //TODO найди максимальный уровень вложенности условного оператора (CLI)

    fun countConditions(line: String) {
        val clear_line = line.trim()
        if (clear_line.startsWith("if") ||
                clear_line.startsWith("for") ||
                clear_line.startsWith("while")    ||  clear_line.startsWith("switch")
                && clear_line.endsWith("{") || clear_line.contains("case")) {
            if(!clear_line.contains("case") && !clear_line.contains("switch")) {
                println("Increment CL for line:$clear_line")
                CL++
            }
            CLI_stack.push(clear_line)

            if(CLI_stack.size > CLI) {
                CLI = CLI_stack.sumBy {
                    if(it.contains("switch")) 0 else 1
                }
            }

        } else if (clear_line.endsWith("}")) {
            if (!CLI_stack.empty()) {
                val popedLine = CLI_stack.pop()
                if(popedLine.contains("switch")) {
                    println("Increment CL by $casesCount cases count")
                    CL += casesCount
                    casesCount = -1
                }

                if(popedLine.contains("case")) {
                    casesCount++
                }
            }
        }
    }

    fun parseFile(): ParseResult {
        val lines = readLinesFromFile()

        //Look through lines to add all ours functions to count them, if
        //their declaration is below first call.
        addAllFunctions(lines)

        addAllNumbers(lines)

        for (line in lines) {
            if (line.contains("//")
                    || line.isBlank() || line.contains("/*") || line.contains("*/") || line.contains("function ")) {
                continue
            }
            countConditions(line);


            operators.keys
                    .filter {
                        if (it == "=" && line.contains(it)) {
                            val index = line.indexOf(it)
                            val previousChar = line[index - 1]
                            val nextChar = line[index + 1]
                            return@filter previousChar != '=' && nextChar != '='
                        }
                        if (it == "==" && line.contains(it)) {
                            val index = line.indexOf(it)
                            val previousChar = line[index - 1]
                            val nextChar = line[index + 1]
                            return@filter previousChar != '=' && nextChar != '='

                        }

                        if (it == "|" && line.contains(it)) {
                            val index = line.indexOf(it)
                            val previousChar = line[index - 1]
                            val nextChar = line[index + 1]
                            return@filter previousChar != '|' && nextChar != '|'

                        }

                        return@filter line.contains(it) && !line.hasInQuotes(it)

                    }
                    .forEach { addOrIncrementOperator(it) }

            val dotIndex = line.lastIndexOf('.')
            //Line contains external method call
            if (dotIndex > 0 && operators.keys.none { line.substring(dotIndex).contains(it) && !line.hasInQuotes(it) }) {
                addExternalOperator(line, dotIndex)
            }

            //Отрезаем часть строки с объявлением, чтобы не посчитать дважды.
            val lineCopy = if (line.contains("var") || line.contains("let")) {
                val lastOperandIndex = addNewOperand(line)
                line.substring(lastOperandIndex + 1)
            } else {
                line
            }

            //Increment every operand found in line.
            operands.keys
                    .filter { operand ->
                        lineCopy.containsOperand(operand) && !lineCopy.hasInQuotes(operand)
                    }
                    .forEach { operand ->
                        //Пока можем найти операнд в строке, смотрим, окружён ли он несимвовами, тогда увеличиваем количество.
                        var lineCopy123 = lineCopy
                        var count = 0

                        while (lineCopy123.indexOf(operand) > 0) {
                            val index = lineCopy123.indexOf(operand)

                            val previousCharIndex = lineCopy123.indexOf(operand) - 1
                            val nextCharIndex = lineCopy123.indexOf(operand) + operand.length

                            if (previousCharIndex >= 0 && nextCharIndex < lineCopy123.length) {
                                val nextChar = lineCopy123[nextCharIndex]
                                val previousChar = lineCopy123[previousCharIndex]
                                !previousChar.isLetter() && !nextChar.isLetter()
                                count++
                            }
                            lineCopy123 = lineCopy123.substring(index + 1)
                        }

                        operands[operand] = operands[operand]!! + count
                    }
        }

        //remove all basic operators that are not presented in code.
        operators = operators.filter { !basicOperators.contains(it.key) || it.value > 0 }.toMutableMap()

        operands = operands.filter { it.value > 0 } as HashMap<String, Int>
        return ParseResult(operators, operands, CL, --CLI)
    }

    private fun addAllNumbers(lines: List<String>) {
        operands["0"] = 0
        operands["1"] = 0
        operands["32"] = 0
        operands["true"] = 0
        operands["false"] = 0
        lines.forEach { line ->
            val zerosCount = line.split(" 0 ").dropLastWhile { it.isEmpty() }.toTypedArray().size
            val onesCount = line.split(" 1 ").dropLastWhile { it.isEmpty() }.toTypedArray().size

            if (zerosCount > 0) {
                operands["0"] = zerosCount - 1
            }
            if (onesCount > 0) {
                operands["1"] = onesCount - 1
            }
        }
    }

    private fun addExternalOperator(line: String, dotIndex: Int) {
        val bracketIndex = line.indexOf('(', dotIndex)
        if (bracketIndex > dotIndex) {
            val operator = line.substring(dotIndex + 1, bracketIndex)
            addOrIncrementOperator(operator)
        } /*else {
            println("Can't extract external operator from line:$line")
        }*/
    }

    private fun addOrIncrementOperator(operator: String) {
        if (operators[operator] == null) {
            operators[operator] = 0
        }
        operators[operator] = operators[operator]!! + 1
    }

    private fun addNewOperand(line: String): Int {
        val spaceIndex = if (line.contains("var")) {
            line.indexOf(' ', line.indexOf("var"))
        } else {
            line.indexOf(' ', line.indexOf("let"))
        }

        val operand = line.substring(spaceIndex + 1).takeWhile { it.isLetter() }
        if (!operands.contains(operand)) {
            operands[operand] = 0
        }

        return spaceIndex + operand.length
    }

    private fun addAllFunctions(lines: List<String>) {
        lines.forEach {
            if (it.contains("function ") && it.contains("(") && it.contains(")")) {
                val braceIndex = it.indexOf("(")
                val spaceIndex = it.lastIndexOf(" ", braceIndex)
                val functionName = it.substring((spaceIndex + 1) until braceIndex)
                operators[functionName] = 0
            }
        }
    }

    private fun readLinesFromFile(): List<String> {
        val inputStream: InputStream = file.inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        return inputString.lines()
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

    /**
     * Checks if argument is in quotes, i.e. "<h1>123</h>"
     */
    private fun String.hasInQuotes(substring: String): Boolean {
        val index = this.indexOf(substring)
        val leftQuotesCount = this.substring(0..index).count { it == '"' || it == '\'' }
        return leftQuotesCount % 2 == 1
    }

    companion object {
        val basicOperators = listOf("for", "while", "if", "=", "==", "===", "<", ">", "<=", ">=", "!=", "!==", "+", "-", "*", "/", "&&", "||", "&", "|", "^", "~", "return")
    }

    class ParseResult(val operators: MutableMap<String, Int>, val operands: MutableMap<String, Int>, val CL: Int, val CLI: Int) {
        val totalUniqueOperators: Int
            get() = operators.size

        val totalUniqueOperands: Int
            get() = operands.size

        val totalOperators: Int
            get() = operators.values.sum()

        val totalOperands: Int
            get() = operands.values.sum()

        val programmDictionnary: Int
            get() = totalUniqueOperators + totalUniqueOperands

        val programmLength: Int
            get() = totalOperators + totalOperands

        val volume: Int
            get() = (programmLength * Math.log(programmDictionnary.toDouble()) / Math.log(2.0)).toInt()

        val cl: Double // CL/operators count
            get() = ( CL.toDouble() /  operators.values.sum())

        val spen:Int
            get() = totalOperands - operands.size
    }
}