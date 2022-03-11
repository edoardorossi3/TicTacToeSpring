package com.example.ticTacToeSpring;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicTacToeRepository extends CrudRepository<TicTacToeMove, Long> {

    public Optional<TicTacToeMove> findTopByOrderByIdDesc();
}
