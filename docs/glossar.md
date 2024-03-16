# Glossar

### BAT-Datei

Eine ausführbare Datei unter Windows. Wird zur Integration der Flengine Jar-Datei in ein Frontend verwendet.

### Computergegner

Ein virtueller Gegner, der durch einen Computer simuliert wird. Die Flengine agiert hier als Gegner, der Züge zurückgibt.

### Controller / Enginecontroller

Teil der Architektur von Flengine. Agiert als Schnittstelle zwischen den verschiedenen Komponenten.

[Subsystem Controller](../flengine/bausteinsicht/ebene-2/subsystem-controller.md)

### Converter

Teil der Architektur von Flengine. Konvertiert die Eingabe zum Datenmodell der Flengine.

[Subsystem Converter](../flengine/bausteinsicht/ebene-2/subsystem-converter.md)

### CuteChess

Ein Frontend für eine Schachengine, die UCI Unterstützen

### Endspieldatenbank

Eine Datenbank, die bei einer angegebenen Stellung den besten Zug im Endspiel zurückgibt.

### Engine

Eine Software, die das Spielen gegen einen Computergegner ermöglicht.

### Eröffungsdatenbank

Eine Datenbank, die bei einer angegebenen Stellung den besten Zug in der Eröffnung zurückgibt.

### Flengine

Der Name der hier entwickelten Schachengine

### Frontend

Eine Software, die meist mittels eines User Interface mit dem Spieler interagiert.

### JAR-Datei

Eine Datei, welches mittels JRE ausgeführt werden kann.

### Java Runtime Enviroment (JRE)

Die Laufzeitumgebung in der alle Java-Programme ausgeführt werden

### Menschlicher Spieler

Ein realer Mensch, welcher über ein Frontend die Schachengine nutzt

### Mittelspiel

Der Teil einer Schachpartie, für den weder die Eröffungsdatenbank noch die Endspieldatenbank zur Zugberechnug genutzt werden kann.

### Minimax-Algorithmus

Ein tiefensuchender Algorithmus, welche zur Zugfindung im Mittelspiel verwendet wird

### Optionen

Einstellungsmöglichkeiten für die Engine, welche über UCI getätigt werden.

### UCI-Protokoll

Univerlas Chess Interface. Ein Protokoll zur Kommunikation mit eine Engine

### Zugermittlung

Das ermitteln eines Zuges für ein gegebenes Schachbrett.[Zugermittlung](../flengine/laufzeitsicht/zugermittlung.md)

### Zuggeber

Der Teil der Engine, welcher die Zugermittlung durchführt.

[Interface MoveProvider](../flengine/bausteinsicht/ebene-2/interface-moveprovider.md)

### Zugüberprüfung

Überprüfung auf Legalität des Zuges.

[Subsystem Rules](../flengine/bausteinsicht/ebene-2/subsystem-rules.md)
