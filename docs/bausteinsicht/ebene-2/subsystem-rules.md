# Subsystem Rules

Dieses Subsystem ist für die Bereitstellung der Schach-Regeln zuständig. Es besteht aus der “Einstiegsklasse” `Rules` und bietet Methoden, Informationen und Züge über den aktuellen Spielstand.

# Diagramm

Das folgende Diagramm zeigt den Aufbau des Subsystems. `Rules` ist nach außen freigegeben und bedient sich den darunterliegenden `PieceRules`. `PieceRules` bietet eine Methode, um alle legale Züge für eine beliebige Spielfigur zu ermitteln, diese Methode greift auf die entsprechende private Methode dieser Figur zu, wo die konkreten Figurenregeln implementiert sind.

![](./attachments/Spielregeln.png)