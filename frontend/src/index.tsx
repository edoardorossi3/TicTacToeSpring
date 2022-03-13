import React from 'react';
import ReactDOM from 'react-dom';
import axios from "axios";

enum Cell { X = 'X', O = 'O', Empty = 'Empty' }

enum Player { X = 'X', O = 'O' }

type TicTacToeMove = {
    player: Player,
    board: Cell[][]
}

const newGame = () => axios.get('http://localhost:8080/createtable').then(res => res.data);
const makemove = (i: number, j: number) => axios.post(`http://localhost:8080/move/${i}/${j}`).then(res => res.data)


const Board = () => {
    const [move, setMove] = React.useState<TicTacToeMove | null>(null);
   
    return <div>

        <button onClick={newGame}>New Game</button>
        <table>
            <tbody>
            <tr>
                <td>
                    <button onClick={() => makemove(0, 0).then(setMove)}>+</button>

                </td>
                <td>
                    <button>+</button>
                </td>
                <td>
                    <button>+</button>
                </td>
            </tr>
            <tr>
                <td>
                    <button>+</button>
                </td>
                <td>
                    <button>+</button>
                </td>
                <td>
                    <button>+</button>
                </td>
            </tr>
            <tr>
                <td>
                    <button>+</button>
                </td>
                <td>
                    <button>+</button>
                </td>
                <td>
                    <button>+</button>
                </td>
            </tr>
            </tbody>

        </table>

    </div>
}

ReactDOM.render(
    <React.StrictMode>
        <Board></Board>
    </React.StrictMode>,
    document.getElementById('root')
);


