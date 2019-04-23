import React, { Component } from 'react';
import style from './ChessBoard.module.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import config from '../config'
import cookie from 'react-cookies';
import { SINGLE_MODE, BATTLE_MODE } from '../ModeSelection/ModeSelection';

const BOARD_EMPTY = 0;
const BOARD_SELF = 1;
const BOARD_OPP = 2;
const COLOR_BLACK = { center: "#999", edge: "black" };
const COLOR_WHITE = { center: "white", edge: "#ccc" };

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
    board_matrix = [];

    my_color = COLOR_BLACK;
    op_color = COLOR_WHITE;
    isMe = false;
    game_mode = this.props.location.state.mode;

    stompClient = {};

    componentDidMount() {

        // console.log("Game Mode:", this.game_mode === SINGLE_MODE)

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
            this.board_matrix[i] = [];
            for (let j = 0; j < 15; j++) {
                this.board_matrix[i][j] = BOARD_EMPTY;
            }
        }

        let socket = new SockJS(config.host + '/ws-game');
        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, (frame) => {
            console.log("Connected:", frame);
            if (this.game_mode === BATTLE_MODE) {
                this.subscribeToBattle(this.stompClient);

            }
            this.stompClient.send('/app/addToQueue', {}, JSON.stringify({ username: cookie.load('username') }))
        });

    }

    subscribeToBattle = (stompClient) => {

        stompClient.subscribe('/topic/added?' + cookie.load('username'), (res) => {
            //status code: OK(202),FAIL(400)
            console.log("Topic add:", JSON.parse(res.body));
        });
        stompClient.subscribe('/topic/join?' + cookie.load('username'), (res) => {
            //status code: Black(220), White(230),
            //black first hand
            let body = JSON.parse(res.body);
            console.log("Topic join", body);

            if (body.status === 400) {
                console.log("Joing Error:")
            } else if (body.status === 220) {
                this.my_color = COLOR_BLACK;
                this.op_color = COLOR_WHITE;
                this.isMe = true;
            } else if (body.status === 230) {
                this.my_color = COLOR_WHITE;
                this.op_color = COLOR_BLACK;
                this.isMe = false;
            }
        });

        stompClient.subscribe('/topic/update?' + cookie.load('username'), (res) => {
            //if has status code: OK(202), WIN(211), LOSE(212)
            //else: no status means a update move from opponent
            let body = JSON.parse(res.body);
            console.log("Topic update", body);
            if (body.status === 400) {
                console.log("Joing Error:")
            } else if (body.status === 211) {
                console.log("You Win!")
            } else if (body.status === 212) {
                console.log("You Lose!")
            } else if (body.status === 202) {
                console.log("Opponent Move:", body);
                this.drawPiece(this.getCanvasCoord(body.obj.x), this.getCanvasCoord(body.obj.y), this.isMe);
            }
        });


    }

    onBoardClick = (e) => {
        e.preventDefault();
        if (!this.isMe) return;
        let rect = e.target.getBoundingClientRect();
        // console.log(e.clientX - rect.left, e.clientY - rect.top);
        let coord = this.getCoord(e.clientX - rect.left, e.clientY - rect.top);
        console.log(coord.i, coord.j);
        if (coord.i < 0 || coord.i > 14 || coord.j < 0 || coord.j > 14 || this.board_matrix[coord.i][coord.j] != 0) return;
        this.board_matrix[coord.i][coord.j] = this.isMe ? BOARD_SELF : BOARD_OPP;
        this.drawPiece(this.getCanvasCoord(coord.i), this.getCanvasCoord(coord.j), this.isMe);

        var move = { "username": cookie.load('username'), "x": coord.i, "y": coord.j };
        this.stompClient.send("/app/putPiece", {}, JSON.stringify(move));

        console.log('Current Board:', this.board_matrix);
    }

    getCoord = (x, y) => {
        let i = Math.floor((x + this.interval / 2 - 20 + this.interval) / this.interval - 2);
        let j = Math.floor((y + this.interval / 2 - 20 + this.interval) / this.interval - 2);
        // console.log(i, j);
        return { i, j }
    }

    getCanvasCoord = x => this.interval + this.interval * x;

    drawPiece = (x, y, role) => {
        console.log("Draw piece", x, y)
        const board = this.refs.board;
        const ctx = board.getContext("2d");

        ctx.beginPath();
        ctx.arc(x, y, this.interval / 2.1, 0, 2 * Math.PI);

        var grd = ctx.createRadialGradient(x - 3, y - 3, 1, x - 2, y - 2, 15);
        grd.addColorStop(0, role ? this.my_color.center : this.op_color.center);
        grd.addColorStop(1, role ? this.my_color.edge : this.op_color.edge);

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
