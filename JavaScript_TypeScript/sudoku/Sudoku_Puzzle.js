var sudoku = /** @class */ (function () {
    function sudoku(board) {
        this.board = board.map(function (row) { return row.map(function (cell) { return (cell === '.' ? 0 : parseInt(cell)); }); });
    }
    sudoku.prototype.isValid = function (row, col, num) {
        for (var i = 0; i < 9; i++) {
            var boxRow = 3 * Math.floor(row / 3) + Math.floor(i / 3);
            var boxCol = 3 * Math.floor(col / 3) + (i % 3);
            if (this.board[row][i] === num ||
                this.board[i][col] === num ||
                this.board[boxRow][boxCol] === num) {
                return false;
            }
        }
        return true;
    };
    sudoku.prototype.solve = function () {
        for (var row = 0; row < 9; row++) {
            for (var col = 0; col < 9; col++) {
                if (this.board[row][col] === 0) {
                    for (var num = 1; num <= 9; num++) {
                        if (this.isValid(row, col, num)) {
                            this.board[row][col] = num;
                            if (this.solve()) {
                                return true;
                            }
                            this.board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    };
    sudoku.prototype.getSolution = function () {
        if (this.solve()) {
            return this.board;
        }
        else {
            throw new Error('No solution found');
        }
    };
    return sudoku;
}());
var board = [
    ["5", "3", ".", ".", "7", ".", ".", ".", "."],
    ["6", ".", ".", "1", "9", "5", ".", ".", "."],
    [".", "9", "8", ".", ".", ".", ".", "6", "."],
    ["8", ".", ".", ".", "6", ".", ".", ".", "3"],
    ["4", ".", ".", "8", ".", "3", ".", ".", "1"],
    ["7", ".", ".", ".", "2", ".", ".", ".", "6"],
    [".", "6", ".", ".", ".", ".", "2", "8", "."],
    [".", ".", ".", "4", "1", "9", ".", ".", "5"],
    [".", ".", ".", ".", "8", ".", ".", "7", "9"]
];
var Sudoku = new sudoku(board);
var solution = Sudoku.getSolution();
console.log(solution);
