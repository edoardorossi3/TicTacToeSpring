import React from 'react';
import ReactDOM from 'react-dom';
import axios from "axios";

const move = async (i: number, j: number) => {
    await axios.post('http://localhost:8080/createamove', {i, j})
}

let i_temp = 0
let j_temp = 0
const hello = () => console.log("hello")

const Board = () => {
    return <div>
        <table style={{textAlign: "center"}}>
            <tbody>
            <tr>
                <td>
                    <button onClick={() => move(0, 0)}>+</button>

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


