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

        //Look through lines to add all ours functions to count them, if
        //their declaration is below first call.
        addAllFunctions(lines)

        for (line in lines) {
            if (line.contains("//")
                    || line.isBlank() || line.contains("/*") || line.contains("*/") || line.contains("function ")) {
                continue
            }

            operators.keys
                    .filter {
                        line.contains(it) && !line.hasInQuotes(it)
                    }
                    .forEach { addOrIncrementOperator(it) }

            val dotIndex = line.lastIndexOf('.')
            //Line contains external method call
            if (dotIndex > 0 && operators.keys.none { line.contains(it) && !line.hasInQuotes(it)}) {
                addExternalOperator(line, dotIndex)
            }

            if (line.contains("var") || line.contains("let")) {
                addNewOperand(line)
            } else {
                //Increment every operand found in line.
                operands.keys
                        .filter { line.containsOperand(it) && !line.hasInQuotes(it)}
                        .forEach { operands[it] = operands[it]!! + 1 }
            }
        }

        //remove all basic operators that are not presented in code.
        operators = operators.filter { !basicOperators.contains(it.key) || it.value > 0 }.toMutableMap()
        return ParseResult(operators, operands)
    }

    private fun addExternalOperator(line: String, dotIndex: Int) {
        val bracketIndex = line.indexOf('(', dotIndex)
        if (bracketIndex > dotIndex) {
            val operator = line.substring(dotIndex + 1, bracketIndex)
            addOrIncrementOperator(operator)
        } else {
            println("Can't extract external operator from line:$line")
        }
    }

    private fun addOrIncrementOperator(operator: String) {
        if (operators[operator] == null) {
            operators[operator] = 0
        }
        operators[operator] = operators[operator]!! + 1
    }

    private fun addNewOperand(line: String) {
        val spaceIndex = if (line.contains("var")) {
            line.indexOf(' ', line.indexOf("var"))
        } else {
            line.indexOf(' ', line.indexOf("let"))
        }

        val operand = line.substring(spaceIndex + 1).takeWhile { it.isLetter() }
        if (!operands.contains(operand)) {
            operands[operand] = 0
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
        val leftQuotesCount = this.substring(0..index).count { it == '"' || it == '\''}
        return leftQuotesCount % 2 == 1
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

function incrementSeconds() {
	seconds += 1;
	${'$'}("#secondsCounter").text("Прошло времени: " + seconds + " секунд");
}

function fillField() {
	for (var i = 0; i < fieldSize; i++) {
		field.push([]);
		var newRow = document.createElement("div");
		newRow.classList.add("row");
		newRow.id = "row" + i;
		document.getElementById("field").appendChild(newRow);
		for (var j = 0; j < fieldSize; j++) {
			field[i][j] = 0;
			var newColumn = document.createElement("div");
			newColumn.classList.add("column");
			newColumn.setAttribute("style", "background-color: #BBBBBB; border:1px solid black");
			newColumn.id = "cell" + (i * fieldSize + j);
			newRow.appendChild(newColumn);
		}
	}
}
//Заполняет минами ячейки, кроме ячейки position
function fillMines(position) {
	var cellY = position / fieldSize;
	var cellX = position % fieldSize;
	var k = minesTotal;
	while (k > 0) {
		var y = Math.floor(Math.random() * fieldSize);
		var x = Math.floor(Math.random() * fieldSize);

		if (field[y][x] == cellStates.HIDDEN_MINE || cellY == y || cellX == x)
			continue;
		field[y][x] = cellStates.HIDDEN_MINE;
		k--;
	}
}
//Привязка слушателей jQuery
function bindListeners() {
	${'$'}('.column').on("mousedown",function(e) {
	    var id = this.id;
	    var intId = parseInt(id.substring(4, 10));

	    if(isFirstClick) {
    		isFirstClick = false;
    		fillMines(intId);
    	}
    	if (e.button === 0) {
    		handleCellClick.call(this, intId, true);
    		return false;
    	}
        if (e.button === 2) {
          handleCellClick.call(this, intId, false);
          return false;
        }
        return true;
 	});

 	${'$'}('#startButton').click(function() {
 		initialize();
 	})
}

function handleCellClick(position, isLeftClick) {
	var cell = this;
	var y = Math.floor(position / fieldSize);
	var x = position % fieldSize;

	if (field[y][x] === cellStates.OPEN) {
		return;
	}

	if (isLeftClick) {
		//Если кликнули по мине, конец игры, иначе обновляем игровое поле
		if (field[y][x] === cellStates.HIDDEN_MINE) {
			this.setAttribute("style", "background-color: #FF0000; border:1px solid black");
			endGame(false);
		} else {
			updateCell(x, y);
		}
	} else {
		//Обновляем флаг в клетке
		updateCellFlag(x, y);
	}
}
//Рекурсивный метод для открытия и обновления поля. Считает количество мин в соседних клетках, открывает клетку, если в ней нет мины.
function updateCell(x, y) {
	if (field[y][x] === cellStates.HIDDEN_MINE || field[y][x] === cellStates.FLAG_MINE)
		return;
	if (field[y][x] === cellStates.OPEN || field[y][x] === cellStates.FLAG_EMPTY)
		return;

	field[y][x] = cellStates.OPEN;
	var count = getAdjacentMinesCount(x, y);

	var intId = y * fieldSize + x;
	var element = document.getElementById("cell" + intId);
	element.setAttribute("style", "background-color: #FFFFFF; border:1px solid black");
	if(count > 0) {
		element.innerHTML = '<p style="width: 12px; margin: 15px auto; color:' + colors[count] + '">' + count + '</p>';
	} else {
		if (y > 0 && x > 0)
			updateCell(x - 1, y - 1);
		if (y > 0)
			updateCell(x, y - 1);
		if (y > 0 && x < fieldSize - 1)
			updateCell(x + 1, y - 1);
		if (x < fieldSize - 1)
			updateCell(x + 1, y);
		if (y < fieldSize - 1 && x < fieldSize - 1)
			updateCell(x + 1, y + 1);
		if (y < fieldSize - 1)
			updateCell(x, y + 1);
		if (y < fieldSize - 1 && x > 0)
			updateCell(x - 1, y + 1);
		if (x > 0)
			updateCell(x - 1, y);
	}
}

function getAdjacentMinesCount(x, y) {
	var count = 0;
	if (y > 0 && x > 0 && (field[y - 1][x - 1] === cellStates.HIDDEN_MINE || field[y - 1][x - 1] === cellStates.FLAG_MINE))
		count++;
	if (y > 0 && (field[y - 1][x] === cellStates.HIDDEN_MINE || field[y - 1][x] === cellStates.FLAG_MINE))
		count++;
	if (y > 0 && x < fieldSize - 1 && (field[y - 1][x + 1] === cellStates.HIDDEN_MINE || field[y - 1][x + 1] === cellStates.FLAG_MINE))
		count++;
	if (x < fieldSize - 1 && (field[y][x + 1] === cellStates.HIDDEN_MINE || field[y][x + 1] === cellStates.FLAG_MINE))
		count++;
	if (y < fieldSize - 1 && x < fieldSize - 1 && (field[y + 1][x + 1] === cellStates.HIDDEN_MINE || field[y + 1][x + 1] === cellStates.FLAG_MINE))
		count++;
	if (y < fieldSize - 1 && (field[y + 1][x] === cellStates.HIDDEN_MINE || field[y + 1][x] === cellStates.FLAG_MINE))
		count++;
	if (y < fieldSize - 1 && x > 0 && (field[y + 1][x - 1] === cellStates.HIDDEN_MINE || field[y + 1][x - 1] === cellStates.FLAG_MINE))
		count++;
	if (x > 0 && (field[y][x - 1] === cellStates.HIDDEN_MINE || field[y][x - 1] === cellStates.FLAG_MINE))
		count++;
	return count;
}

//Устанавливает/убирает флаг в клетке
function updateCellFlag(x, y) {
	if (flagsLeft === 0)
		return;
	var intId = y * fieldSize + x;
	var element = document.getElementById("cell" + intId);
	if (field[y][x] ===  cellStates.HIDDEN_MINE || field[y][x] === cellStates.HIDDEN_EMPTY) {
		flagsLeft--;
		//element.innerHTML = '<p style="width: 12px; margin: 15px auto;">F</p>';
		element.innerHTML = '<img src="https://img.icons8.com/color/48/000000/filled-flag.png">';
		field[y][x] = (field[y][x] == cellStates.HIDDEN_MINE) ? cellStates.FLAG_MINE : cellStates.FLAG_EMPTY;
	} else {
		element.innerHTML = "";
		flagsLeft++;
		field[y][x] = (field[y][x] === cellStates.FLAG_MINE) ? cellStates.HIDDEN_MINE : cellStates.HIDDEN_EMPTY;
	}
	updateFlagsTextView();
	//Если все флаги установлены и нет скрытых клеток -> конец игры
	if (flagsLeft === 0) {
		if(!checkHasHiddenCells())
			endGame(true);
	}
}

function checkHasHiddenCells() {
	for (var i = 0; i < fieldSize; i++) {
		for (var j = 0; j < fieldSize; j++) {
			if (field[i][j] === cellStates.HIDDEN_MINE || field[i][j] === cellStates.HIDDEN_EMPTY) {
				return true;
			}

		}
	}
	return false;
}

function updateFlagsTextView() {
	${'$'}("#flagsLeftTextDiv").html('<h2>' + "осталось флагов: " + flagsLeft + '</h2>');
}

function endGame(isWin) {
	${'$'}(".field").css("pointer-events","none");

	if(counter)
		clearInterval(counter);

	var text;
	if (isWin) {
		text = "Победа";
	} else {
		text = "Поражение";
		for (var i = 0; i < fieldSize; i++) {
			for (var j = 0; j < fieldSize; j++) {
				if (field[i][j] === cellStates.HIDDEN_MINE || field[i][j] === cellStates.FLAG_MINE) {
					var id = (i * fieldSize + j);
					document.getElementById("cell" + id).innerHTML = '<img src="https://img.icons8.com/emoji/48/000000/bomb-emoji.png">';
				}
			}
		}
	}
	${'$'}('#endGameTextDiv').html("<h1>" + text + "</h1>");
}"""