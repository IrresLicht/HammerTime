CREATE TABLE StrukturObjekt
	(ID                 INTEGER     	NOT NULL,
	Gruppen_ID			INTEGER			NOT NULL,
    ObjektName			VARCHAR(255)	NOT NULL,
	HatNachfolger		INTEGER			NOT NULL,
	Links				INTEGER			NOT NULL,
	Rechts				INTEGER			NOT NULL,
    CONSTRAINT StrukturObjekt_PK  		PRIMARY KEY (ID)
	);

CREATE TABLE Buchungseintrag
	(ID					INTEGER			NOT NULL,
	Startzeit			LONG			NOT NULL,
	Endzeit				LONG			NOT NULL,
	Kommentar			VARCHAR(10000)			,
	StrukturObjekt_ID	INTEGER			NOT NULL,
	CONSTRAINT Buchungseintrag_PK  		PRIMARY KEY (ID)
	);

INSERT INTO StrukturObjekt VALUES (1, -1, 'PROJEKTSTRUKTUR', 1, 1, 2);