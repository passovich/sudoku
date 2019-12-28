package com.blogspot.passovich.sudoku;

import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
public class Matrix {
    public static String TAG="myLogs";
    static int difficulty;
    final static int basicMatrix[][]= {           //Базовая матрица
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 4, 8, 7, 9, 5, 6, 3, 1},
            {0, 5, 1, 7, 6, 2, 3, 9, 8, 4},
            {0, 6, 3, 9, 1, 8, 4, 7, 5, 2},
            {0, 8, 9, 6, 4, 5, 2, 1, 7, 3},
            {0, 3, 7, 2, 9, 1, 8, 4, 6, 5},
            {0, 1, 5, 4, 3, 6, 7, 2, 9, 8},
            {0, 9, 6, 5, 2, 3, 1, 8, 4, 7},
            {0, 4, 8, 1, 5, 7, 6, 3, 2, 9},
            {0, 7, 2, 3, 8, 4, 9, 5, 1, 6}
    };
    public static int solvedMatrix[][]= {          //решенная матрица
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 4, 8, 7, 9, 5, 6, 3, 1},
            {0, 5, 1, 7, 6, 2, 3, 9, 8, 4},
            {0, 6, 3, 9, 1, 8, 4, 7, 5, 2},
            {0, 8, 9, 6, 4, 5, 2, 1, 7, 3},
            {0, 3, 7, 2, 9, 1, 8, 4, 6, 5},
            {0, 1, 5, 4, 3, 6, 7, 2, 9, 8},
            {0, 9, 6, 5, 2, 3, 1, 8, 4, 7},
            {0, 4, 8, 1, 5, 7, 6, 3, 2, 9},
            {0, 7, 2, 3, 8, 4, 9, 5, 1, 6}
    };
    public static int matrix[][]= {                //матрица изменяемая пользователем
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 0, 7, 0, 5, 0, 0, 0},
            {0, 0, 1, 7, 6, 2, 0, 0, 0, 0},
            {0, 6, 0, 9, 0, 0, 4, 7, 0, 2},
            {0, 0, 9, 0, 0, 0, 0, 0, 7, 3},
            {0, 3, 0, 2, 9, 0, 8, 4, 0, 5},
            {0, 1, 5, 0, 0, 0, 0, 0, 9, 0},
            {0, 9, 0, 5, 2, 0, 0, 8, 0, 7},
            {0, 0, 0, 0, 0, 7, 6, 3, 2, 0},
            {0, 0, 0, 0, 8, 0, 9, 0, 1, 0}
    };
    public static int taskMatrix[][]= {            //матрица,которую нужно решить(неизменная во время игры)
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 0, 7, 0, 5, 0, 0, 0},
            {0, 0, 1, 7, 6, 2, 0, 0, 0, 0},
            {0, 6, 0, 9, 0, 0, 4, 7, 0, 2},
            {0, 0, 9, 0, 0, 0, 0, 0, 7, 3},
            {0, 3, 0, 2, 9, 0, 8, 4, 0, 5},
            {0, 1, 5, 0, 0, 0, 0, 0, 9, 0},
            {0, 9, 0, 5, 2, 0, 0, 8, 0, 7},
            {0, 0, 0, 0, 0, 7, 6, 3, 2, 0},
            {0, 0, 0, 0, 8, 0, 9, 0, 1, 0}
    };
    public static int auxMatrix[][][] = new int[10][10][10];
    public static int undoMatrix[][][][] = new int [10][10][10][10];
    public static int undoCounter=0;

    public  Matrix(int difficulty){
        //генерируем новые массивы матриц
        this.difficulty = difficulty;
        getSolvedMatrix(basicMatrix, solvedMatrix);
        getNonSolvedMatrix(basicMatrix, solvedMatrix, matrix);
        generateAuxMatrix(matrix, auxMatrix);
        copyMatrix(matrix, taskMatrix);
        undoCounter = 0;
    }
    public static void undoSet(int auxMatrix[][][],int undoMatrix[][][][]){
        //сдвигаем массивы auxMatrix в массиве undoMatrix вправо
        for (int y = 8; y >= 0; y--){
            for (int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    for(int k = 0; k < 10; k++){
                        undoMatrix[y+1][i][j][k] = undoMatrix[y][i][j][k];
                    }
                }
            }
        }
        //в нулевой элемент записываем новый auxMatrix
        for (int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                for(int k = 0; k < 10; k++){
                    undoMatrix[0][i][j][k] = auxMatrix[i][j][k];
                }
            }
        }
        if (undoCounter < 10) undoCounter++;
        Log.d(TAG,"undoSet, undo counter="+undoCounter);
        Log.d(TAG, toString(auxMatrix) );
    }
    public static void undoGet(int auxMatrix[][][],int undoMatrix[][][][]){
        if (undoCounter>0) {
            //берём auxMatrix из нулевой ячейки
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < 10; k++) {
                        auxMatrix[i][j][k] = undoMatrix[0][i][j][k];
                    }
                }
            }

            for (int y = 1; y < 10; y++) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        for (int k = 0; k < 10; k++) {
                            undoMatrix[y - 1][i][j][k] = undoMatrix[y][i][j][k];
                        }
                    }
                }
            }
            undoCounter--;
            Log.d(TAG, "undoGet, undo counter=" + undoCounter);
            Log.d(TAG,toString(auxMatrix) );
        }
    }
    public static void lineProcess (int auxMatrix[][][],int rowNumber){                                                                      //удалить лишние элементы доп матрицы в указанной строке
        for (int i = 1; i <= 9; i++){
            if (auxMatrix[rowNumber][i][0] != 0){
                for (int j = 1; j <= 9; j++){
                    if (j != i)
                        auxMatrix[rowNumber][j][auxMatrix[rowNumber][i][0]] = 0;
                }
            }
        }
    }
    public static void lineProcessAll(int auxMatrix[][][]){
        //Удалить лишние элементиы доп матрицы во всех строках
        for (int i = 1; i <= 9; i++){
            lineProcess(auxMatrix, i);
        }
    }
    public static void columnProcess (int auxMatrix[][][], int columnNumber){                                                                      //удалить лишние элементы доп матрицы в указанном столбце
        for (int i = 1; i <= 9; i++){
            if (auxMatrix[i][columnNumber][0] != 0){
                for (int j = 1; j <= 9; j++){
                    if (j != i)
                        auxMatrix[j][columnNumber][auxMatrix[i][columnNumber][0]] = 0;
                }
            }
        }
    }
    public static void columnProcessAll(int auxMatrix[][][]){
        //Удалить лишние элементиы доп матрицы во всех столбцах
        for (int i = 1; i <= 9; i++){
            columnProcess(auxMatrix, i);
        }
    }
    public static void squareProcess(int auxMatrix[][][], int iStart, int jStart){                                                                          //Удалить лишние элементы в квадрате с указанным началом координат
        int element = 0;
        for (int i = iStart; i <= iStart+2; i++){
            for (int j = jStart; j <= jStart + 2; j++){
                if (auxMatrix[i][j][0] != 0){
                    element = auxMatrix[i][j][0];
                    for (int k = iStart; k <= iStart + 2; k++){
                        for (int l = jStart; l <= jStart + 2; l++){
                            if ((k != i)||(l != j))
                                auxMatrix[k][l][element] = 0;
                        }
                    }
                }
            }
        }
    }
    public static void squareProcessAll(int auxMatrix[][][]){
        //Удаляем лишние элементы во всех квадратах
        for(int i = 1; i <= 7; i += 3){
            for(int j = 1; j <= 7; j += 3){
                squareProcess(auxMatrix, i, j);
            }
        }
    }
    public static void auxMatrixProcessAll (int auxMatrix[][][]){
        // решение судоку из полной дополнительной матрицы
        int counter = 0;
        int tempMatrix[][][] = new int[10][10][10];
        copyMatrix(auxMatrix, tempMatrix);
        boolean comparation = false;
        while (!comparation){              //работаем над матрицей, пока есть изменения от логики
            copyMatrix(auxMatrix, tempMatrix);
            columnProcessAll(auxMatrix);
            lineProcessAll(auxMatrix);
            squareProcessAll(auxMatrix);
            auxMatrixCheck(auxMatrix);
            counter++;
            comparation = compareMatrix(auxMatrix, tempMatrix);
        }
    }
    public static boolean auxMatrixCheck(int auxMatrix[][][]){
        //Проверка доп матрицы на вычисленные элементы
        int counter = 0;
        int element = 0;
        for(int i = 1; i <= 9; i++){
            for(int j = 1; j <= 9; j++){
                if (auxMatrix[i][j][0] == 0){
                    for(int k = 1; k <= 9; k++){
                        if (auxMatrix[i][j][k] == 0) counter++;
                        else element = auxMatrix[i][j][k];
                    }
                    if (counter == 8) auxMatrix[i][j][0] = element;
                    if (counter == 9) return false;
                    counter = 0;
                }
            }
        }
        return true;
    }
    public static void copyMatrix(int matrix1[][],int matrix2[][]){
        // копирывание матрицы 1 в матрицу 2
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                matrix2[i][j] = matrix1[i][j];
            }
        }
    }
    public static void copyMatrix(int matrix1[][][],int matrix2[][][]){
        // копирывание матрицы 1 в матрицу 2
        for (int i = 0; i < 10; i++){
            for (int j = 0; j<10; j++){
                for (int k = 0; k < 10; k++){
                    matrix2[i][j][k] = matrix1[i][j][k];
                }
            }
        }
    }
    public static boolean compareMatrix(int matrix1[][], int matrix2[][]){
        boolean answer = true;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (matrix2[i][j] != matrix1[i][j])answer = false;
            }
        }
        return answer;
    }
    public static boolean compareMatrix(int matrix1[][][], int matrix2[][]){
        boolean answer = true;
        for (int i = 0; i < 10; i++){
            for (int j=0; j<10; j++){
                if (matrix2[i][j] != matrix1[i][j][0]) answer = false;
            }
        }
        return answer;
    }
    public static boolean compareMatrix(int matrix1[][][], int matrix2[][][]){
        boolean answer = true;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                for (int k = 0; k < 10; k++){
                    if (matrix2[i][j][k] != matrix1[i][j][k]) answer = false;
                }
            }
        }
        return answer;
    }
    public static void auxMatrixGenerate(int matrix[][],int auxMatrix[][][]){
        for(int i = 1; i <= 9; i++){
            for(int j = 1; j <= 9; j++){
                if (matrix[i][j] != 0){
                    auxMatrix[i][j][matrix[i][j]] = matrix[i][j];
                    auxMatrix[i][j][0] = matrix[i][j];  //Если 0, то значение ячейки неизвестно
                }else
                    for (int k = 1; k <= 9; k++){
                        auxMatrix[i][j][k] = k;
                    }
            }
        }
    }
    public static void generateAuxMatrix(
            //формирование полной дополнительной матрицы
            int matrix[][],
            int auxMatrix[][][]
    ){
        Log.d(TAG,"generateAuxMatrix");
        for(int i = 1; i <= 9; i++){
            for(int j = 1; j <= 9; j++){
                if (matrix[i][j] != 0){
                    auxMatrix[i][j][0] = matrix[i][j];
                }else auxMatrix[i][j][0] = 0;
            }
        }
    }
    public static void generateZeroAuxMatrix(
            //формирование чистой дополнительной матрицы
            int matrix[][],
            int auxMatrix[][][]
    ){
        for(int i = 1; i <= 9; i++){
            for(int j = 1; j <= 9; j++){
                if (matrix[i][j] != 0){
                    auxMatrix[i][j][0] = matrix[i][j];
                }else if (auxMatrix[i][j][0] == 0){
                    auxMatrix[i][j][0] = -1;
                    for (int k = 1; k <= 9; k++){
                        auxMatrix[i][j][k] = k;
                    }
                }
            }
        }
    }
    public static void getSolvedMatrix (
            //перетасовка матрицы (получение новой полной не решенной матрицы)
            int basicMatrix[][],
            int solvedMatrix[][]
    ){
        copyMatrix(basicMatrix, solvedMatrix);
        Random r = new Random();
        int counter = r.nextInt(1000);
        for (int i = 0; i < counter; i++){
            int variant=r.nextInt(4);
            switch (variant){
                case 0: matrixLineExchange(solvedMatrix); break;
                case 1: matrixColumnExchange(solvedMatrix); break;
                case 2: matrixTrippleLineExchange(solvedMatrix); break;
                case 3: matrixTrippleColumnExchange(solvedMatrix); break;
            }
        }
    }
    public static void matrixLineExchange (int BaseMatrix[][]){
        //перестановка двух случайных строк в матрице судоку
        Random r = new Random();
        int numberLine = r.nextInt(3);
        int numberGroupLine = r.nextInt(3);
        int temp;
        for (int i = 1; i <= 9; i++){
            if (numberLine == 0){
                temp = BaseMatrix[1+numberGroupLine*3][i];
                BaseMatrix[1+numberGroupLine*3][i] = BaseMatrix[2+numberGroupLine*3][i];
                BaseMatrix[2+numberGroupLine*3][i] = temp;
            }
            if (numberLine == 1){
                temp = BaseMatrix[2+numberGroupLine*3][i];
                BaseMatrix[2+numberGroupLine*3][i] = BaseMatrix[3+numberGroupLine*3][i];
                BaseMatrix[3+numberGroupLine*3][i] = temp;
            }
            if (numberLine == 2){
                temp = BaseMatrix[1+numberGroupLine*3][i];
                BaseMatrix[1+numberGroupLine*3][i] = BaseMatrix[3+numberGroupLine*3][i];
                BaseMatrix[3+numberGroupLine*3][i] = temp;
            }
        }
    }
    public static void matrixColumnExchange ( int BaseMatrix[][]){
        //перестановка двух случайных столбцов в матрице судоку
        Random r = new Random();
        int numberColumn = r.nextInt(3);
        int numberGroupColumn = r.nextInt(3);
        int temp;
        for (int i = 1; i <= 9; i++){
            if (numberColumn == 0){
                temp = BaseMatrix[i][1 + numberGroupColumn * 3];
                BaseMatrix[i][1 + numberGroupColumn* 3 ] = BaseMatrix[i][2 + numberGroupColumn * 3];
                BaseMatrix[i][2 + numberGroupColumn * 3] = temp;
            }
            if (numberColumn == 1){
                temp = BaseMatrix[i][2 + numberGroupColumn * 3];
                BaseMatrix[i][2 + numberGroupColumn * 3] = BaseMatrix[i][3 + numberGroupColumn * 3];
                BaseMatrix[i][3 + numberGroupColumn * 3] = temp;
            }
            if (numberColumn == 2){
                temp = BaseMatrix[i][1 + numberGroupColumn * 3];
                BaseMatrix[i][1 + numberGroupColumn * 3] = BaseMatrix[i][3 + numberGroupColumn * 3];
                BaseMatrix[i][3 + numberGroupColumn * 3] = temp;
            }
        }
    }
    public static void matrixTrippleColumnExchange (int BaseMatrix[][]){
        //перестановка двух случайных столбцов в матрице судоку
        Random r = new Random();
        int numberTrippleColumn = r.nextInt(3);
        int temp;
        for (int i = 1; i <= 9; i++){
            if (numberTrippleColumn == 0){
                for(int j = 1; j <= 3; j++){
                    temp = BaseMatrix[i][j];
                    BaseMatrix[i][j]=BaseMatrix[i][j + 3];
                    BaseMatrix[i][j + 3]=temp;
                }
            }
            if (numberTrippleColumn == 1){
                for(int j = 1; j <= 3;j++){
                    temp = BaseMatrix[i][j + 3];
                    BaseMatrix[i][j + 3] = BaseMatrix[i][j + 6];
                    BaseMatrix[i][j + 6]=temp;
                }
            }
            if (numberTrippleColumn==2){
                for(int j = 1;j <= 3; j++){
                    temp = BaseMatrix[i][j];
                    BaseMatrix[i][j]=BaseMatrix[i][j + 6];
                    BaseMatrix[i][j + 6]=temp;
                }
            }
        }
    }
    public static void matrixTrippleLineExchange (int BaseMatrix[][]){
        //перестановка двух случайных столбцов в матрице судоку
        Random r = new Random();
        int numberTrippleLine = r.nextInt(3);
        int temp;
        for (int i = 1; i <= 9; i++){
            if (numberTrippleLine == 0){
                for(int j = 1; j <= 3; j++){
                    temp = BaseMatrix[j][i];
                    BaseMatrix[j][i] = BaseMatrix[j + 3][i];
                    BaseMatrix[j + 3][i] = temp;
                }
            }
            if (numberTrippleLine == 1){
                for(int j = 1; j <= 3; j++){
                    temp = BaseMatrix[j + 3][i];
                    BaseMatrix[j + 3][i] = BaseMatrix[j + 6][i];
                    BaseMatrix[j + 6][i] = temp;
                }
            }
            if (numberTrippleLine == 2){
                for(int j = 1; j <= 3; j++){
                    temp = BaseMatrix[j][i];
                    BaseMatrix[j][i] = BaseMatrix[j + 6][i];
                    BaseMatrix[j + 6][i] = temp;
                }
            }
        }
    }
    public static void getNonSolvedMatrix(
            //удаление лишних элементов из перетасованной матрицы
            int basicMatrix[][],
            int solvedMatrix[][],
            int nonSolvedMatrix[][]
    ){
        // 30-35 элементов - легко - 51 - 46 удалений
        // 25-30 элементов - средне -56 - 51 удалений
        // 20-25 элементов - сложно - 61 - 56 удалений
        getSolvedMatrix(basicMatrix, solvedMatrix);
        List<Element> elements = new ArrayList<>(100);
        int counter = 0;
        //получаем линейное представление матрицы в виде списка
        for (int i = 1; i <= 9; i++){
            for (int j = 1; j <= 9; j++){
                Element element = new Element();
                element.i = i;
                element.j = j;
                element.element = solvedMatrix[i][j];
                elements.add(element);
                counter++;
            }
        }
        //пытаемся удалить лишние эл-ты и получить матрицу для решения
        Random r = new Random();
        int size = elements.size();
        //Перемешивем случайным образом матрицу (линейный список)
        for (int i = 0; i < 1000; i++){
            int element1, element2;
            Element temp1 = new Element();
            Element temp2 = new Element();
            element1 = r.nextInt(size);
            element2 = r.nextInt(size);
            temp1=elements.get(element1);
            temp2=elements.get(element2);
            elements.set(element1,temp2);
            elements.set(element2,temp1);
        }
        int tempMatrix[][] = new int[10][10];
        int tempAuxMatrix[][][] = new int [10][10][10];
        //получаем временную матрицу
        copyMatrix(solvedMatrix,tempMatrix);

        //пытаемся удалить максимум элементов из временной матрицы
        copyMatrix(solvedMatrix,tempMatrix);
        int elementsSize = elements.size();
        counter=0;
        int maxDeletes=0;
        switch (difficulty){
            case 1:maxDeletes = 46 + r.nextInt(6); break;
            case 2:maxDeletes = 51 + r.nextInt(6); break;
            case 3:maxDeletes = 81; break;
        }
        for (int k = 0; k < elements.size(); k++){
            tempMatrix[elements.get(k).i][elements.get(k).j] = 0;
            for (int i1 = 0; i1 < 10; i1++){
                for (int j1 = 0; j1 < 10; j1++){
                    for (int k1 = 0; k1 < 10; k1++){
                        tempAuxMatrix[i1][j1][k1] = 0;
                    }
                }
            }
            auxMatrixGenerate(tempMatrix, tempAuxMatrix);
            auxMatrixProcessAll(tempAuxMatrix);
            if ((winnerCheck(tempAuxMatrix, solvedMatrix)) && (counter <= maxDeletes)){
                tempMatrix[elements.get(k).i][elements.get(k).j] = 0;
                counter++;
            }
            else tempMatrix[elements.get(k).i][elements.get(k).j] = elements.get(k).element;
        }
        Log.d(TAG,"counter="+counter);
        //возвращаемся к двумерной матрице
        copyMatrix(tempMatrix,nonSolvedMatrix);
    }
    public static boolean winnerCheck(int auxMatrix[][][],int solvedMatrix[][]){
        for(int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (auxMatrix[i][j][0] != solvedMatrix[i][j]){return false;}
            }
        }
        return true;
    }
    public static boolean checkForEndGame(int auxMatrix[][][]){
        for(int i = 1; i <=9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (auxMatrix[i][j][0] <= 0) return false;
            }
        }
        return true;
    }
    private static class Element {
        //класс для одного элемента матрицы с координатами (для метода создания)
        int i,j,element;
        public Element(){
            this.i=0;
            this.j=0;
            this.element=0;
        }
    }
    public static void elementAuxMatrixReset (
            int iMatrix,
            int jMatrix,
            int auxMatrix[][][]
    ){
        for (int i = 1; i <= 9; i++){
            auxMatrix[iMatrix][jMatrix][i] = 0;
        }
    }
    public static void removeButtonNumberFromAuxMatrix(
            int iMatrix,
            int jMatrix,
            int buttonNumber,
            int auxMatrix[][][]
    ){
        for (int i = 1; i <= 9; i++) {
            if (auxMatrix[i][jMatrix][buttonNumber] == buttonNumber) {
                auxMatrix[i][jMatrix][buttonNumber] = 0;    //убрать элемент из строки
            }
            if (auxMatrix[iMatrix][i][buttonNumber] == buttonNumber)
                auxMatrix[iMatrix][i][buttonNumber] = 0;    //убрать элемент из столбца
        }
        //убрать элемент из квадрата
        //было удаление лишних элементов в квадрате
        //сначала нужно определить квадрат
        int i1 = 0, j1 = 0;
        if (iMatrix <= 3) i1 = 1;
        else if (iMatrix <= 6) i1 = 4;
        else i1 = 7;
        if (jMatrix <= 3) j1 = 1;
        else if (jMatrix <= 6) j1 = 4;
        else j1 = 7;
        //удалить элемент из квадрата
        for (int i = i1; i <= i1 + 2; i++) {
            for (int j = j1; j <= j1 + 2; j++) {
                if (auxMatrix[i][j][buttonNumber] == buttonNumber)
                    auxMatrix[i][j][buttonNumber] = 0;
            }
        }
    }
    public static int checkAuxMatrixForLastElement(
            int iMatrix,
            int jMatrix,
            int auxMatrix[][][]
    ){
        int counter = 0;
        int element = 0;
        for (int k = 1; k <= 9; k++){
            if (auxMatrix[iMatrix][jMatrix][k] > 0){
                counter++;
                element=auxMatrix[iMatrix][jMatrix][k];
            }
        }
        if (counter == 1)return element;
        else return 0;
    }
    public static String toString(int auxMatrix[][][]){
        String stringMatrix="";
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                for(int k = 0; k<10; k++){
                    stringMatrix = stringMatrix + Integer.toString(auxMatrix[i][j][k]);
                }
            }
        }
        return stringMatrix;
    }
    public static String toString(int Matrix[][]){
        String stringMatrix="";
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                stringMatrix = stringMatrix + Integer.toString(Matrix[i][j]);
            }
        }
        return stringMatrix;
    }
    public static void fromString(String stringMatrix, int Matrix[][]){
        int counter = 0;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                    char ch = stringMatrix.charAt(counter);
                    if (ch == '-'){Matrix[i][j] = -1; counter++;}
                    else {Matrix[i][j] = Character.getNumericValue(ch);}
                    counter++;
            }
        }
        Log.d(TAG,"matrix getted from string = "+stringMatrix);
    }
    public static void fromString(String stringMatrix, int auxMatrix[][][]){
        int counter = 0;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                for(int k = 0; k<10; k++){
                    char ch = stringMatrix.charAt(counter);
                    if (ch == '-'){auxMatrix[i][j][k] =-1; counter++;}
                    else {auxMatrix[i][j][k] = Character.getNumericValue(ch);}
                    counter++;
                }
            }
        }
        Log.d(TAG,"matrix getted from string = "+stringMatrix);
    }
    public static boolean checkAllElementsSolved(int auxMatrix[][][]){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (auxMatrix[i][j][0] <= 0) return false;
            }
        }
        return true;
    }
}

