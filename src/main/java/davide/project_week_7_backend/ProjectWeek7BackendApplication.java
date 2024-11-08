package davide.project_week_7_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*creare una spring web application che permetta agli utenti di visualizzare una lista di eventi, prenotare posti per partecipare agli eventi disponibili e fornire funzionalità di gestione degli eventi per gli organizzatori
Implementare un sistema di registrazione e accesso per gli utenti. Definire almeno due ruoli utente: "Utente Normale" e "Organizzatore di Eventi" (Non serve il CRUD completo per gli utenti). Le password devono essere protette opportunamente. “Organizzatore di Eventi” è il creatore dell’evento, non è un Admin della piattaforma
Gli organizzatori possono creare nuovi eventi specificando dettagli come titolo, descrizione, data, luogo e numero di posti disponibili, l’evento inoltre deve avere un riferimento al creatore di esso. Gli organizzatori devono anche poter modificare ed eventualmente eliminare i propri eventi
Gli utenti possono prenotare posti per partecipare agli eventi che hanno ancora disponibilità
Ricordarsi di gestire in maniera appropriata eccezioni ed opportune risposte di errore*/

@SpringBootApplication
public class ProjectWeek7BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectWeek7BackendApplication.class, args);
    }

}
