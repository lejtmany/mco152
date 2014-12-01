package pong;
/*
 * Ben Forgy
 * Nov 8, 2014
 */

interface Context {
    Ball getBall();
    Board getBoard();
    Paddle getPaddle();
}
