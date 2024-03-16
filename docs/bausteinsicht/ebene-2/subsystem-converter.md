# Subsystem Converter

Der Converter stellt die “Verbindung zur Außenwelt” der Flengine dar und ist für die Kommunikation zur GUI zuständig. Aufgabe des Umwandlers ist es, eingehende Anfragen des Frontends in Java-Objekte umzuwandeln und diese an den Engine-Controller weiterzuleiten. Anschließend wird die Antwort des Engine-Controllers wieder in UCI-Protokoll-konforme Strings umzuwandelt und an das Frontend übergeben.

# Diagramm

Folgendes Diagramm erläutert die konzeptionelle Funktionsweise des Converters. Die Anfragen der GUI über Stdin werden vom `RequestHandler` entgegengenommen und verarbeitet. Dieser veranlasst die Verarbeitung mittels des `Organizer`s. Dieser nutzt den `Converter`, um zwischen UCI und Java-Objekten zu parsen. Diese Objekte nutzt der Organisator wiederum, um mit der Engine zu kommunizieren.

![](./attachments/Anfragenhandling.png)