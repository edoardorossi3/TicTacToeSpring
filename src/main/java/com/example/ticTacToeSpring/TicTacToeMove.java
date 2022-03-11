package com.example.ticTacToeSpring;

import javax.persistence.*;
import java.util.Arrays;
import java.util.stream.Collectors;

enum CellStatus {EMPTY, X, O}

enum Player {X, O}

@Entity
public class TicTacToeMove {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Player currentPlayer;

    public String gameTable;

    public TicTacToeMove(){}

    public TicTacToeMove(Player currentPlayer, String gameTable) {
        this.currentPlayer = currentPlayer;
        this.gameTable = gameTable;
    }

    public static String fromTicTacToeTable(CellStatus[][] table){
        return Arrays.stream(table)
                .map(row-> Arrays.stream(row).map(cell->cell.toString()).collect(Collectors.joining(",")))
                .collect(Collectors.joining(";"));
    }


    public Long getId() {return id;}

}
