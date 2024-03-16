# DE5: Programmatische Loggingkonfiguration

## Fragestellung:

Wie kann das Logging konfiguriert werden?

## Einflussfaktoren:

*   Grad der Konfigurierbarkeit
    
*   Möglichkeiten integriertes Java-Logging anzupassen
    
*   UCI-Command `debug on/off` unterstützen (dynamische konfiguration)
    

## Alternativen:

### `logging.properties`

*   Lässt nur eine Konfiguration zu
    
*   Nicht zur Laufzeit anpassbar
    
*   Überschreibt alle programmatische Konfiguration
    
*   Zur Compilezeit festzulegen oder weitere Datei neben JAR
    

### Java VM Optionen

*   Nicht benutzerfreundlich
    
*   Nicht zur Laufzeit anpassbar
    

### Programmatische Konfiguration

*   Zur Laufzeit anpassbar
    
*   Kann auf UCI Comamnd reagieren
    
*   Java VM Optionen auch programmatisch auslesbar
    

## Entscheidung:

*   Konfiguration programmatisch (via `LogChannelManager`, sh. [Logging](../../flengine/querschnittliche-konzeption/logging.md))