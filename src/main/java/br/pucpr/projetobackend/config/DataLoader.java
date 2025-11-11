package br.pucpr.projetobackend.config;

import br.pucpr.projetobackend.model.Actor;
import br.pucpr.projetobackend.model.Author;
import br.pucpr.projetobackend.model.Movie;
import br.pucpr.projetobackend.model.User;
import br.pucpr.projetobackend.repository.ActorRepository;
import br.pucpr.projetobackend.repository.AuthorRepository;
import br.pucpr.projetobackend.repository.MovieRepository;
import br.pucpr.projetobackend.repository.UserRepository;
import br.pucpr.projetobackend.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadUsers();
        loadAuthors();
        loadActors();
        loadMovies();
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setNome("Admin User");
            admin.setEmail("admin@example.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            User regularUser = new User();
            regularUser.setNome("Regular User");
            regularUser.setEmail("user@example.com");
            regularUser.setSenha(passwordEncoder.encode("user123"));
            regularUser.setRole(Role.USER);
            userRepository.save(regularUser);

            System.out.println("✅ Usuários de teste criados:");
            System.out.println("   📧 Email: admin@example.com | 🔑 Senha: admin123 | 👤 Role: ADMIN");
            System.out.println("   📧 Email: user@example.com  | 🔑 Senha: user123  | 👤 Role: USER");
        }
    }

    private void loadAuthors() {
        List<Author> authors = Arrays.asList(
            createAuthor("Fernando Meirelles", "Diretor brasileiro conhecido por Cidade de Deus e O Jardineiro Fiel. Indicado ao Oscar de Melhor Diretor."),
            createAuthor("Walter Salles", "Cineasta brasileiro premiado, diretor de Central do Brasil e Diários de Motocicleta. Vencedor de Urso de Ouro em Berlim."),
            createAuthor("José Padilha", "Diretor brasileiro de Tropa de Elite e Tropa de Elite 2. Também dirigiu séries como Narcos."),
            createAuthor("Karim Aïnouz", "Cineasta brasileiro conhecido por O Céu de Suely e A Vida Invisível. Vencedor de prêmios em Cannes."),
            createAuthor("Anna Muylaert", "Diretora brasileira de Que Horas Ela Volta? e Mãe Só Há Uma. Reconhecida por retratar questões sociais."),
            createAuthor("Kleber Mendonça Filho", "Diretor pernambucano de O Som ao Redor, Aquarius e Bacurau. Vencedor do Prêmio do Júri em Cannes."),
            createAuthor("Glauber Rocha", "Ícone do Cinema Novo brasileiro, diretor de Deus e o Diabo na Terra do Sol e Terra em Transe."),
            createAuthor("Hector Babenco", "Diretor argentino-brasileiro de Pixote e Carandiru. Primeiro latino-americano indicado ao Oscar de Melhor Diretor."),
            createAuthor("Selton Mello", "Ator e diretor brasileiro, diretor de O Palhaço. Reconhecido por sua versatilidade no cinema nacional.")
        );
        authorRepository.saveAll(authors);
    }

    private void loadActors() {
        List<Actor> actors = Arrays.asList(
            createActor("Wagner Moura", LocalDate.of(1976, 6, 27), "Brasileiro", "Ator conhecido por Tropa de Elite, Narcos e Marighella. Indicado ao Globo de Ouro.", "https://example.com/wagner.jpg"),
            createActor("Fernanda Montenegro", LocalDate.of(1929, 10, 16), "Brasileira", "Primeira atriz brasileira indicada ao Oscar, conhecida por Central do Brasil. Ícone do cinema nacional.", "https://example.com/fernanda.jpg"),
            createActor("Rodrigo Santoro", LocalDate.of(1975, 8, 22), "Brasileiro", "Ator internacional conhecido por 300, Love Actually e Westworld. Sucesso no cinema brasileiro e hollywoodiano.", "https://example.com/rodrigo.jpg"),
            createActor("Alice Braga", LocalDate.of(1983, 4, 15), "Brasileira", "Atriz de Cidade de Deus, Sou Legend e Queen of the South. Reconhecida internacionalmente.", "https://example.com/alice.jpg"),
            createActor("Matheus Nachtergaele", LocalDate.of(1969, 1, 3), "Brasileiro", "Ator premiado de O Auto da Compadecida, Cidade de Deus e Bacurau. Múltiplos prêmios nacionais.", "https://example.com/matheus.jpg"),
            createActor("Sônia Braga", LocalDate.of(1950, 6, 8), "Brasileira", "Atriz internacional de Aquarius, Kiss of the Spider Woman e Dona Flor. Indicada ao Globo de Ouro.", "https://example.com/sonia.jpg"),
            createActor("Lázaro Ramos", LocalDate.of(1978, 11, 1), "Brasileiro", "Ator, diretor e roteirista de Madame Satã e Ó Paí, Ó. Grande nome do cinema e TV brasileiros.", "https://example.com/lazaro.jpg"),
            createActor("Camila Pitanga", LocalDate.of(1977, 6, 14), "Brasileira", "Atriz conhecida por Paraíso Tropical e Velho Chico. Múltiplos prêmios em televisão e cinema.", "https://example.com/camila.jpg"),
            createActor("Selton Mello", LocalDate.of(1972, 12, 30), "Brasileiro", "Ator e diretor de O Palhaço e Lisbela e o Prisioneiro. Um dos mais versáteis atores brasileiros.", "https://example.com/selton.jpg"),
            createActor("Taís Araújo", LocalDate.of(1978, 11, 25), "Brasileira", "Atriz de Cheias de Charme e Geração Brasil. Primeira protagonista negra de novela das nove.", "https://example.com/tais.jpg"),
            createActor("Cauã Reymond", LocalDate.of(1980, 5, 20), "Brasileiro", "Ator de Avenida Brasil e A Regra do Jogo. Galã da televisão e cinema brasileiros.", "https://example.com/caua.jpg"),
            createActor("Juliana Paes", LocalDate.of(1979, 3, 26), "Brasileira", "Atriz de A Favorita e Gabriela. Uma das atrizes mais populares da televisão brasileira.", "https://example.com/juliana.jpg")
        );
        actorRepository.saveAll(actors);
    }

    private void loadMovies() {
        List<Author> authors = authorRepository.findAll();
        List<Actor> actors = actorRepository.findAll();

        Author meirelles = findAuthorByName(authors, "Fernando Meirelles");
        Author salles = findAuthorByName(authors, "Walter Salles");
        Author padilha = findAuthorByName(authors, "José Padilha");
        Author ainouz = findAuthorByName(authors, "Karim Aïnouz");
        Author muylaert = findAuthorByName(authors, "Anna Muylaert");
        Author kleber = findAuthorByName(authors, "Kleber Mendonça Filho");
        Author glauber = findAuthorByName(authors, "Glauber Rocha");
        Author babenco = findAuthorByName(authors, "Hector Babenco");
        Author seltonAuthor = findAuthorByName(authors, "Selton Mello");

        Actor wagner = findActorByName(actors, "Wagner Moura");
        Actor fernanda = findActorByName(actors, "Fernanda Montenegro");
        Actor rodrigo = findActorByName(actors, "Rodrigo Santoro");
        Actor alice = findActorByName(actors, "Alice Braga");
        Actor matheus = findActorByName(actors, "Matheus Nachtergaele");
        Actor sonia = findActorByName(actors, "Sônia Braga");
        Actor lazaro = findActorByName(actors, "Lázaro Ramos");
        Actor camila = findActorByName(actors, "Camila Pitanga");
        Actor selton = findActorByName(actors, "Selton Mello");
        Actor tais = findActorByName(actors, "Taís Araújo");
        Actor caua = findActorByName(actors, "Cauã Reymond");
        Actor juliana = findActorByName(actors, "Juliana Paes");
        
        List<Movie> movies = Arrays.asList(
            createMovie("Cidade de Deus", meirelles, Arrays.asList(alice, matheus)),
            createMovie("O Jardineiro Fiel", meirelles, Arrays.asList()),
            createMovie("Central do Brasil", salles, Arrays.asList(fernanda)),
            createMovie("Diários de Motocicleta", salles, Arrays.asList(rodrigo)),
            createMovie("Tropa de Elite", padilha, Arrays.asList(wagner)),
            createMovie("Tropa de Elite 2", padilha, Arrays.asList(wagner)),
            createMovie("O Céu de Suely", ainouz, Arrays.asList()),
            createMovie("A Vida Invisível", ainouz, Arrays.asList()),
            createMovie("Que Horas Ela Volta?", muylaert, Arrays.asList(camila)),
            createMovie("Aquarius", kleber, Arrays.asList(sonia)),
            createMovie("Bacurau", kleber, Arrays.asList(sonia, selton)),
            createMovie("Deus e o Diabo na Terra do Sol", glauber, Arrays.asList()),
            createMovie("Pixote", babenco, Arrays.asList()),
            createMovie("Carandiru", babenco, Arrays.asList(wagner, lazaro, rodrigo)),
            createMovie("O Palhaço", seltonAuthor, Arrays.asList(selton, tais, caua, juliana))
        );

        movieRepository.saveAll(movies);
    }

    private Author createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        return author;
    }

    private Actor createActor(String name, LocalDate birthDate, String nationality, String biography, String photoUrl) {
        Actor actor = new Actor();
        actor.setName(name);
        actor.setBirthDate(birthDate);
        actor.setNationality(nationality);
        actor.setBiography(biography);
        actor.setPhotoUrl(photoUrl);
        return actor;
    }

    private Movie createMovie(String title, Author author, List<Actor> actors) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setAuthor(author);
        movie.setActors(actors);
        return movie;
    }

    private Author findAuthorByName(List<Author> authors, String name) {
        return authors.stream()
            .filter(a -> a.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    private Actor findActorByName(List<Actor> actors, String name) {
        return actors.stream()
            .filter(a -> a.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
}

