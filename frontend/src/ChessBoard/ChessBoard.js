import React, { Component } from 'react';
import style from './ChessBoard.module.css';

class ChessBoard extends Component {

    board_size = 480;
    interval = this.board_size / 16;

    componentDidMount() {
        const board = this.refs.board;
        const ctx = board.getContext("2d");

        for (let i = 1; i <= 15; i++) {
            // Vertical Lines
            ctx.beginPath();
            ctx.strokeStyle = "#555"
            ctx.moveTo(this.interval * i, this.interval);
            ctx.lineTo(this.interval * i, this.board_size - this.interval);
            ctx.stroke();
            
            ctx.strokeStyle = "#555"
            ctx.moveTo(this.interval, this.interval * i);
            ctx.lineTo(this.board_size - this.interval, this.interval * i);
            ctx.stroke();
        }
    }

    render() {
        return (
            <div className="ChessBoard">
                <canvas className={style.board} ref="board" width={this.board_size} height={this.board_size} />
            </div>
        );
    }
}

export default ChessBoard;
