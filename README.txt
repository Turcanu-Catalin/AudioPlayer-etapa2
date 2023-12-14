Pentru aceasta etapa, am luat implementarea deja facuta pentru etapa trecuta si am dezvoltat o.
Fiindca acum avem inca 2 tipuri de useri, am adaugat doua clase, Artist si Host,
ce extind clasa parinte User, avand campuri si functii proprii in conformitate cu cerinta.
Am mai adaugat si clasa album ce extinde AudioCollection, avand in etapa asta
noul tip de colectie audio, albumul.
Mi am mai creat in utils niste clase ajutatoare:
Clasele Event, Merch le folosesc pentru evenimentele si merchurile artistilor.
iar Annnouncement pentru anunturile hostilor.
De asemenea mi am creat clasa Date pentru comoditatea de a lucra
cu data unui eveniment, intrucat trebuia sa verific validitatea unei dati.
Clasele ArtistsEntry si HostEntry le am creat pentru a putea face
serach dupa un artist sau host, fiindca in search se foloseste un
obiect de tip LibraryEntry unde se slaveaza rezultatul searchului,
astfel am folosit aceste clase ce extind LibraryEntry
pentru a converti din Artist si Host in ele, in Admin am facut conversia,
astfel putand lucra cu ele mai departe.
Pentru sistemul de pagini am facut un pachet cu clase aparte.
Am folosit design patternul Factory pentru comoditate(si ca trebuia :)).
Am creeat o interfata Page unde e doar antentul functie printCurrentPage,
si alte 4 clase pentru fiecare tip de pagina ce implemnteaza aceasta interfata,
rescriind metoda printCurrentPage pentru fiecare caz.
La final am creeat si clasa factory, numita PageFactory, unde in dependenta
de unde se afla useru si ce doreste sa acceseze, se creaza pagina si se
afiseaza continutul ei in dpendenta de tipul paginii.
Am mai folosit doua design patterns Singleton pentru Admin si PageFactory,
intrucat aceste 2 clase am avut nevoie sa le instantiez doar o data.
Am folosit si ChatGPT in aceasta tema, mai mult pentru erori de sintaxa minore
in cadrul metodelor, pe care nu le intelegeam pe deplin si pentru
documentatie refirotare la design patterns.

(PS: Sa ai o zi frumoasa :))
