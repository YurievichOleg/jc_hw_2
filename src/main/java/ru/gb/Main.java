package ru.gb;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int a = 5;
        int b = 5;
        initialize(a,b);
        printField();
        int count = 0;
        int busy = 0;
        while (count == 0){
            int empty = 0;

            moveHuman();
            if(busy != field.length * field[0].length - 1){
                moveComp();
                System.out.println(busy);
            }

            printField();

            for(int i = 0; i < field.length; i ++){
                for (int j = 0; j < field[0].length; j ++){

                    if(winTheGame(i,j,WIN,DOT_HUMAN) && count == 0) {
                        System.out.println("WinWinWin!!!");
                        count = 1;

                    }
                    if(winTheGame(i,j,WIN,DOT_COMP) && count == 0){
                        System.out.println("Loose!");
                        count = 2;
                    }
                    if(!isEmpty(i,j)){
                        empty ++;
                    }
                }
            }
            busy = empty;
            if(empty == field.length * field[0].length){
                System.out.println("Nobody.");
                count = 3;
            }
        }


    }


    private static final char DOT_HUMAN = 'X';
    private static final char DOT_COMP = 'O';
    private static final char DOT_EMPTY = '•';

    private static final int WIN = 4;

    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static void initialize(int a, int b){
        fieldSizeX = a;
        fieldSizeY = b;
        field = new char[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeX; i ++){
            for (int j = 0; j < fieldSizeY; j++){
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printField(){
        System.out.print("+");
        for (int i = 1; i < fieldSizeX +1; i ++){
            System.out.print(String.format("-%d",i));
            if(i == fieldSizeX) System.out.print("-");
        }
        System.out.println();
        for (int i = 1; i < fieldSizeY + 1; i ++){
            System.out.print(i + "|");
            for (int j = 0; j < fieldSizeX; j++){
                System.out.print(field[j][i-1] + "|");
            }
            System.out.println();
        }
        for(int i = 0; i < fieldSizeX + 1; i ++){
            System.out.print("--");
        }
        System.out.println();
    }

    private static boolean isEmpty(int a, int b){
        return field[a][b] == DOT_EMPTY;
    }

    /**
     * Метод для проверки полной занятости игрового поля
     * @return
     */
    private static boolean isOccupied(){

        for (int i = 0; i < field.length; i ++){
            for(int j = 0; j < field[0].length; j ++){
                if(field[i][j] == DOT_EMPTY){
                    return false;
                }
            }
        }
        return true;
    }
    private static boolean isCellValid(int a, int b){
        return a >= 0 && a < field.length && b >= 0 && b < field[0].length;
    }

    /**
     * Ход игрока
     */
    private static void moveHuman(){
        int x,y;
        do{
            System.out.println("Введите координаты поля по горизонтали:");
            x = scanner.nextInt() - 1;
            System.out.println("Введите координаты поля по вертикали:");
            y = scanner.nextInt() - 1;
        }while (!isCellValid(x,y) || !isEmpty(x,y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Ход машины
     */
    private static void moveComp(){
        int count = 0;
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[0].length; j++){
                if(isEmpty(i,j)){
                    field[i][j]=DOT_HUMAN;
                    for(int a = 0; a < field.length; a++){
                        for (int b = 0; b < field[0].length; b++){
                            if(winTheGame(a,b,WIN,DOT_HUMAN) && count==0){
                                field[i][j] = DOT_COMP;
                                System.out.println("here");
                                count++;
                                break;

                            }
                        }
                    }
                    if (field[i][j] != DOT_COMP){
                        field[i][j] = DOT_EMPTY;

                    }

                }
            }
        }
        if (count == 0){
            int x,y;
            do{
                x = random.nextInt(field.length);
                y = random.nextInt(field[0].length);
            }while (!isEmpty(x,y));
            field[x][y] = DOT_COMP;
        }

    }

    /**
     * проверка на выйгрышную комбинацию по диагонали вверх
     * @param x координата начальной точки по горизонтали
     * @param y координата начальной точки по вертикали
     * @param win необходимое количество занятых полей для выйгрыша
     * @return boolean
     */
    private static boolean winUpDiagonally(int x, int y, int win, char sign){
        if(x + (win - 1) < field.length && y - (win -1) >=0){
            int count = 0;
            for (int i = 0; i < win; i ++){
                if(field[x + i][y - i] == sign){
                    count++;
                    if(count == win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * проверка на выйгрышную комбинацию по диагонали вниз
     * @param x координата начальной точки по горизонтали
     * @param y координата начальной точки по вертикали
     * @param win необходимое количество занятых полей для выйгрыша
     * @return boolean
     */
    private static boolean winDownDiagonally(int x, int y, int win, char sign){
        if(x + (win - 1) < field.length && y + (win -1) < field[0].length){
            int count = 0;
            for (int i = 0; i < win; i ++){
                if(field[x + i][y + i] == sign){
                    count++;
                    if(count == win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * проверка на выйгрышную комбинацию вправо
     * @param x координата начальной точки по горизонтали
     * @param y координата начальной точки по вертикали
     * @param win необходимое количество занятых полей для выйгрыша
     * @return boolean
     */
    private static boolean winRight(int x, int y, int win, char sign){
        if(x + (win - 1) < field.length ){
            int count = 0;
            for (int i = 0; i < win; i ++){
                if(field[x + i][y] == sign){
                    count++;
                    if(count == win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * проверка на выйгрышную комбинацию вниз
     * @param x координата начальной точки по горизонтали
     * @param y координата начальной точки по вертикали
     * @param win необходимое количество занятых полей для выйгрыша
     * @return boolean
     */
    private static boolean winDown(int x, int y, int win, char sign){
        if(y + (win - 1) < field[0].length ){
            int count = 0;
            for (int i = 0; i < win; i ++){
                if(field[x][y + i] == sign){
                    count++;
                    if(count == win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Метод объединяющий все предыдущие проверки на выйгрыш
     * @param a координаты по горизонтали
     * @param b координаты по вертикали
     * @param win необходимая длина выйгрышной комбинации
     * @param sign какой знак будет принимать метод за искомый
     * @return
     */
    private static boolean winTheGame(int a, int b, int win, char sign){
        if (winDownDiagonally(a,b,win,sign) || winUpDiagonally(a,b,win,sign) || winRight(a,b,win,sign) || winDown(a,b,win,sign)){
            return true;
        }
        return false;
    }

}
