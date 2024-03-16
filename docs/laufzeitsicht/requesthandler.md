# RequestHandler

Im folgenden Diagramm kann die Kommunikation zwischen der verwendeten Schach-GUI und Flengine eingesehen werden. Hierbei wird im Gegensatz zum allgemeinen UCI-Ablauf genauer auf die von Flengine unterstützen Features wie unterschiedliche Optionen und den Debug-Modus eingegangen.

![](./attachments/UCI%20RequestHandler.png)

## Initialisierungsphase

Während der Initilaisierungsphase übergibt der RequestHandler den Enginenamen und den Autorennamen an die GUI. Dazu gibt er die Einstellungsmöglichkeiten, welche die Engine bietet (Difficulty und RecursiveDepth), an. “uciok“ beendet die Intialisierung.

## Debug-Mode

Über den Befehl “debug“ kann der Debug-Mode der Engine an- und ausgeschaltet werden. Wenn aktiviert sendet die Engine über “info“-Befehle Debuginformationen an die GUI, welche diese anzeigt. Im Sequenzdiagramm ist dies anhand der “info“-Nachricht, dass der Debug-Mode aktiviert ist, aufgeführt.