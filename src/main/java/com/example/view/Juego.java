package com.example.view;

import com.example.controller.ControllerBosque;
import com.example.controller.ControllerDragon;
import com.example.controller.ControllerHechizos;
import com.example.controller.ControllerMago;
import com.example.controller.ControllerMonstruo;
import com.example.model.AtaqueBasico;
import com.example.model.BolaFuego;
import com.example.model.BolaNieve;
import com.example.model.Mago;
import com.example.model.Monstruo;
import com.example.model.Rayo;
import com.example.model.TipoMonstruo;

public class Juego {
    
    public void configInicial() {
        
        ControllerBosque cBosque = new ControllerBosque();
        ControllerDragon cDragon = new ControllerDragon();
        ControllerHechizos cHechizos = new ControllerHechizos();
        ControllerMago cMago = new ControllerMago();
        ControllerMonstruo cMonstruo = new ControllerMonstruo();

        //Inicializacion de hechizos
        cHechizos.inicializarHechizos();
        
        BolaFuego bolaFuego = cHechizos.obtenerBolaFuego();
        BolaNieve bolaNieve = cHechizos.obtenerBolaNieve();
        Rayo rayo = cHechizos.obtenerRayo();
        AtaqueBasico ataqueBasico = cHechizos.obtenerAtaqueBasico();

        //Inicialización de los magos y sus hechizos
        Mago HarryPotter = cMago.guardarMago("Harry Potter", 200, 3);
        Mago Judini = cMago.guardarMago("Judini", 180, 2);

        cMago.anadirHechizo(HarryPotter.getId(), bolaFuego.getId());
        cMago.anadirHechizo(HarryPotter.getId(), rayo.getId());

        cMago.anadirHechizo(Judini.getId(), ataqueBasico.getId());
        cMago.anadirHechizo(Judini.getId(), bolaFuego.getId());


        //Inicialización de los 3 monstruos
        Monstruo ogroPepe = cMonstruo.guardarMonstruo("Pepe", 400, TipoMonstruo.ogro, 20);
        Monstruo trollJofrey = cMonstruo.guardarMonstruo("Jofrey", 450, TipoMonstruo.troll, 16);
        Monstruo espectroBenjen = cMonstruo.guardarMonstruo("Benjen", 300, TipoMonstruo.espectro, 30);

        //Inicialización del dragón
        cDragon.guardarDragon("Viserys", 50, 1000);
        

    }


}
