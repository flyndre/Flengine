# DE1: Datentyp für Figurentyp

## Frage: Wie soll der Typ einer Schachfigur im Datenmodell gespeichert werden?

Frage betrifft die Ausgestaltung des Datenmodells und damit die gesamte Architektur. Je nach Entscheidung muss im gesamten Projekt damit umgegangen werden.

## Einflussfaktoren:

*   Entscheidet darüber wie komplex der Umgang mit dem Datenmodell ist.
    
*   Betrifft das Qualitätziel einer einfachen und klaren Architektur
    
*   Es besteht das Risiko von zu komplexen Zugriffen auf das Modell
    

## Alternativen:

### Figurtyp als Enum:

*   Den Figurtyp als Enum zu realisieren hat den Vorteil einer einfachen Struktur und des einfachen Umgangs damit. Er kann einfach Verglichen werden. Mit dieser Entscheidung wird jedoch der Objektorientierung nicht die höchste priorität gegeben.
    

### Figurtyp als Klasse mit Kindklassen:

*   Die reale Welt wird duch Objektorientierung wiedergespiegelt. Erschwert den vergleich des Figurtypes, da instanceof() abfragen nötig sind.
    

## Entscheidung:

Der Figurtyp wird als Enum realisiert.

*   Entschieden im gemeinsammen Gespräch am 30.10.23 mit allen Entwicklern
    
*   Mit der Entscheidung wird der Code einfacher gehalten