/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.madeinorbit.server;

import com.madeinorbit.server.controller.ServerController;

/**
 *
 * @author Kuba Rodak
 * @author Mykhailo Fedenko
 */
public class Server {
    static ServerController serverController;

    public static void main(String[] args) {
        serverController = new ServerController();

        serverController.runLoop();
    }
}
