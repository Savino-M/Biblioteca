# Biblioteca
Una bozza di una biblioteca online pronta per essere caricata sul server heroku.
Fare attenzione a creare due app separate su heroku, una dovrà fungere da backend e l'altra da frontend.
La parte frontend dell'applicazione è nella cartella WebContent.
Un esempio di questo progetto caricato su Heroku si trova al seguente link: https://bibliotecafront.herokuapp.com/.
L'app ha il solo scopo di aiutare ad entrare nell'ottica dello sviluppo cloud in quanto per realizzarla si fa uso dei framework Spring e Hibernate.

Si consiglia di eseguire l'applicazione prima in locale, andando a cambiare il file application.properties, e settando i parametri in modo da connettersi a un db localize(es H2).
Successivamente, una volta verificato che il backend funzioni in locale, lo si può caricare su Heroku, cambiando però l'application properties per connettersi a un db remoto.

Poi si può passare al frontned, dove basta cambiare gli URL delle richieste HTTP, mettendo invece che "localhost:8080", l'URL del backend caricato su Heroku.
