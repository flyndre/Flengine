# DE6: Nutzung von Virtual Threads

## Fragestellung:

Macht ein Einsatz von Virtual Threads für die Berechnung des MinMax-Algorithmus Sinn?

## Einflussfaktoren:

*   Geschwindigkeit des Programms
    
*   Umsetzbarkeit des MinMax-Algorithmus
    

## Alternativen:

*   Beibehaltung der bisherigen Implementierung auf Basis eines ForkJoinPools und RecursiveTask
    

## Entscheidung:

Die bisherige Implementierung auf Basis des ForkJoinPools wird beibehalten.

*   Die Implementierung auf Basis von Virtual Threads brachte keinen Geschwindigkeitszuwachs, sondern lief in Tests minimal langsamer