# DE4: Deployment via Batch

## Fragestellung:

Wie kann die Engine im UI verwendet werden?

Problem: Alle UCI-UIs benötigen ausführbare Dateien (.exe, .bat, .cmd) um die Engine aufzurufen. Es kann keine jar-Datei an die Engine übergeben werden.

## Einflussfaktoren:

Wie kann eine sinnvolle ausführbare Datei erstellt werden?

Bereiche:

*   Komplexität
    
*   Sicherheit
    
*   Handhabbarkeit
    

## Alternativen:

### Batch-Datei:

An die Engine kann einfach eine Batchdatei übergeben werden, die den Java-Compiler aufruft. Dies ist mit abstand der einfachste Weg und einfach automatisierbar, jedoch wird die JRE benötigt, um die Engine durch das UI spielen zu lassen.

### Launch4j:

Konvertiert jar-Datei zu einer Exe. Sehr komplex da ein Drittanbieter Programm verwendet werden muss. Kein Weg vorbei an Dekompilation, also Sicherheitsrisiken. Großer Vorteil: Der Endnutzer muss keine JRE installiert haben.

## Entscheidung:

Da Java eine sehr verbreitete Sprache ist, und die JRE bei vielen System schon installiert ist wird sich für das Batch-Skript entschieden. Falls keine JRE installiert ist, wird ein Schritt in der Deploymentanleitung eingefügt, um diese zu installieren.