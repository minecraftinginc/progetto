public class InserimentoFilmFactory {

    public static InserimentoFilm createInserimentoFilm() {
        return new InserimentoFilmImpl();
    }

    private static class InserimentoFilmImpl extends InserimentoFilm {// se in futuro volessi inserire film in altri modi potrei aggiungere qui
        //per mancanza di tempo non ho implementato ulteriori modi ma di base il factory methos è usato anche se non al massimo delle potenzialità
        //Ad esempio, si potrebbero aggiungere metodi per validare i dati del film, gestire immagini multiple, interfacciarsi con un database diverso
    }
}//factory method
