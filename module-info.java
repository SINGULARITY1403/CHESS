module CHESS.main
{
    requires guava;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    opens src.sample;
    opens src.engine;
    opens src.gui;
}