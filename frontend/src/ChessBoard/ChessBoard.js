import React, { Component } from 'react';
import style from './ChessBoard.module.css';

class ChessBoard extends Component {

    componentDidMount() {
        const board = this.refs.board;
    }

    render() {
        return (
            <div className="ChessBoard">
                <canvas className={style.board} ref="board" width={480} height={480} />
            </div>
        );
    }
}

export default ChessBoard;
