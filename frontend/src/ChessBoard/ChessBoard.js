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
    isMe = false;


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
        for (let i = 0; i < 15; i++) {
            board[i] = [];
            for (let j = 0; j < 15; j++) {
                board[i][j] = BOARD_EMPTY;
            }
        }

    }

    onBoardClick = (e) => {
        e.preventDefault();
        let rect = e.target.getBoundingClientRect();
        // console.log(e.clientX - rect.left, e.clientY - rect.top);
        let coord = this.getCoord(e.clientX - rect.left, e.clientY - rect.top);
        console.log(coord.i, coord.j);
        if (coord.i < 0 || coord.i > 14 || coord.j < 0 || coord.j > 14 || this.board[coord.i][coord.j] != 0) return;
        this.board[coord.i][coord.j] = this.isMe ? BOARD_SELF : BOARD_OPP;
        this.drawPiece(this.interval + this.interval * coord.i, this.interval + this.interval * coord.j, this.isMe);
    }

    getCoord = (x, y) => {
        let i = Math.floor((x + this.interval / 2 - 20 + this.interval) / this.interval - 2);
        let j = Math.floor((y + this.interval / 2 - 20 + this.interval) / this.interval - 2);
        // console.log(i, j);
        return { i, j }
    }

    drawPiece = (x, y, role) => {
        console.log("Draw piece", x, y)
        const board = this.refs.board;
        const ctx = board.getContext("2d");

        ctx.beginPath();
        ctx.arc(x, y, this.interval / 2.1, 0, 2 * Math.PI);

        var grd = ctx.createRadialGradient(x - 3, y - 3, 1, x - 2, y - 2, 15);
        grd.addColorStop(0, role ? "#999" : "white");
        grd.addColorStop(1, role ? "black" : "#ccc");

        // ctx.fillStyle = "#222";
        ctx.fillStyle = grd;
        ctx.shadowOffsetX = 2
        ctx.shadowOffsetY = 2
        ctx.shadowColor = "#555"
        ctx.shadowBlur = 5
        ctx.fill()

        this.isMe = !role;
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
