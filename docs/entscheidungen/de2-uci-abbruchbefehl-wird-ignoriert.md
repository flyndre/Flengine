# DE2: UCI Abbruchbefehl wird ignoriert

## Frage:

Kann der Abbruchbefehl von UCI von der Engine ignoriert werden?

Frage betrifft das Design der Nachrichtenverarbeitung und es kompletten Zuggenerierungsprozesses.

## Einflussfaktoren:

*   Bei ignorierung des Befehls muss keine Abbruchmöglichkeit der Zugberechnung eingebaut werden
    
*   Erheblicher mehraufwand und komplexitätszuwachs des Zuggenerierens.
    

## Alternativen:

### Befehl Ignorieren:

*   Der Abbruchbefehl wird zwar entgegengenommen aber von der Nachrichtenverwaltunf ignoriert.
    

### Befehl unterstützen:

*   Der Abbruch befehl wird von der Engine voll unterstützt und kann die Zuggenerierung jeder Zeit unterbrechen.
    

## Entscheidung:

Der Befehl wird ignoriert.

*   Der Befehl kann ignoriert werden, da der MinMax Algorithmus eine maximale Tiefe von 3 erreichen soll.
    
*   Mit dieser Entscheidung wird die Zuggenerierung einfach gehalten.
    
*   Entscheidung in gemeinsamen Architekturmeeting am 30.10.23 gefallen