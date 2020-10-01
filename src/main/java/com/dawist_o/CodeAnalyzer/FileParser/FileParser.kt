package com.dawist_o.CodeAnalyzer.FileParser

import java.io.File

fun main() {
    val fileParser = FileParser(File(""))
    val result = fileParser.parseFile()
}


class FileParser(private val file: File) {

    private var operators = basicOperators.associate { Pair(it, 0) }.toMutableMap()
    private val operands = HashMap<String, Int>()

    //TODO найди максимальный уровень вложенности условного оператора (CLI)

    fun parseFile(): ParseResult {
        val lines = readLinesFromFile()
        addAllFunctions(lines)
        for (line in lines) {
            if (line.contains("//") || line.isBlank()) {
                continue
            }

            if (!line.contains("function ")) {
                operators.keys.filter { line.contains(it) }.forEach { parseOperator(it) }
            }

            if (line.contains("var") || line.contains("let")) {
                addOperand(line)
            }

                val validOperands = operands
                        .keys
                        .filter { line.contains(it) }

                validOperands.forEach {
                    operands[it] = operands[it]!! + 1
                }
            }

        operators = operators.filter { it.value > 0  }.toMutableMap() // remove all operators that are not presented in code.
        return ParseResult(operators, operands)
    }

    private fun parseOperator(operator: String) {
        if (operators[operator] == null) {
            operators[operator] = 0
        }
        operators[operator] = operators[operator]!! + 1
    }

    private fun addOperand(line: String) {
        val spaceIndex = if (line.contains("var")) {
            line.indexOf(' ', line.indexOf("var"))
        } else {
            line.indexOf(' ', line.indexOf("var"))
        }

        val operand = line.substring(spaceIndex + 1).takeWhile { it.isLetter() }
        if(!operands.contains(operand)) {
            operands[operand] = -1
        }
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
        return text.lines()
    }

    companion object {
        val basicOperators = listOf("for", "while", "if", "=", "==", "===", "<", ">", "<=", ">=", "!=", "!==", "+", "-", "*", "/", "&&", "||", "&", "|", "^", "~")
    }

    class ParseResult(val operators: MutableMap<String, Int>, val operands: MutableMap<String, Int>) {
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
    }
}


val text = """var field;
var fieldSize;
var fieldDiv;
var minesTotal;
var flagsLeft;
var isFirstClick;
var colors = ["#FF0000", "#00FF00", "#00000FF", "#c62828", "#E91E63", "#29B6F6", "#F9A825", "#4E342E", "#4DB6AC"];
var seconds;
var counterDiv;
var counter;
//Состояния ячеек поля
var cellStates = {
	HIDDEN_EMPTY : 0,
	HIDDEN_MINE: 1,
	OPEN: 2,
	FLAG_EMPTY: 3,
	FLAG_MINE: 4
}

${'$'}(document).ready(function() {
	fieldDiv = ${'$'}("#field");
	counterDiv  = ${'$'}("#secondsCounter");
	initialize();
});

function initialize() {
	field = [];
	fieldSize = 16;
	fieldDiv.html("");
	minesTotal = 30;
	flagsLeft = minesTotal;
	isFirstClick = true;
	seconds = 0;

	fillField();

	${'$'}('#endGameTextDiv').html("<h1></h1>");
	updateFlagsTextView();

	${'$'}("#field").css("pointer-events","auto");

	bindListeners();
	if(counter)
		clearInterval(counter);
	counter = setInterval(incrementSeconds, 1000);
}

}"""