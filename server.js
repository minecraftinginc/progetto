const express = require('express');
const fileUpload = require('express-fileupload');
const app = express();
const port = 3000;
const fs = require('fs');
// Configura Express per servire file statici dalla directory in cui si trova "server.js"
app.use(express.static(__dirname));
app.use(express.json()); // Per analizzare il corpo delle richieste in formato JSON
app.use(fileUpload()); // Per gestire gli upload di file

let utente = [];
const oggetti = [];
let userDataChanged = false; // Una variabile per tenere traccia dei cambiamenti nei dati degli utenti
let oggettiDataChanged = false; // Una variabile per tenere traccia dei cambiamenti nei dati degli oggetti
// Carica i dati degli utenti e degli oggetti da file di testo quando il server si avvia
function loadUserData() {
   try {
    const userData = fs.readFileSync('utenti.txt', 'utf8');
    utente = userData.split('\n').map(user => {
      const [id, username, email, password, dataDiNascita, nome, cognome] = user.split(':');
      return { id, username, email, password, dataDiNascita, nome, cognome };
    });
  } catch (error) {
    utente = []; // Inizializza con un array vuoto in caso di errore
  }
   try {
    const objectData = fs.readFileSync('oggetti.txt', 'utf8');
    oggetti.push(...objectData.split('\n').map(item => {
      const [nome, descrizione, prezzo, categoria, proprietario, immagine] = item.split(':');
      return { nome, descrizione, prezzo, categoria, proprietario, immagine };
    }));
  } catch (error) {
    oggetti.push({}); // Inizializza con un oggetto vuoto
  }
}

function saveDataIfChanged() {
  if (userDataChanged) {
    // Salva i dati degli utenti in un formato separato da due punti
    const userData = utente.map(user => {
      return `${user.id}:${user.username}:${user.email}:${user.password}:${user.dataDiNascita}:${user.nome}:${user.cognome}`;
    }).join('\n'); // Ogni utente su una nuova riga

    fs.writeFileSync('utenti.txt', userData, { encoding: 'utf8', flag: 'w' });
    userDataChanged = false; // Resetta la variabile
  }
}
// Funzione per verificare se un utente è già presente
function isUserAlreadyExists(username, email) {
  // Controlla se un utente con lo stesso username o email esiste già nell'array utente
  const existingUser = utente.find(user => user.username === username || user.email === email);

  return !!existingUser; // Restituisce true se esiste già un utente con lo stesso username o email
}
// Carica i dati all'avvio del server
loadUserData();

// Rotte per gli utenti
app.get('/utente', (req, res) => {
  res.json(utente);
});

app.post('/utente', (req, res) => {
  const userData = req.body;

  if (!userData.username || !userData.email || !userData.password || !userData.dataDiNascita || !userData.nome || !userData.cognome) {
    return res.status(400).send('Dati utente incompleti');
  }

  // Verifica se l'utente è già presente
  if (isUserAlreadyExists(userData.username, userData.email)) {
    return res.status(409).send('Utente già esistente'); // Restituisce uno stato di conflitto
  }

  // Se l'utente non esiste già, procedi con l'aggiunta
  const newUser = {
    id: utente.length + 1,
    username: userData.username,
    email: userData.email,
    password: userData.password,
    dataDiNascita: userData.dataDiNascita,
	nome:userData.nome,
	cognome:userData.cognome
    // Altri attributi specifici per il tuo progetto
  };
  utente.push(newUser);
  userDataChanged = true;
  saveDataIfChanged();
  res.redirect('/benvenuto.html');
});

// Rotte per gli oggetti
app.get('/oggetti', (req, res) => {
  res.json(oggetti);
});

app.post('/oggetti', (req, res) => {
  if (!req.files || Object.keys(req.files).length === 0) {
    return res.status(400).send('Nessun file caricato.');
  }
  
  const newoggetti = {
    id: oggetti.length + 1, // Un identificatore univoco
    nome: req.body.nome,
    descrizione: req.body.descrizione,
    prezzo: req.body.prezzo,
    categoria: req.body.categoria,
    proprietario: req.body.proprietario,
    // Altri attributi specifici per il tuo progetto
  };

  const image = req.files.immagine;
  const uploadPath = __dirname + '/uploads/' + image.name;

  image.mv(uploadPath, (err) => {
    if (err) {
      return res.status(500).send(err);
    }

    newoggetti.immagine = '/uploads/' + image.name;
    oggetti.push(newoggetti);
	oggettiDataChanged = true;
    saveDataIfChanged(); // Salva i dati degli oggetti dopo l'aggiunta di un oggetto
    res.redirect('/inserimentooggetti.html');
  });
});
// Funzione per verificare le credenziali dell'utente
function isUserValid(username, password) {
  // Trova l'utente con lo stesso username
  const user = utente.find(user => user.username === username);

  // Se l'utente non esiste o la password non corrisponde, restituisci false
  if (!user || user.password !== password) {
    return false;
  }

  return true; // Le credenziali sono corrette
}

app.post('/login', (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).send('Credenziali mancanti');
  }

  // Verifica le credenziali
  if (isUserValid(username, password)) {
    return res.redirect('/benvenuto.html');
  } else {
    return res.status(401).send('Accesso non autorizzato'); // Restituisce uno stato di "Non autorizzato" (HTTP 401)
  }
});

app.listen(port, () => {
  console.log(`Il server è in ascolto sulla porta ${port}`);
});