# Aufbau

Die Flengine ist ein Java-Programm, welches mittels einer Main-Methode ausgeführt werden kann. Zusätzlich dazu wird eine Batch-Datei zur Verfügung gestellt, die besagtes Programm ausführt.

Folgende Komponenten besitzt die Schachengine:

-   Implementierung der Schachregeln
-   Ermittlung der Züge mittels mehreren Zuggebern ([Zugermittlung](../../docs/laufzeitsicht/zugermittlung.md))
-   Anbindung eines User-Interfaces mittels UCI-Protokoll
-   Converter der Eingabe in ein entwickeltes Datenformat

Der Austausch der Daten zwischen den Komponenten erfolgt durch eigene Datenmodelle, die in [Domänenmodell](../../docs/querschnittliche-konzeption/domnenmodell.md) aufgeführt sind.

Mittels der Teilung in verschiedene Komponenten können diese ausgetauscht werden.
