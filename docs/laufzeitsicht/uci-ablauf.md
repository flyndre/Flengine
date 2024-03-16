# UCI-Ablauf

Das folgende Diagramm beschreibt beispielhaft den Ablauf der Kommunikation zwischen einer GUI und einer Engine mithilfe des UCI-Protokolls. Dabei ist zu beachten, dass die Reihenfolge vieler Befehle in der Praxis abweichen kann, da dies im Protokoll nicht festgelegt ist.

![](./attachments/Ablauf%20UCI-Protokoll.png)

## Initialisierungsphase

Im ersten Schritt initialisiert die GUI die Kommunikation mit der Engine über den Befehl “uci“. Daraufhin sendet letztere verschiedene Informationen über sich selbst an die GUI zurück. Dies beinhaltet neben einigen Informationen wie dem Namen und dem Autor der Engine auch sämtliche möglichen Einstellungen, welche vom Nutzer geändert werden können. Dies wird mit dem “option“-Befehl realisiert. Die Initialisierungsphase endet mit der Nachricht “uciok“ seitens der Engine an die GUI.

## Engine-Einstellungen ändern

Mittels des Befehls “setoption“ kann die GUI eine Einstellung der Engine ändern. Dies kann nach der Initialisierungsphase zu jeder Zeit geschehen.

## Berechnung durchführen

Der Befehl “ucinewgame“ markiert den Beginn einer neuen Berechnung, ist aber nicht zwingend notwendig. Eine neue Berechnung kann ebenfalls durch den Befehl “position“ gestartet werden, dieser ist zwingend, auch nach “ucinewgame“, notwendig. Er enthält die Stellung, von welcher aus die Engine die Berechnung des nächsten Zuges durchführen soll. Der “go“-Parameter startet die Berechnung. Die Engine gibt das Ergebnis über “bestmove“ an die GUI zurück.

## Debug-Informationen an GUI senden

Die Engine kann zu jeder Zeit Debuginformationen über den “info“-Befehl an die GUI senden. Dies kann ein beliebiger Text sein, die Anzeige für den Nutzer übernimmt die GUI.