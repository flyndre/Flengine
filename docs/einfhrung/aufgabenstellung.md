# Aufgabenstellung

# Was ist Flengine?

Flengine ist eine vollständige Schachengine. Sie verwendet die Eröffnungs- sowie die Endspieldatenbank von Leechess. Im entsprechenden Mittelspiel wird der Minimax-Algorithmus verwendet, um Züge zu bewerten bzw. zu ziehen.

# Features

*   Überprüft erhaltene Züge
    
*   Zieht eigene Züge
    
*   Unterstützt UCI-Protokoll
    
    *   Eingabe über Stdin
        
    *   Ausgabe über Stdout
        
    *   Eingabe von Befehlen muss immer! möglich sein, auch wenn Berechnungen ausgeführt werden
        
    *   nichtdefinierte Eingaben werden ignoriert und die Engine versucht den Rest des Strings zu interpretieren
        
    *   Details siehe Spezifikation: [https://www.shredderchess.com/download/div/uci.zip](https://www.shredderchess.com/download/div/uci.zip)