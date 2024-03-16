# Konventionen

# Code

Der Quellcode folgt den [Java-Code-Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html) von Oracle. Darüber hinaus wird sich für Coding-Praktiken und Dateistruktur des Quellcodes an [Best Practices](https://www.baeldung.com/java-clean-code) ([Clean%20Coding%20in%20Java%20(Baeldung).pdf](./attachments/Clean%20Coding%20in%20Java%20(Baeldung).pdf)
) orientiert.

Es wird für Methodennamen Camelcase verwendet. Klassennamen werden groß geschrieben.

Wenn HTTP-Anfragen an die entsprechenden Datenbanken erfolgen, soll die Dependency OkHttp verwendet werden. Der String der Antwort wird nicht verwendet sondern direkt in ein Datenmodell durch Json-B umgewandelt.

# Commits

Um eine einheitliche Commit-Historie zu garantieren sollen Commits in folgendem Aufbau erfolgen:

**<Ticketnummer>: <Tätigkeit>**

Falls keine Ticketnummer vorliegt, sollte die Art der Änderung dargestellt werden, sodass dies ebenfalls bekannt ist. Man unterscheidet zwischen:

*   **feat:** Feature wird hinzugefügt
    
*   **refactor:** Refactoring des Codes
    
*   **docs:** Dokumentation des Codes
    

Die Beschreibung der Tätigkeit erfolgt in englischer Sprache.

### Beispiele

*   FE-123: Implemented new Class Engine.
    
*   feat: New Endpoint for existing Class Engine.
    
*   docs: add Javadocs for Class Engine.
    

# Branches

Auch bei der Benamung von Branches soll auf ein einheitliches Muster geachtet werden:

**<Änderungsart>/<Feature>**

Als Änderungsart kommen vor allem folgende Kategorien in Betracht:

*   **feature:** Feature wird hinzugefügt
    
*   **fix:** Refactoring des Codes
    
*   **docs:** Dokumentation des Codes
    
*   **refactor:** Reformatierung des Codes
    
*   **test:** Änderungen bezüglich tests
    

Auch hier werden die Features in englischer Sprache beschrieben.

### Beispiele

*   feature/endgame-service
    
*   fix/500-error-when-castling
    
*   test/test-for-api