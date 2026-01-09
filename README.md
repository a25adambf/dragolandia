# DRAGOLANDIA

## INTRODUCIÓN


## ANÁLISIS

### DIAGRAMA DE CLASES
'''mermaid
---
config:
  theme: redux-dark
---
classDiagram
    class Mago {
        -int id
        -string nombre
        -int vida
        -int nivelMagia
        -List~Hechizo~ conjuros
        +lanzarHechizo(Monstruo monstruo)
    }

    class Monstruo {
        -int id
        -string nombre
        -int vida
        -TipoMonstruo tipo
        -int fuerza
        +atacar(Mago mago)
    }

    class TipoMonstruo {
        <<enumeration>>
        OGRO
        TROLL
        ESPECTRO
    }

    class Dragon {
		-int id
        -string nombre
        -int intensidadFuego
        -int resistencia
        +exhalar(Monstruo monstruo)
    }

    class Bosque {
        -int id
        -string nombre
        -int nivelPeligro
        -Monstruo monstruoJefe
        -List~Monstruo~ monstruos
        +mostrarJefe()
        +cambiarJefe(Monstruo nuevoJefe)
        +addMonstruo(Monstruo monstruo)
    }

    class Hechizo {
        <<abstract>>
        -int id
        -String nombre
		-String descripción
        +efecto()*
    }

    class BolaFuego {
        +efecto()
    }

    class Rayo {

        +efecto()
    }

    class BolaNieve {
        +efecto()
    }

    class AtaqueBasico {
        +efecto()
    }

    Mago "1" --> "*" Hechizo : conoce
    Mago ..> Monstruo : lanza hechizo a
    Monstruo --> TipoMonstruo : tiene
    Monstruo ..> Mago : ataca a
    Dragon ..> Monstruo : exhala fuego a
    Dragon "1" --> "1" Bosque : habita en
    Bosque "1" --> "1" Monstruo : tiene jefe
    Bosque "1" --> "0..*" Monstruo : contiene
    Hechizo <|-- BolaFuego : hereda
    Hechizo <|-- Rayo : hereda
    Hechizo <|-- BolaNieve : hereda
    Hechizo <|-- AtaqueBasico : hereda
    '''

## DISEÑO

### ENTIDAD REALACIÓN

---
config:
  theme: redux-dark-color
---
erDiagram
    BOSQUES ||--o| DRAGONES : "tiene"
    BOSQUES ||--o| MONSTRUOS : "tiene jefe"
    BOSQUES ||--o{ BOSQUES_MONSTRUOS : "contiene"
    MONSTRUOS ||--o{ BOSQUES_MONSTRUOS : "habita en"
    MAGOS ||--o{ MAGOS_HECHIZOS : "conjura"
    HECHIZOS ||--o{ MAGOS_HECHIZOS : "es conjurado por"

    BOSQUES {
        int id PK
        int nivelPeligro
        string nombre
        int dragon_id FK
        int monstruoJefe_id FK
    }

    DRAGONES {
        int id PK
        int intensidadFuego
        string nombre
        int resistencia
    }

    MONSTRUOS {
        int id PK
        int fuerza
        string nombre
        enum tipo
        int vida
    }

    BOSQUES_MONSTRUOS {
        int Bosque_id FK
        int monstruos_id FK
    }

    MAGOS {
        int id PK
        int nivelMagia
        string nombre
        int vida
    }

    HECHIZOS {
        string DTYPE
        int id PK
        string descripcion
        string nombre
    }

    MAGOS_HECHIZOS {
        int Mago_id FK
        int conjuros_id FK
    }

### CAMBIOS Y MEJORAS

El principal cambio que introduciría sería un sistema de maná para los magos. Es decir al invocar un hechizo gasta maná, y a su vez recupera una pequeña parte del total en cada turno.