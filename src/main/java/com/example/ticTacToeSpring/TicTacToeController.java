package com.example.ticTacToeSpring;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

class InvalidTicTacToeInput extends RuntimeException {
    InvalidTicTacToeInput(String msg) {
        super(msg);
    }
}

@RestController
public class TicTacToeController {


    private final TicTacToeRepository ticTacToeRepository;


    public TicTacToeController(TicTacToeRepository ticTacToeRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
    }


    @PostMapping("/createamove")
    public TicTacToeMove makeAMove(@RequestParam int i, @RequestParam int j) {

        var oldMove = ticTacToeRepository.findTopByOrderByIdDesc();
        TicTacToeGame tictactoegame = oldMove.isEmpty()
                ? new TicTacToeGame()
                : new TicTacToeGame(oldMove.get().currentPlayer, TicTacToeGame.fromTicTacToeString(oldMove.get().gameTable));

        tictactoegame.makeMove(i, j);

        TicTacToeMove tictactoetable = new TicTacToeMove(tictactoegame.currentPlayer, TicTacToeMove.fromTicTacToeTable(tictactoegame.gameTable));

        return ticTacToeRepository.save(tictactoetable);
    }

}

class TicTacToeGame {


    CellStatus[][] gameTable = new CellStatus[3][3];
    Player currentPlayer; // X o O


    TicTacToeGame() {
        for (int i = 0; i < 3; i++)                 // <-
            for (int j = 0; j < 3; j++)             // <- iterate over all the cells of the board
                gameTable[i][j] = CellStatus.EMPTY; // <- Set all cells empty

        currentPlayer = Player.X;                   // <- Set the initial player
    }

    TicTacToeGame(Player currentPlayer, CellStatus[][] gameTable) {  //per old move...
        this.currentPlayer = currentPlayer;
        this.gameTable = gameTable;
    }

    public static CellStatus[][] fromTicTacToeString(String tableString) {
        CellStatus[][] table = new CellStatus[3][3];
        var row = tableString.split(";");
        for (int i = 0; i < 3; i++) {
            table[i] = Arrays.stream(row[i].split(",")).map(c -> CellStatus.valueOf(c)).toArray(CellStatus[]::new);
        }
        return table;
    }

    static private boolean isWinning(CellStatus c0, CellStatus c1, CellStatus c2) {
        return c0 != CellStatus.EMPTY && c0 == c1 && c1 == c2;
    }

    static private Optional<Player> getWinner(CellStatus cell) {
        return Optional.of(cell == CellStatus.X ? Player.X : Player.O);
    }

    public void makeMove(int i, int j) throws InvalidTicTacToeInput {
        if (i < 0 || i > 2 || j < 0 || j > 2) { // <- Check for out of bounds
            throw new InvalidTicTacToeInput("Out of Bounds");
        }
        if (gameTable[i][j] != CellStatus.EMPTY) { // <- check for already filled
            throw new InvalidTicTacToeInput("Position already used");
        }

        gameTable[i][j] = currentPlayer == Player.X ? CellStatus.X : CellStatus.O;
        currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
    }

    public void printBoard() {
        for (var row : gameTable) {
            for (var cell : row)
                System.out.print(cell == CellStatus.EMPTY ? "." : cell.toString());
            System.out.println();
        }
    }

    public Optional<Player> getTheWinner() {
        // Rows
        var g = this.gameTable;

        if (isWinning(g[0][0], g[0][1], g[0][2])) return getWinner(g[0][0]);
        if (isWinning(g[1][0], g[1][1], g[1][2])) return getWinner(g[1][0]);
        if (isWinning(g[2][0], g[2][1], g[2][2])) return getWinner(g[2][0]);

        // Columns
        if (isWinning(g[0][0], g[1][0], g[2][0])) return getWinner(g[0][0]);
        if (isWinning(g[0][1], g[1][1], g[2][1])) return getWinner(g[0][1]);
        if (isWinning(g[0][2], g[1][2], g[2][2])) return getWinner(g[0][2]);

        // Diagonals
        if (isWinning(g[0][0], g[1][1], g[2][2])) return getWinner(g[0][0]);
        if (isWinning(g[0][2], g[1][1], g[2][0])) return getWinner(g[0][2]);

        return Optional.empty();
    }

    public boolean isDraw() {
        return Arrays.stream(gameTable).flatMap(Arrays::stream).allMatch(c -> c != CellStatus.EMPTY);
    }

}
