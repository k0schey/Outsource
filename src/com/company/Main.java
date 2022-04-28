package com.company;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Main
{
    //
    public static void main(String[] args) throws IOException
    {
        int[] n = new int[20];
        // файл произвольного прямого доступа к произвольном байту
        RandomAccessFile d = new RandomAccessFile("123.txt", "rw");

        // заполняем массив индексов номерами
        for (int i = 0; i < n.length; i++)
        {
            n[i] = i;
            System.out.printf("%4d", i); // по 4 позциции на элемент
        }
        System.out.println();

        // записываем случайные числа в файл
        // рандомная запись (int) чтобы округлять из лонга 8 байт в int 4 байта
        for (int i = 0; i < n.length; i++) d.write((int) Math.round(65 + Math.random() * 25));
        // файл может оказаться большего размера
        d.setLength(n.length);
        // переход в начало
        d.seek(0);

        // выводим на экран записи из файла
        for (int i = 0; i < n.length; i++) System.out.printf("%4d", d.read());
        System.out.println();

        // сортировка индексами
        sortInd(d, n);

        for (int i : n)
        {
            d.seek(i);
            System.out.printf("%4d", d.read());
        }
        d.close();
        System.out.println();

        Peasant[] peasants = new Peasant[20];
        for (int i = 0; i < peasants.length; i++)
        {
            peasants[i] = new Peasant(String.valueOf(i), (int)(Math.random()*20));
            System.out.printf("%s %4d \n", peasants[i].name, peasants[i].clas);
        }
   }

    public static void sortInd(RandomAccessFile f, int[] ind) throws IOException
    {
        byte x = 0,y = 0; int k = 0;
        for (int i = 0; i < ind.length-1; i++)
        {
            // ищем i элемент
            f.seek(ind[i]);
            // сохраняем порядковый номер
            x = f.readByte();
            k = i;

            for (int j = i+1; j < ind.length; j++)
            {
                f.seek(ind[j]);
                y = f.readByte();
                if (y < x)
                {
                    k = j; x = y;
                }
            }

            int e = 0;
            e = ind[i];
            ind[i] =ind[k];
            ind[k] = e;
        }
    }
}

class Peasant
{
    String name;
    int clas;

    Peasant(String n, int c)
    {
        name = n;
        clas = c;
    }
}
