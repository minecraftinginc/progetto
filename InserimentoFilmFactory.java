public class InserimentoFilmFactory {

    public static InserimentoFilm createInserimentoFilm() {
        return new InserimentoFilmImpl();
    }

    private static class InserimentoFilmImpl extends InserimentoFilm {
    }
}//factory method
