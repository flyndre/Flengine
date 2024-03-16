# Zuermittlung MinMax

# Funktionsweise

Der Minimaxalgorithmus funktioniert im Grunde immer gleich. Er geht alle entsprechenden Züge bis zu einer bestimmten Tiefe durch und baut aus diesen einen Baum. Jeder Zug wird bewertet. Der Zug, dessen Pfad die meisten Punkte erzielt wird dann ausgewählt.

Beim Schach wird der entsprechend stärkste Zug der eigenen Farbe genommen. Dann wird der beste Zug der jeweilig anderen Farbe ausgewählt und dessen Rating vom vorherigen Rating subtrahiert. Die Funktionsweise kann auch in folgendem Bild nachvollzogen werden.

Man erkennt hier eine Baumstruktur die aufgebaut wird. Der Zug, der am Ende die höchste Bewertung erhält, wird dann als erstes Element der Liste zurückgegeben.

# Aufbau

Der Algorithmus wurde mittels einem Rekursionsbaum umgesetzt. Um den Vorgang zu beschleunigen wurde er gemultithreadet. Dazu wurde die Javaklasse RecursiveTask verwendet um den Rekursiven Aufruf einem Fork-Join-Pool zu übergeben und dort berechnen zu lassen.

Der Aufbau der Komponenten sieht wie folgt aus.

Man erkennt schnell einen rekursiven Aufruf und eine sehr schlechte Laufzeit (exponentiell) da bei jeder neuen Task die entsprechenden weiteren Züge bis zum eingestellten Level bewertet werden müssen.

Die maximale Tiefe des Rekursionsbaums wird über die UCI-Option `RecursiveDepth` eingestellt.

Im folgenden kann der Ablauf des Minimax-Algorithmus nachvollzogen werden. Hierbei sind alle Komponenten dargestellt, die der Minmax benötigt, um zu funktionieren. Wichtig ist hier zu erwähnen, das der Algorithmus hier als eine Komponente dargestellt wird, sich jedoch in Minmax-Klasse sowie in die Klasse für den rekursiven Aufruf aufteilt. (Siehe [Subsystem MinMax](../../../docs/bausteinsicht/ebene-2/subsystem-minmax.md))

Das Diagramm zeigt nur einen Auszug des Ablaufes. Zurückgegeben wird eine sortierte Liste, wobei das erste Element den besten Zug beinhaltet.

![](./attachments/Ablauf%20Minimax.png)
