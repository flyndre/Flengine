# Spielstrategien

Bei den Spielstrategien kann eine Reihenfolge der Abläufe angegeben werden.

Die Standardreihenfolge läuft wie folgt ab:

*   Überprüfung ob Züge aus der Lichess-Eröffnungsdatenbank verfügbar sind
    
    *   Die Engine antwortet also zuerst mit “Buchwissen”
        
*   Wenn kein Zug gefunden wird, übernimmt der Minmax-Algorithmus
    
    *   Dieser Algorithmus wurde selbstständig entwickelt und bewertet die aktuelle Situation
        
*   Überprüfung der Endspieldatenbank von Lichess
    
    *   Wenn weniger als 7 Figuren auf dem Brett stehen spricht man von einem Endspiel. Ab diesem Zeitpunkt wird wieder mit “Buchwissen” geantwortet.