class sudoku{
    board : number[][];
    constructor (board : string [][]){
        this.board = board.map(row => row.map(cell => (cell === '.' ? 0 : parseInt(cell))));
    }

    private isValid(row: number, col: number, num: number): boolean {
        for (let i = 0; i < 9; i++) {
          const boxRow = 3 * Math.floor(row / 3) + Math.floor(i / 3);
          const boxCol = 3 * Math.floor(col / 3) + (i % 3);
    
          if (
            this.board[row][i] === num ||
            this.board[i][col] === num ||
            this.board[boxRow][boxCol] === num
          ) {
            return false;
          }
        }
        return true;
    }


    private solve(): boolean{
        for(let row = 0; row < 9; row++){
            for(let col = 0; col < 9; col++){
                if(this.board[row][col] === 0){
                    for(let num =1 ; num <=9; num++){
                        if(this.isValid(row,col,num)){
                            this.board[row][col] = num;
                            if(this.solve()){
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
    }
    

    getSolution(): number[][] {
        if (this.solve()) {
          return this.board;
        } else {
          throw new Error('No solution found');
        }
    }

}


const board: string[][] = [
    ["5","3",".",".","7",".",".",".","."],
    ["6",".",".","1","9","5",".",".","."],
    [".","9","8",".",".",".",".","6","."],
    ["8",".",".",".","6",".",".",".","3"],
    ["4",".",".","8",".","3",".",".","1"],
    ["7",".",".",".","2",".",".",".","6"],
    [".","6",".",".",".",".","2","8","."],
    [".",".",".","4","1","9",".",".","5"],
    [".",".",".",".","8",".",".","7","9"]
];
  
  const Sudoku = new sudoku(board);
  const solution = Sudoku.getSolution();
  console.log(solution);