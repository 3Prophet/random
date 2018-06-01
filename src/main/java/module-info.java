/*
 * @(#)module-info.java Copyright (c) 2018 Supercomputing Systems AG, Schweiz. Alle Rechte vorbehalten.
 */

module ch.scs.random {
    exports ch.scs.random;
    exports ch.scs.random.utils;

    opens ch.scs.random.view to javafx.fxml;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.fxml;

}