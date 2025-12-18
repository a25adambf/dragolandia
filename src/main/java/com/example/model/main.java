package com.example.model;

import java.lang.ModuleLayer.Controller;

import com.example.controller.ControllerMago;

public class main {
    

    public static void main(String[] args) {

        ControllerMago cm = new ControllerMago();
        
        boolean mago = cm.guardarMago("mago",40,5);

        Dragon dragon = new Dragon();

        Monstruo monstruo = new Monstruo();

        Bosque bosque = new Bosque();
    }
}
