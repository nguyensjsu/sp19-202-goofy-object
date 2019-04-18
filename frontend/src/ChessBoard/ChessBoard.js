import React, { Component } from 'react';
import style from './ChessBoard.module.css';

const BOARD_EMPTY = 0;
const BOARD_SELF = 1;
const BOARD_OPP = 2;

class ChessBoard extends Component {

    board_size = 480;
    interval = this.board_size / 16;
    points_coord = [
        { x: this.board_size / 2, y: this.board_size / 2 },
        { x: this.interval * 4, y: this.interval * 4 },
        { x: this.interval * 12, y: this.interval * 4 },
        { x: this.interval * 4, y: this.interval * 12 },
        { x: this.interval * 12, y: this.interval * 12 }
    ];
    board = [];


    componentDidMount() {
        const board = this.refs.board;
        const ctx = board.getContext("2d");

        for (let i = 1; i <= 15; i++) {
            // Grid Lines
            ctx.beginPath();
            ctx.strokeStyle = "#555"
            ctx.moveTo(this.interval * i, this.interval);
            ctx.lineTo(this.interval * i, this.board_size - this.interval);
            ctx.moveTo(this.interval, this.interval * i);
            ctx.lineTo(this.board_size - this.interval, this.interval * i);
            ctx.stroke();
        }
        // Points on the board
        for (let coord of this.points_coord) {
            ctx.beginPath();
            ctx.arc(coord.x, coord.y, 4, 0, 2 * Math.PI);
            ctx.fillStyle = "#555";
            ctx.fill()
        }

        // Initialize board array.
        for(let i = 0; i < 15; i++) {
            board[i] = [];
            for(let j = 0; j < 15; j++) {
                board[i][j] = BOARD_EMPTY;
            }
        }

    }

    onBoardClick = (e) => {
        e.preventDefault();
        console.log(e.offsetX, e.offsetY)
    }

    render() {
        return (
            <div className="ChessBoard">
                <canvas className={style.board} ref="board" width={this.board_size} height={this.board_size} onClick={this.onBoardClick} />
            </div>
        );
    }
}

export default ChessBoard;
