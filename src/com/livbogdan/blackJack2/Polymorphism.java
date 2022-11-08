package com.livbogdan.blackJack2;

import static java.lang.System.out;

public class Polymorphism
{
    public static void main(String[] args)
    {
        Cat kasper = new Cat();
        Dog dog = new Dog();

        out.println(kasper);
        out.println(dog);
    }
}
